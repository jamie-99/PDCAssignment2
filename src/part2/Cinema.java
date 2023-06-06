package part2;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jamie
 */
public class Cinema 
{
    private final DBManager dbManager;
    private final Connection conn;
    private Statement statement;
    
    private LinkedList<TimeSlot> timeSlots;
    private Movies movies;
    private int cinemaNumber, capacity, row, column;
    private boolean[][] seats;

    /*
    * This constructor takes two arguments: an integer called "cinemaNumber" and 
    * an integer called "capacity". This constructor initializes several instance variables, 
    * including the "movies" object, the "timeSlots" list (by calling a private method 
    * called "readCinemaScheduleFromFile"), and the "seats" array 
    * (by creating a 2D boolean array and initializing it to "true" for all seats). 
    * It also calculates the number of rows and columns based on the capacity.
    */
    public Cinema(int cinemaNumber, int capacity) 
    {
        this.dbManager = new DBManager();
        this.conn = this.dbManager.getConnection();
        this.connectDataBase();
        
        this.cinemaNumber = cinemaNumber;
        this.capacity = capacity;

        this.movies = new Movies();

        this.timeSlots = new LinkedList();
        this.readCinemaScheduleFromFile();

        this.row = (int) Math.sqrt(this.capacity);
        this.column = (int) Math.sqrt(this.capacity);

        this.seats = new boolean[this.row][this.column];

        for (int i = 0; i < this.row; i++) {
            for (int j = 0; j < this.column; j++) {
                this.seats[i][j] = true;
            }
        }
    }

    /*
    * This method prints the current state of the seats in the cinema. 
    * It prints out a grid with letters (representing rows) and numbers (representing columns) along the edges, 
    * and uses brackets to represent whether a seat is available (an empty space) or taken (an X).
    */
    public void printSeats() {
        for (int columnNumber = 0; columnNumber <= this.column; columnNumber++) {
            if (columnNumber == 0) {
                System.out.print("  ");
            } else if (columnNumber == this.column) {
                System.out.print(" " + columnNumber + "\n");
            } else {
                System.out.print(" " + columnNumber + " ");
            }
        }

        char alphabet = 'A';

        for (int i = 0; i < this.row; i++) {
            System.out.print(alphabet + " ");

            for (int j = 0; j < this.column; j++) {
                if (this.seats[i][j]) {
                    System.out.print("[ ]");
                } else {
                    System.out.print("[X]");
                }
            }

            alphabet++;
            System.out.print("\n");
        }
    }

    /*
    * It books a seat in the cinema, given its row and column numbers. 
    * If the seat is already taken, it prints out an error message.
    */
    public void bookSeat(int row, int column) {
        if (!this.seats[row][column]) {
            System.out.println("The seat is already taken. Please select another seat.");
        } else {
            this.seats[row][column] = false;
        }
    }

    /*
    * It checks if a given movie is currently being shown in the cinema. 
    * It returns true if the movie is being shown, and false otherwise.
    */
    public boolean containsMovie(Movie movie) {
        boolean flag = false;

        for (TimeSlot timeSlot : this.timeSlots) {
            if (timeSlot.getMovie().getTitle().equalsIgnoreCase(movie.getTitle())) {
                flag = true;
            }
        }

        return flag;
    }

    /*
    * It adds a new time slot to the cinema's schedule.
    */
    public void addTimeSlot(TimeSlot timeSlot) {
        this.timeSlots.add(timeSlot);
    }

    /*
    * It removes time slot from the cinema's timetable.
    */
    public boolean removeTimeSlot(TimeSlot timeSlot) {
        return this.timeSlots.remove(timeSlot);
    }

    /*
    * This method uses the date from the input parameter to retrun the timeslots.
    */
    public LinkedList<TimeSlot> getTimeSlots(LocalDate date) {
        LinkedList<TimeSlot> output = new LinkedList();

        for (TimeSlot ts : this.timeSlots) {
            if (ts.getDate().equals(date)) {
                output.add(ts);
            }
        }

        return output;
    }

    public String toString(LinkedList<TimeSlot> input) {
        String output = "Cinema " + this.getCinemaNumber() + "\n";

        for (TimeSlot ts : input) {
            output += ts;
        }

        return output;
    }

    /*
    * A  method that finds a movie in the Movies object with a given title. 
    * It returns the Movie object if it is found, and null otherwise.
    */
    private Movie findMovie(String title) {
        for (Movie movie : this.movies.getMovies()) {
            if (movie.getTitle().equalsIgnoreCase(title)) {
                return movie;
            }
        }

        return null;
    }

    /*
    * A method that reads the cinema schedule from a file.
    */
    private void readCinemaScheduleFromFile() {
        BufferedReader br = null;

        try {
            br = new BufferedReader(new FileReader("./resources/cinema" + this.getCinemaNumber() + ".txt"));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Cinema.class.getName()).log(Level.SEVERE, null, ex);
        }
        String line = "";
        try {
            while ((line = br.readLine()) != null) {
                String str[] = line.split("-");

                String time[] = str[0].split(":");
                LocalTime startTime = LocalTime.of(Integer.parseInt(time[0]), Integer.parseInt(time[1]));

                this.addTimeSlot(new TimeSlot(this.findMovie(str[1]), LocalDate.now().plusDays(1), startTime));
            }
        } catch (IOException ex) {
            Logger.getLogger(Cinema.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException ex) {
                    Logger.getLogger(Cinema.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    /*
    * Method that writes the cinema schedule for a given date to a file.
    */
    public void writeCinemaScheduleToFile(LocalDate date) {
        PrintWriter pw = null;

        try {
            pw = new PrintWriter("./resources/cinema" + this.getCinemaNumber() + ".txt");

            for (TimeSlot ts : this.getTimeSlots(date)) {
                pw.println(ts.toString());
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Cinema.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (pw != null) {
                pw.close();
            }
        }
    }

    public int getRow() {
        return this.row;
    }

    public int getColumn() {
        return this.column;
    }

    public int getCinemaNumber() {
        return cinemaNumber;
    }
    
    public void connectDataBase()
    {
        try 
        {
            this.statement = this.conn.createStatement();
            
            this.checkTableExists("Cinema");
            
            this.statement.addBatch("CREATE TABLE Cinema (CinemaID int NOT NULL, )");
        } 
        catch (SQLException ex) 
        {
            Logger.getLogger(Cinema.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void checkTableExists(String name)
    {
        try 
        {
            DatabaseMetaData dbmd = this.conn.getMetaData();
            
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
            Logger.getLogger(Cinema.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
