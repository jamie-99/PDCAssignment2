package part2;

import javax.swing.JLabel;

/*
Movie class stores title, duration, movieType, and movieLabel variables.
*/
public class Movie 
{
    private String title;
    private int duration;
    private MovieType movieType;
    private JLabel movieLabel;
    
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
    
    public String movieDetail()
    {
        return "Movie type: " + this.getMovieType().getMovieType() + ", Duration: " + this.getDuration() + " minutes";
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
    
    /*
    This method sets the movie type of the movie based on the provided string value. 
    It iterates over the MovieType enum values and matches the case-insensitive movie type string to find the corresponding enum value.
    */
    public void setMovieType(String movieType)
    {
        for (MovieType mt : MovieType.values())
        {
            if (mt.getMovieType().equalsIgnoreCase(movieType))
            {
                this.movieType = mt;
            }
        }
    }
    
    public void setMovieType(MovieType movieType)
    {
        this.movieType = movieType;
    }
    
    public void setMovieLabel(JLabel movieLabel)
    {
        this.movieLabel = movieLabel;
    }
    
    public JLabel getMovieLabel()
    {
        return this.movieLabel;
    }
}