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
public class Movie 
{
    private String title;
    private int duration;
    private MovieType movieType;
    
    public Movie(String title, int duration)
    {
        this.title = title;
        this.duration = duration;
    }
    
    @Override
    public String toString()
    {
        return this.title + "(" + this.getMovieType().getMovieType() + ") - " + this.getDuration() + " minutes";
    }

    public String getTitle() 
    {
        return this.title;
    }
    
    public int getDuration() 
    {
        return this.duration;
    }
    
    public MovieType getMovieType()
    {
        return this.movieType;
    }
    
    public void setMovieType(MovieType movieType)
    {
        this.movieType = movieType;
    }
}