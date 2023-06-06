package part2;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jamie
 */
public class User extends Observable
{
    private final DBManager dbManager;
    private final Connection conn;
    private Statement statement;
    
    private String userName;
    private String password;
    private String name;
    private Membership membership;
    private int pointBalance;    
    
    public User(String name, Membership membership)
    {
        this.name = name;
        this.membership = membership;
        this.pointBalance = 0;
        
        this.dbManager = new DBManager();
        this.conn = this.dbManager.getConnection();
        
        this.connectDataBase();
    }
    
    public User()
    {
        this.dbManager = new DBManager();
        this.conn = this.dbManager.getConnection();
    }
    
    public void updatePointBalance(int inputPoints)
    {
        this.pointBalance += inputPoints;
    }
    
//    @Override
//    public String toString()
//    {
//        return this.name + "(" + this.membership.getMembership() + ")";
//    }

    public String getName() 
    {
        return name;
    }

    public Membership getMembership() 
    {
        return membership;
    }
        
    public int getPointBalance() 
    {
        return pointBalance;
    }
    
    public void connectDataBase()
    {
        try 
        {
            this.statement = this.conn.createStatement();
            
            this.checkTableExists("Users");
            
            this.statement.addBatch("CREATE TABLE Users (UserID int NOT NULL, UserName varchar(50), Password varchar(20), "
                    + "FirstName varchar(50), LastName varchar(50))");
            this.statement.addBatch("INSERT INTO Users VALUES (1, 'jamie', 'password', 'Jamie', 'Lee')");
            this.statement.addBatch("ALTER TABLE Users ADD PRIMARY KEY (UserID)");
            this.statement.executeBatch();
        } 
        catch (SQLException ex) 
        {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public Data checkName(String userName, String password)
    {
        Data data = new Data();
        
        String query = "SELECT username, password FROM Users WHERE username = '" + userName + "'";
        
        try 
        {
            ResultSet rs = this.statement.executeQuery(query);
            
            while (rs.next())
            {
                String pass = rs.getString("password");
                
                if (password.compareTo(pass) == 0)
                {
                    data.setSignInFlag(true);
                }
            }
            
            if (!data.getSignInFlag())
            {
                System.out.println("The user does not exist.");
            }
        } 
        catch (SQLException ex) 
        {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return data;
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
        //User user = new User();
    }
}
