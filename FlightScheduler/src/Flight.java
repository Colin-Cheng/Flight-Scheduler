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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Flight {

    private static PreparedStatement addFlight;
    private static PreparedStatement getFlight;
    private static PreparedStatement getSeats;
    private static PreparedStatement passengersBook;
    private static PreparedStatement DatesBook;
    private static PreparedStatement passengersWaitList;
    private static PreparedStatement cancelFlight;
    private static PreparedStatement deletePassengerB;
    private static PreparedStatement deletePassengerW;
    private static Connection connection = DBConnection.getConnection();

    public Flight() {
        try {
            getFlight = connection.prepareStatement("SELECT * FROM FLIGHTS");
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            System.exit(1);
        }
    }

    public static List< String> getFlight() {
        List<String> results = null;
        ResultSet resultSet = null;

        try {
            getFlight = connection.prepareStatement("SELECT * FROM FLIGHTS");
            resultSet = getFlight.executeQuery();
            results = new ArrayList< String>();

            while (resultSet.next()) {
                results.add(resultSet.getString("NAME").toString());
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } 
        return results;
    }
    
    public static int getSeats(String flight) {
        ResultSet resultSet = null;
        int seats = 0;

        try {
           
            getSeats = connection.prepareStatement("SELECT * FROM FLIGHTS where NAME = ?");
            getSeats.setString(1, flight);
            resultSet = getSeats.executeQuery();

            resultSet.next();
            seats=resultSet.getInt("SEATS");
            
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } 
        return seats;
    }
    public static int addFlight (String flightName,int numseats)
    {
        int result = 0;

        try
        {
            addFlight = connection.prepareStatement("INSERT INTO Flights (name, seats) VALUES (?,?)");
            addFlight.setString(1, flightName);
            addFlight.setInt(2, numseats);
            
                    
            result = addFlight.executeUpdate();
        }
        catch (SQLException sqlException)
        {
            sqlException.printStackTrace();
            
        }
        
        return result;
    }

    public static int deleteFlight(String flightName)
    {
        int result = 0;
        try
        {
            cancelFlight = connection.prepareStatement("DELETE FROM FLIGHTS WHERE NAME = ?");
            cancelFlight.setString(1, flightName);
            result = cancelFlight.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return result;
    }
    public static List<String> getPassengersBook(String flight)
    {
        List<String> bookedNames = null;
        ResultSet resultSet = null; 
        try
        {
            bookedNames = new ArrayList< String>();
            passengersBook = connection.prepareStatement("SELECT Passenger FROM BOOKING WHERE FLIGHT = ?");
            passengersBook.setString(1, flight); 
            resultSet = passengersBook.executeQuery(); 
            while (resultSet.next())
            {
                bookedNames.add(resultSet.getString("Passenger"));
            }
        }
        catch (SQLException sqlException)
        {
           sqlException.printStackTrace();
        }
       return bookedNames;
    }
    
    public static List<String> getPassengersDate(String flight)
    {
        List<String> bookedDates = null;
        ResultSet resultSet = null; 
        try
        {
            bookedDates = new ArrayList< String>();
            DatesBook = connection.prepareStatement("SELECT DATE FROM BOOKING WHERE FLIGHT = ?");
            DatesBook.setString(1, flight); 
            resultSet = DatesBook.executeQuery(); 
            while (resultSet.next())
            {
                bookedDates.add(resultSet.getString("DATE"));
            }
        }
        catch (SQLException sqlException)
        {
           sqlException.printStackTrace();
        }
       return bookedDates;
    }
    public static List<String> getPassengersWaitList(String flight)
    {
        List<String> waitListNames = null;
        ResultSet resultSet = null; 
        try
        {
            waitListNames = new ArrayList< String>();
            passengersWaitList.setString(1, flight); 
            resultSet = passengersWaitList.executeQuery(); 
            while (resultSet.next())
            {
                waitListNames.add(resultSet.getString("Passenger"));
            }
        }
        catch (SQLException sqlException)
        {
           sqlException.printStackTrace();
        }
        return waitListNames;
    }
       public static int deleteBook(String flightName)
    {
        int result = 0;
        try
        {
            deletePassengerB = connection.prepareStatement("DELETE FROM BOOKING WHERE FLIGHT = ?");
            deletePassengerB.setString(1, flightName);
            result = deletePassengerB.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return result;
    }
       public static int deleteWaitList(String flightName)
    {
        int result = 0;
        try
        {
            deletePassengerW = connection.prepareStatement("DELETE FROM WAITLIST WHERE FLIGHT = ?");
            deletePassengerW.setString(1, flightName);
            result = deletePassengerW.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return result;
    }
}
