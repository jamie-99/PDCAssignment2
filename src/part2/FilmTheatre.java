/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package part2;

import java.time.LocalDate;
import java.util.Random;

/**
 *
 * @author jamie
 */
public class FilmTheatre 
{
    private Movies movies;
    private Cinema[] cinemas;

    /*
    * The FilmTheatre class has a constructor that initializes the movies and cinemas fields.
    */
    public FilmTheatre() {
        this.movies = new Movies();
        this.cinemas = new Cinema[3];
        this.addCinema();
    }

    /*
    * The currentMovies() method prints out the current movies available in the theatre.
    */
    public void currentMovies() {
        int count = 1;
        for (Movie movie : this.movies.getMovies()) {
            System.out.println(count + ". " + movie.toString());

            count++;
        }
    }
    
    /*
    * The showingTime() method prints out the showing time of a movie on a specific date in all cinemas.
    */
    public void showingTime(LocalDate date, Movie movie) {
        for (Cinema cinema : this.cinemas) {
            System.out.println("Cinema " + cinema.getCinemaNumber());

            for (TimeSlot timeSlot : cinema.getTimeSlots(date)) {
                if (timeSlot.getMovie().getTitle().equalsIgnoreCase(movie.getTitle())) {
                    System.out.println(timeSlot.toString());
                }
            }

            System.out.print("\n");
        }
    }

    /*
    * The movieDetail() method returns the details of a movie in a specific cinema for the next day.
    */
    public String movieDetail(int cinemaNumber, Movie movie) {
        String output = "";

        for (TimeSlot timeSlot : this.getCinema(cinemaNumber).getTimeSlots(LocalDate.now().plusDays(1))) {
            if (timeSlot.getMovie().getTitle().equalsIgnoreCase(movie.getTitle())) {
                System.out.println(timeSlot.toString());
                output += timeSlot.toString();
            }
        }

        return output;
    }

    /*
    * The addCinema() method adds cinemas to the array of cinemas field.
    */
    private void addCinema() {
        Random random = new Random();

        for (int i = 0; i < this.cinemas.length; i++) {
            int capacity = 64 + random.nextInt(225 - 64 + 1);

            this.cinemas[i] = new Cinema(i + 1, capacity);
        }
    }

    public Cinema[] getCinemas() {
        return this.cinemas;
    }

    public Cinema getCinema(int cinemaNumber) {
        return this.cinemas[cinemaNumber - 1];
    }

    public Movies getMovies() {
        return this.movies;
    }
}
