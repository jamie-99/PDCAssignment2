package part2;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jamie
 */
public class Movies 
{
    private final DBManager dbManager;
    private final Connection conn;
    private Statement statement;
    
    private ArrayList<Movie> movies;
    
    public Movies() 
    {
        this.dbManager = new DBManager();
        this.conn = this.dbManager.getConnection();
        this.connectDataBase();
        
        this.movies = new ArrayList();
        //this.addMovies();
    }
    
    public void connectDataBase()
    {
        try 
        {
            this.statement = this.conn.createStatement();

            this.checkTableExists("Movie");
            
            this.statement.addBatch("CREATE TABLE Movie (MovieID int NOT NULL, Title varchar(50), MovieType varchar(10), Duration int)");
            this.statement.addBatch("INSERT INTO Movie VALUES (1, 'Star Wars', '3D', 130), \n"
                    + "(2, 'Minions', 'Normal', 89), \n"
                    + "(3, 'Avengers', 'IMAX', 190), \n"
                    + "(4, 'Super Mario', '4D', 95), \n"
                    + "(5, 'John Wick', 'IMAX', 160)");
            this.statement.addBatch("ALTER TABLE Movie ADD PRIMARY KEY (MovieID)");
            this.statement.executeBatch();
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
    
    public void closeConnection() 
    {
        this.dbManager.closeConnections();
    }

    public ArrayList<Movie> getMovies() {
        return this.movies;
    }

    public Movie getMovie(int index) {
        return this.movies.get(index - 1);
    }

    public int getSize() {
        return this.movies.size();
    }
}
