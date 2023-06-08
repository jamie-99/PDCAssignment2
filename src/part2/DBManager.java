package part2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jamie
 */
public class DBManager extends Observable
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
    
    public Data checkCredential(String userName, String password)
    {
        Data data = new Data();
        
        String query = "SELECT username, password FROM Users WHERE username = '" + userName + "'";
        
        ResultSet rs = this.queryDB(query);
        
        try 
        {   
            if (rs.next())
            {
                String pass = rs.getString("password");
                
                if (password.compareTo(pass) == 0)
                {
                    data.setSignInFlag(true);
                    System.out.println("Sign in successful");
                }
            }
            else
            {
                System.out.println("The user does not exist.");
            }
        } 
        catch (SQLException ex) 
        {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return data;
    }
    
    public ArrayList<Movie> retrieveMoviesFromDB()
    {
        ArrayList<Movie> movies = new ArrayList<>();
        
        String query = "SELECT Title, MovieType, Duration FROM Movie";
        
        ResultSet rs = this.queryDB(query);
        
        try 
        {
            while (rs.next())
            {
                String title = rs.getString("Title");
                int duration = Integer.parseInt(rs.getString("Duration"));
                String movieType = rs.getString("MovieType");
                
                Movie movie = new Movie(title, duration);
                movie.setMovieType(movieType);
                movies.add(movie);
            }
        } 
        catch (SQLException ex) 
        {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return movies;
    }
    
    public String[] getMovieSchedule(String title) 
    {
        String[] schedule = new String[3];
        
        String query = "SELECT c1.Time AS Cinema1_Time, c2.Time AS Cinema2_Time, c3.Time AS Cinema3_Time " +
                    "FROM Movie m " +
                    "LEFT JOIN Cinema1 c1 ON m.MovieID = c1.MovieID " +
                    "LEFT JOIN Cinema2 c2 ON m.MovieID = c2.MovieID " +
                    "LEFT JOIN Cinema3 c3 ON m.MovieID = c3.MovieID " +
                    "WHERE m.Title = '" + title + "'";
        
        ResultSet rs = this.queryDB(query);
        
        try 
        {
            while (rs.next())
            {
                int index = 0;
                
                String cinema1Time = rs.getString("Cinema1_Time");
                
                if (cinema1Time != null)
                {
                    schedule[index] = cinema1Time;
                    index++;
                }
                
                String cinema2Time = rs.getString("Cinema2_Time");
                
                if (cinema2Time != null)
                {
                    schedule[index] = cinema2Time;
                    index++;
                }
                
                String cinema3Time = rs.getString("Cinema3_Time");
                if (cinema3Time != null)
                {
                    schedule[index] = cinema3Time;
                }
            }
            
        } 
        catch (SQLException ex) 
        {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return schedule;
    }
    
    public double getDiscountRate(int membershipID)
    {
        double discountRate = 0.0;
        
        String query = "SELECT m.discountRate FROM Membership m JOIN Users u ON m.MembershipID = u.MembershipID WHERE u.MembershipID = " + membershipID;
        
        ResultSet rs = this.queryDB(query);
        
        try 
        {
            if (rs.next())
            {
                discountRate = rs.getDouble("DiscountRate");
            }
        } 
        catch (SQLException ex) 
        {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
        }

        return discountRate;
    }
    
    public int getMembershipID(String username)
    {
        int membershipID = 0;
        
        String query = "SELECT MembershipID FROM Users WHERE username = '" + username + "'";
        
        ResultSet rs = this.queryDB(query);
        
        try 
        {
            if (rs.next())
            {
                membershipID = rs.getInt("MembershipID");
                System.out.println("MembershipID for username '" + username + "': " + membershipID);
            } 
            else 
            {
                System.out.println("No MembershipID found for username '" + username + "'");
            }
        } 
        catch (SQLException ex) 
        {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return membershipID;
    }
    
    public int getUserID(String username)
    {
        String query = "SELECT UserID FROM Users WHERE username = '" + username + "'";
        int userID = 0;
        
        ResultSet rs = this.queryDB(query);
        
        try 
        {
            if (rs.next())
            {
                userID = rs.getInt("UserID");
                System.out.println("UserID for username '" + username + "': " + userID);
            } 
            else 
            {
                System.out.println("No UserID found for username '" + username + "'");
            }
        } 
        catch (SQLException ex) 
        {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return userID;
    }
    
    public int getMovieID(String title)
    {
        String query = "SELECT MovieID FROM Movie WHERE Title = '" + title + "'";
        int movieID = 0;
        
        ResultSet rs = this.queryDB(query);
        
        try 
        {
            if (rs.next())
            {
                movieID = rs.getInt("MovieID");
                System.out.println("MovieID for movie '" + title +"': " + movieID);
            }
            else
            {
                System.out.println("No MovieID found for title '" + title + "'");
            }
        } 
        catch (SQLException ex) 
        {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return movieID;
    }
    
    public ResultSet queryDB(String sql) 
    {
        Connection connection = this.conn;
        Statement statement = null;
        ResultSet resultSet = null;

        try 
        {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
        } 
        catch (SQLException ex) 
        {
            System.out.println(ex.getMessage());
        }
        
        return resultSet;
    }
    
    public void updateDB(String sql) 
    {
        Connection connection = this.conn;
        Statement statement = null;
        ResultSet resultSet = null;

        try 
        {
            statement = connection.createStatement();
            statement.executeUpdate(sql);
        } 
        catch (SQLException ex) 
        {
            System.out.println(ex.getMessage());
        }
    }
    
    public Connection getConnection()
    {
        return this.conn;
    }
}