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
public class Membership 
{
    private final DBManager dbManager;
    private final Connection conn;
    private Statement statement;
    
    public Membership()
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
            
            this.checkTableExists("Membership");
            
            this.statement.addBatch("CREATE TABLE Membership (MembershipID int NOT NULL, Membership varchar(50), DiscountRate float)");
            this.statement.addBatch("INSERT INTO Membership VALUES (1, 'Non Member', 1.0), \n"
                    + "(2, 'Rewards Member', 0.9), \n"
                    + "(3, 'Rewards VIP', 0.85)");
            this.statement.addBatch("ALTER TABLE Membership ADD PRIMARY KEY (MembershipID)");
            this.statement.executeBatch();
        } 
        catch (SQLException ex) 
        {
            Logger.getLogger(Membership.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(Membership.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void closeConnection() 
    {
        this.dbManager.closeConnections();
    }
    
    public static void main(String[] args) 
    {
        Membership membership = new Membership();
    }
}
