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



public class Booking {
    private static PreparedStatement addBooking;
    private static PreparedStatement getBooking;
    private static PreparedStatement getFlightSeats;
    private static PreparedStatement selectStatus;
    private static Connection connection = DBConnection.getConnection();

    public static ArrayList< String > bookingFlightDate(String flight, Date date)
    {
        ArrayList< String > results = null;
        ResultSet resultSet = null;
        
        try 
        {
            selectStatus=connection.prepareStatement("SELECT PASSENGER FROM BOOKING where FLIGHT = ? and DATE = ?");
            selectStatus.setString(1, flight);
            selectStatus.setDate(2, date);
            resultSet = selectStatus.executeQuery();
            results = new ArrayList< String >();
            
            while (resultSet.next())
            {
                results.add(resultSet.getString("PASSENGER"));
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
    }//passenger
    
    public static int addBooking (String passengerName, Date flightDate, String flightType)
    {
        int result = 0;

        try
        {
            addBooking = connection.prepareStatement("INSERT INTO BOOKING (passenger,date, flight) VALUES (?,?,?)");
            addBooking.setString(1, passengerName);
            addBooking.setDate(2, flightDate);
            addBooking.setString(3, flightType);
            
                    
            result = addBooking.executeUpdate();
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
    


public static DefaultTableModel settingJTable (Date theDay, String theFlight) {

    String[][] toAdd;
    ArrayList<String> passengerName = new ArrayList<>();

        String query = "select passenger from booking where date = ? and flight = ?";
        PreparedStatement preparedStmt;
        try {
            preparedStmt = connection.prepareStatement(query);
            preparedStmt.setDate(1,theDay);
            preparedStmt.setString(2,theFlight);
            ResultSet result = preparedStmt.executeQuery();
            while(result.next()) {
        try {
            passengerName.add(result.getString("passenger"));
        } catch (SQLException ex) {
            Logger.getLogger(Booking.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
        } catch (SQLException ex) {
            Logger.getLogger(Booking.class.getName()).log(Level.SEVERE, null, ex);
        }

        toAdd = new String[passengerName.size()] [1];
        for (int i = 0; i < passengerName.size(); i++) {
            toAdd[i] [0] = passengerName.get(i);
        }
        String [] colNames = new String[] {"Passenger"};
        return new DefaultTableModel(toAdd,colNames);
    
    
}
public static int cancelBooking(String passenger, Date date)
{
    int results = 0;
        ResultSet resultSet = null;
        
        try
        {
            PreparedStatement preparedStmt = connection.prepareStatement("DELETE from booking where PASSENGER = ? and DATE = ?"); 
            preparedStmt.setString(1, passenger); preparedStmt.setDate(2, date); 
            results = preparedStmt.executeUpdate();
            

        }
       
        catch (SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return results;

}
public static int getBooking(String passengerName, Date dates) {
        ResultSet resultSet = null;
        int seats = 0;

        try {
           
            getBooking = connection.prepareStatement("SELECT * FROM Booking where (passenger,Date) =(?,?)");
            getBooking.setString(1, passengerName); 
            getBooking.setDate(2, dates);
            resultSet = getBooking.executeQuery();

            resultSet.next();
            seats=resultSet.getInt("Flight");
            
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } 
        return seats;
    }

    public static int bookFlight(Date flightDate, String flightType, String passengerName, Timestamp position) {
     int result = 0;

        try
        {
            addBooking = connection.prepareStatement("INSERT INTO BOOKING (passenger,date, flight, timestamp) VALUES (?,?,?,?)");
            addBooking.setString(1, passengerName);
            addBooking.setDate(2, flightDate);
            addBooking.setString(3, flightType);
            addBooking.setTimestamp(4, position);
            
                    
            result = addBooking.executeUpdate();
        }
        catch (SQLException sqlException)
        {
            sqlException.printStackTrace();
            
        }
        
        return result;
    }

    public static String getPassengerBooking(String cancel_Passenger, Date cancel_Date) {
        String  results = null;
        ResultSet resultSet = null;
        
        try
        {
            selectStatus=connection.prepareStatement("SELECT Flight FROM BOOKING where Passenger = ? and DATE = ?");
            selectStatus.setString(1, cancel_Passenger);
            selectStatus.setDate(2, cancel_Date);
            resultSet = selectStatus.executeQuery();
            
            
            if (resultSet.next())
            {
                results= resultSet.getString("flight");
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



