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
import javax.swing.table.DefaultTableModel;

public class Passenger {
    private static PreparedStatement addPassenger;
    private static PreparedStatement passengerSelect;
    private static Connection connection = DBConnection.getConnection();

    public static ArrayList< String > getPassenger()
    {
        ArrayList< String > results = null;
        ResultSet resultSet = null;
        
        try
        {
            passengerSelect=connection.prepareStatement("SELECT * FROM Passenger");
            resultSet = passengerSelect.executeQuery();
            results = new ArrayList< String >();
            
            while (resultSet.next())
            {
                results.add(resultSet.getString("NAME"));
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
    
    public static int addPassenger (String passengerName)
    {
        int result = 0;

        try
        {
            addPassenger = connection.prepareStatement("INSERT INTO Passenger (name) VALUES (?)");
            addPassenger.setString(1, passengerName);
            
                    
            result = addPassenger.executeUpdate();
        }
        catch (SQLException sqlException)
        {
            sqlException.printStackTrace();
            
        }
        
        return result;
    }
public static DefaultTableModel settingJTable (String passengerName) throws SQLException {

    String[][] toAdd;
    ArrayList<String> flightWaitlist = new ArrayList<>();
    ArrayList<String> flightBook = new ArrayList<>();
    ArrayList<String> dateBook = new ArrayList<>();
    ArrayList<String> dateWait = new ArrayList<>();

    String query = "select flight from booking where passenger = ?";
    PreparedStatement preparedStmt;
    preparedStmt = connection.prepareStatement(query);
    preparedStmt.setString(1,passengerName);
    ResultSet result = preparedStmt.executeQuery();
    while(result.next()) {
        flightBook.add(result.getString("flight"));
    }
    
    query = "select date from booking where passenger = ?";
    preparedStmt = connection.prepareStatement(query);
    preparedStmt.setString(1,passengerName);
    result = preparedStmt.executeQuery();
    while(result.next()) {
        dateBook.add(result.getString("date"));
    }
    
    query = "select date from waitlist where passenger = ?";
    preparedStmt = connection.prepareStatement(query);
    preparedStmt.setString(1,passengerName);
    result = preparedStmt.executeQuery();
    while(result.next()) {
        dateWait.add(result.getString("date"));
    }
    
    query = "select flight from waitlist where passenger = ? ";
    preparedStmt = connection.prepareStatement(query);
    preparedStmt.setString(1,passengerName);
    result = preparedStmt.executeQuery();
    while(result.next()) {
        flightWaitlist.add(result.getString("flight"));
    }
        
    toAdd = new String[flightBook.size() + flightWaitlist.size()] [3];
    for (int i = 0; i < flightBook.size(); i++) {
        toAdd[i] [0] = flightBook.get(i);
        toAdd[i] [1] = "Book";
        toAdd[i] [2] = dateBook.get(i);
    }
        
    for (int i = 0; i < flightWaitlist.size(); i++) {
        toAdd[i] [0] = flightWaitlist.get(i);
        toAdd[i] [1] = "Waitlist";
        toAdd[i] [2] = dateWait.get(i);
    }
    String [] colNames = new String[] {"Flight", "Status","Date"};
    return new DefaultTableModel(toAdd,colNames);   
}    
}
