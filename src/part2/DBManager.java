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

/*
 DBManager is responsible for managing the connection to a database and executing SQL queries.
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
    
    /*
    The establishConnection method checks if the connection is null and establishes a new connection using the provided URL, username, and password.
    */
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
    
    /*
    The checkCredential method checks the user's credentials by executing an SQL query to retrieve the username and password from the database. 
    If the credentials match, it sets the signInFlag of a Data object to true.
    */
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
    
    /*
    The retrieveMoviesFromDB method retrieves a list of movies from the database by executing an SQL query. 
    It creates Movie objects and adds them to an ArrayList<Movie>.
    */
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
    
    /*
    The getMovieSchedule method retrieves the schedule for a specific movie by executing an SQL query. 
    It returns an array of three strings representing the movie times for different cinema halls.
    */
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
    
    /*
    The getDiscountRate method retrieves the discount rate for a given membership ID by executing an SQL query.
    */
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
    
    /*
    The getMembershipID method retrieves the membership ID for a given username by executing an SQL query.
    */
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
    
    /*
    The getUserID method retrieves the user ID for a given username by executing an SQL query.
    */
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
    
    /*
    The getMovieID method retrieves the movie ID for a given movie title by executing an SQL query.
    */
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
    
    /*
    The queryDB method executes an SQL query and returns the result as a ResultSet object.
    */
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
    
    /*
    The updateDB method executes an SQL update statement, such as INSERT, UPDATE, or DELETE.
    */
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
}