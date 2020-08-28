/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author chengwei
 */
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

public class Waitlist {
    private static PreparedStatement addWaitlist;
    private static PreparedStatement getFlightSeats;
    private static PreparedStatement waitlistSelect;
    private static PreparedStatement status;
    private static Connection connection = DBConnection.getConnection();

    
    public static ArrayList< String > waitlistDate(Date date)
    {
        ArrayList< String > results = null;
        ResultSet resultSet = null;
        
        try 
        {
            waitlistSelect=connection.prepareStatement("SELECT PASSENGER, FLIGHT FROM BOOKING where DATE = ?");
            waitlistSelect.setDate(1, date);
            
            resultSet = waitlistSelect.executeQuery();
            results = new ArrayList< String >();
            
            while (resultSet.next())
            {
                results.add(resultSet.getString("PASSENGER"));
                results.add(resultSet.getString("FLIGHT"));
            }
        }
       
        catch (SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        finally
        {
            try
            {
                resultSet.close();
            }
            catch (SQLException sqlException)
            {
                sqlException.printStackTrace();
            }
        }
        return results;
    } 
    
    public static int addWaitlist (String passengerName, Date flightDate, String flightType, Timestamp passengerPosition)
    {
        int result = 0;

        try
        {
            addWaitlist = connection.prepareStatement("INSERT INTO WAITLIST (passenger,date, flight, timestamp) VALUES (?,?,?,?)");
            addWaitlist.setString(1, passengerName);
            addWaitlist.setDate(2, flightDate);
            addWaitlist.setString(3, flightType);
            addWaitlist.setTimestamp(4, passengerPosition);
            
                    
            result = addWaitlist.executeUpdate();
        }
        catch (SQLException sqlException)
        {
            sqlException.printStackTrace();
            
        }
        
        return result;
    }
    
    public static int getBookCount(String flight, Date date )
    {
        int results = 0;
        ResultSet resultSet = null;
        
        try
        {
            getFlightSeats = connection.prepareStatement("select count(flight) from booking where flight = ? and date = ?"); 
            getFlightSeats.setString(1, flight); getFlightSeats.setDate(2, date); 
            resultSet = getFlightSeats.executeQuery(); 
            resultSet.next(); 
            results = resultSet.getInt(1);
            

        }
       
        catch (SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        finally
        {
            try
            {
                resultSet.close();
            }
            catch (SQLException sqlException)
            {
                sqlException.printStackTrace();
            }
        }
        return results;
    }
public static DefaultTableModel settingJTable (Date theDay) {

    String[][] toAdd;
    ArrayList<String> passengerName = new ArrayList<>();
    ArrayList<String> flightType = new ArrayList<>();

        String query = "select passenger, flight from waitlist where date = ? ";
        PreparedStatement preparedStmt;
        try {
            preparedStmt = connection.prepareStatement(query);
            preparedStmt.setDate(1,theDay);
            ResultSet result = preparedStmt.executeQuery();
            while(result.next()) {
        try {
            passengerName.add(result.getString("passenger"));
            flightType.add(result.getString("flight"));
        } catch (SQLException ex) {
            Logger.getLogger(Waitlist.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
        } catch (SQLException ex) {
            Logger.getLogger(Waitlist.class.getName()).log(Level.SEVERE, null, ex);
        }

        toAdd = new String[passengerName.size()] [2];
        for (int i = 0; i < passengerName.size(); i++) {
            toAdd[i] [0] = passengerName.get(i);
            toAdd[i] [1] = flightType.get(i);
        }
        String [] colNames = new String[] {"Passenger", "Flight"};
        return new DefaultTableModel(toAdd,colNames);
    
    
}
public static int cancelWaitlist(String passenger, Date date)
{
    int results = 0;
        ResultSet resultSet = null;
        
        try
        {
            PreparedStatement preparedStmt = connection.prepareStatement("DELETE from waitlist where PASSENGER = ? and DATE = ?"); 
            preparedStmt.setString(1, passenger); preparedStmt.setDate(2, date); 
            results = preparedStmt.executeUpdate();
            

        }
       
        catch (SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return results;
}
public static String waitlistPassengerDate(String passengerName, Date dates)
{ 
    String results = null;
    ResultSet resultSet = null;
    try
        {
            status=connection.prepareStatement("SELECT passenger FROM Waitlist where flight = ? and DATE = ?");
            status.setString(1, passengerName);
            status.setDate(2, dates);
            resultSet = status.executeQuery();
            
            
            if (resultSet.next())
            {
                results =resultSet.getString("PASSENGER");
            }
        }
       
        catch (SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        finally
        {
            try
            {
                resultSet.close();
            }
            catch (SQLException sqlException)
            {
                sqlException.printStackTrace();
            }
        }
        return results;
    }
    }