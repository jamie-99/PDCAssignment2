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
        
        try 
        {
            Statement statement = this.conn.createStatement();
            ResultSet rs = statement.executeQuery(query);
            
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
    
    public Data checkUsernameExists(String username)
    {
        Data data = new Data();
        
        String query = "SELECT username FROM users";
        
        try 
        {
            Statement statement = this.conn.createStatement();
            ResultSet rs = statement.executeQuery(query);
            
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
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return data;
    }
    
    public ArrayList<Movie> retrieveMoviesFromDB()
    {
        ArrayList<Movie> movies = new ArrayList<>();
        
        String query = "SELECT Title, MovieType, Duration FROM Movie";
        
        try 
        {
            Statement statement = this.conn.createStatement();
            ResultSet rs = statement.executeQuery(query);
            
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
        
        try 
        {
            Statement statement = this.conn.createStatement();
            ResultSet rs = statement.executeQuery(query);
            
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