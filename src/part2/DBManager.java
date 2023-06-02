package part2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jamie
 */
public class DBManager 
{
    private static final String URL = "jdbc:derby://localhost:1527/FilmTheatreDB";
    private static final String userName = "pdc";
    private static final String password = "pdc";
    
    private Connection conn;
    
    public DBManager()
    {   
        this.establishConnection();
    }
    
    public void establishConnection() 
    {
        if (this.conn == null) 
        {
            try 
            {
                this.conn = DriverManager.getConnection(URL, userName, password);
                System.out.println(URL + " connection successful");
            } 
            catch (SQLException ex) 
            {
                System.out.println(ex.getMessage());
            }
        }
    }
    
    public void closeConnections() 
    {
        if (this.conn != null) 
        {
            try 
            {
                this.conn.close();
            } 
            catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
    
    public ResultSet queryDB(String sql) {

        Connection connection = this.conn;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return resultSet;
    }
    
    public void updateDB(String sql) 
    {
        Connection connection = this.conn;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.createStatement();
            statement.executeUpdate(sql);

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public Connection getConnection()
    {
        return this.conn;
    }
}