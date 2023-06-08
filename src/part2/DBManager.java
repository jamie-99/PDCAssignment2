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
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(Movies.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return movies;
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