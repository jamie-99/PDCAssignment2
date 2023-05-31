package part2;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jamie
 */
public class Movie 
{
    private final DBManager dbmanager;
    private final Connection conn;
    private Statement statement;
    
    public Movie()
    {
        this.dbmanager = new DBManager();
        this.conn = this.dbmanager.getConnection();
    }
    
    public void connectDataBase()
    {
        try 
        {
            this.statement = conn.createStatement();

            this.checkTableExists("Movie");
            
            this.statement.addBatch("CREATE TABLE Movie (MovieID int, Title varchar(50), Type varchar(10), Duration int)");
            this.statement.addBatch("INSERT INTO Movie VALUES (1, 'Star Wars', '3D', 130), \n"
                    + "(2, 'Minions', 'Normal', 89), \n"
                    + "(3, 'Avengers', 'IMAX', 190), \n"
                    + "(4, 'Super Mario', '4D', 95), \n"
                    + "(5, 'John Wick', 'IMAX', 160)");
        } 
        catch (SQLException ex) 
        {
            Logger.getLogger(Movie.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void checkTableExists(String name) 
    {
        DatabaseMetaData dbmd;

        try 
        {
            dbmd = this.conn.getMetaData();

            String[] types = {"TABLE"};
            this.statement = this.conn.createStatement();
            ResultSet rs = dbmd.getTables(null, null, null, types);

            while (rs.next()) 
            {
                String tableName = rs.getString("TABLE_NAME");
                System.out.println(tableName);
                
                if (tableName.equalsIgnoreCase(name)) 
                {
                    this.statement.executeUpdate("Drop table " + name);
                    System.out.println("Table " + name + " already exists. " + name + " has been deleted.");
                    break;
                }
            }
        } 
        catch (SQLException ex) 
        {
            Logger.getLogger(Movie.class.getName()).log(Level.SEVERE, null, ex);
        }

        
    }
    
    public static void main(String[] args) {
        // TODO code application logic here
    }
    
}