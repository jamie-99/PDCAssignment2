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
public class Cinema 
{
    private final DBManager dbManager;
    private final Connection conn;
    private Statement statement;
    
    public Cinema()
    {
        this.dbManager = new DBManager();
        this.conn = this.dbManager.getConnection();
    }
    
    public void connectDataBase()
    {
        try 
        {
            this.statement = this.conn.createStatement();
            
            this.checkTableExists("Cinema");
            
            this.statement.addBatch("CREATE TABLE Cinema (CinemaID int NOT NULL, )");
        } 
        catch (SQLException ex) 
        {
            Logger.getLogger(Cinema.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void checkTableExists(String name)
    {
        try 
        {
            DatabaseMetaData dbmd = this.conn.getMetaData();
            
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
            Logger.getLogger(Cinema.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
