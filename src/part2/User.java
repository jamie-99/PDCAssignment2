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
    private String firstName;
    private String lastName;
    private Membership membership;
    private int pointBalance;    
    
    public User()
    {
        this.dbManager = new DBManager();
        this.conn = this.dbManager.getConnection();
        this.connectDataBase();
    }
    
    public void updatePointBalance(int inputPoints)
    {
        this.setPointBalance(this.pointBalance + inputPoints);
    }
    
//    @Override
//    public String toString()
//    {
//        return this.name + "(" + this.membership.getMembership() + ")";
//    }

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
    
    public void getUserDetail(String username)
    {
        String query = "";
        
        try 
        {
            ResultSet rs = this.statement.executeQuery(query);
        } 
        catch (SQLException ex) 
        {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void addToTabel(String query)
    {
        try 
        {
            this.statement.execute(query);
        } 
        catch (SQLException ex) 
        {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public Data checkCredential(String userName, String password, User user)
    {
        Data data = new Data();
        
        String query = "SELECT username, password FROM Users WHERE username = '" + userName + "'";
        
        try 
        {
            ResultSet rs = this.statement.executeQuery(query);
            
            if (rs.next())
            {
                String pass = rs.getString("password");
                
                if (password.compareTo(pass) == 0)
                {
                    data.setSignInFlag(true);
                    System.out.println("Sign in successful");
                    
                    String dbFirstName = rs.getString("FIRSTNAME");
                    String dbLastName = rs.getString("LASTNAME");
                }
            }
            else
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
    
    public Data checkUsernameExists(String username)
    {
        Data data = new Data();
        
        String query = "SELECT username FROM users";
        
        try 
        {
            ResultSet rs = this.statement.executeQuery(query);
            
            while (rs.next())
            {
                String un = rs.getString("username");
                
                if (un.compareTo(username) == 0)
                {
                    data.setSignUpFlag(false);
                }
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
    
    public void setMembership(String membership)
    {
        for (Membership m : Membership.values())
        {
            if (membership.equalsIgnoreCase(m.getMembership()))
            {
                this.setMembership(m);
            }
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

    public void setUserName(String userName) 
    {
        this.userName = userName;
    }

    public void setPassword(String password) 
    {
        this.password = password;
    }

    public void setMembership(Membership membership) 
    {
        this.membership = membership;
    }

    public void setPointBalance(int pointBalance) 
    {
        this.pointBalance = pointBalance;
    }

    /**
     * @param fistName the fistName to set
     */
    public void setFistName(String fistName) {
        this.firstName = fistName;
    }

    /**
     * @param lastName the lastName to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
