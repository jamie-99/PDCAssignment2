package part2;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author jamie
 */
public class TimeSlot 
{
    private Movie movie;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;

    /*
    * A constructor that takes a Movie, a LocalDate, and a LocalTime as arguments. 
    * The constructor first checks if the start time is valid (i.e., after 9am) and throws an 
    * IllegalArgumentException if it's not. Then it calculates the end time by adding the duration 
    * of the movie to the start time, and checks if the end time is valid (i.e., before 10pm) and 
    * throws an IllegalArgumentException if it's not. If both the start time and end time are valid, 
    * the constructor sets the start and end times as instance variables.
    */
    public TimeSlot(Movie movie, LocalDate date, LocalTime startTime) 
    {
        this.date = date;
        this.movie = movie;

        if (!this.isValidStartTime(startTime)) 
        {
            throw new IllegalArgumentException("Invalid start time.");
        } 
        else 
        {
            this.startTime = startTime;

            LocalTime temp = this.startTime.plusMinutes(this.movie.getDuration());

            if (!this.isValidEndTime(temp)) 
            {
                throw new IllegalArgumentException("Invalid end time.");
            } 
            else 
            {
                this.endTime = temp;
            }
        }
    }

    private boolean isValidStartTime(LocalTime startTime) 
    {
        return startTime.getHour() >= 9;
    }

    private boolean isValidEndTime(LocalTime endTime) 
    {
        return endTime.getHour() <= 22;
    }

    @Override
    public String toString() 
    {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm");
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        return this.date.format(dateFormatter) + "\n"
                + this.startTime.format(timeFormatter) + " - " + this.endTime.format(timeFormatter);
    }

    public LocalTime getStartTime() 
    {
        return this.startTime;
    }

    public LocalTime getEndTime() 
    {
        return this.endTime;
    }

    public Movie getMovie() 
    {
        return this.movie;
    }

    public LocalDate getDate() 
    {
        return this.date;
    }
}
