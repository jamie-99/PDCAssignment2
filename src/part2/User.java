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
public class User 
{
    private final DBManager dbManager;
    private final Connection conn;
    private Statement statement;
    
    public User()
    {
        this.dbManager = new DBManager();
        this.conn = this.dbManager.getConnection();
        
        this.connectDataBase();
    }
    
    public void connectDataBase()
    {
        try 
        {
            this.statement = this.conn.createStatement();
            
            this.checkTableExists("User");
            
            this.statement.addBatch("CREATE TABLE User (UserID int NOT NULL, UserName varchar(50), Password varchar(20), "
                    + "FisrtName varchar(50), LastName varchar(50))");
            this.statement.addBatch("INSERT INTO User VALUES (1, 'jamie', 'password', 'Jamie', 'Lee')");
            this.statement.addBatch("ALTER TABLE User ADD PRIMARY KEY (UserID)");
            this.statement.executeBatch();
        } 
        catch (SQLException ex) 
        {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void closeConnection() 
    {
        this.dbManager.closeConnections();
    }
    
    public static void main(String[] args) 
    {
        User user = new User();
    }
}
