package part2;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jamie
 */
public class FilmTheatreApp 
{
    static Scanner scan;
    private FilmTheatre filmTheatre;
    private User user;
    private Movie selectedMovie;
    private HashMap<AgeGroup, Integer> numberOfPeople;
    private int numberOfTickets;

    public FilmTheatreApp() {
        this.scan = new Scanner(System.in);
        this.filmTheatre = new FilmTheatre();
        this.numberOfPeople = new HashMap();
        this.numberOfTickets = 0;
    }

    /*
    * This is the main method of the program. It creates a new instance of FilmTheatreApp, 
    * calls the createUser method to create a new user, numberOfPeople method to get the number of people, 
    * showMovies method to display the available movies, and finally calls the bookMovie method to book the 
    * selected movie and number of seats.
     */
    public static void main(String[] args) {
        FilmTheatreApp fta = new FilmTheatreApp();

        fta.createUser();
        fta.numberOfPeople();
        fta.showMovies();
        fta.bookMovie();
    }

    /*
    * This method prompts the user to enter their name and membership status. 
    * It creates a new instance of User class based on the input.
     */
    public void createUser() {
        String userInput = "";
        String name = "";

        System.out.println("Welcome to Film Theatre App. What is your name?");
        name = scan.nextLine();

        System.out.println("Would you a like to be a member? (Y, N)");
        userInput = scan.nextLine();

        while (!userInput.equalsIgnoreCase("Y") && !userInput.equalsIgnoreCase("N")) {
            System.out.println("Invalid input. Please press Y or N.");
            userInput = scan.nextLine();
        }

        if (userInput.equalsIgnoreCase("Y")) {
            System.out.println("Would you like to pay for your membership? (Y, N)");
            userInput = scan.nextLine();

            while (!userInput.equalsIgnoreCase("Y") && !userInput.equalsIgnoreCase("N")) {
                System.out.println("Invalid input. Please press Y or N.");
                userInput = scan.nextLine();
            }

            if (userInput.equalsIgnoreCase("Y")) {
                this.user = new User(name, Membership.REWARDS_VIP);
            } else if (userInput.equalsIgnoreCase("N")) {
                this.user = new User(name, Membership.REWARDS_MEMBER);
            }
        } else if (userInput.equalsIgnoreCase("N")) {
            this.user = new User(name, Membership.NON_MEMBER);
        }
    }

    /*
    * This method displays all the movies that are currently available to watch.
     */
    public void showMovies() {
        System.out.println("Currently showing movies");

        this.filmTheatre.currentMovies();
    }

    /*
    * This method prompts the user to enter the number of people attending the movie for each age group. 
    * It stores the number of people in a HashMap with the corresponding age group as the key.
     */
    public void numberOfPeople() {
        String userInput = "";
        int number = 0;
        System.out.println("How many people?");

        for (AgeGroup ageGroup : AgeGroup.values()) {
            System.out.print(ageGroup.name() + ": ");
            userInput = scan.nextLine();

            try {
                number = Integer.parseInt(userInput);
            } catch (NumberFormatException e) {
                System.out.println("Incorrect input. Please try again");
                userInput = scan.nextLine();
                number = Integer.parseInt(userInput);
            }

            this.numberOfPeople.put(ageGroup, number);
            this.numberOfTickets += number;
        }
    }

    /*
    * This method allows the user to book a movie. 
    * It prompts the user to select a movie and a cinema, and then choose seats for the selected movie. 
    * It also calls the detailedTicket method to print a detailed ticket for the booked movie.
     */
    public void bookMovie() {
        ArrayList<String> seats = new ArrayList();

        System.out.println("\nPlease select a movie by number.");
        int movie = scan.nextInt();

        while (movie < 0 || movie > this.filmTheatre.getMovies().getSize()) {
            System.out.println("Please input a valid number.");
            movie = scan.nextInt();
        }

        System.out.println("Please select a time by selecting cinema number.");
        this.selectedMovie = this.filmTheatre.getMovies().getMovie(movie);
        this.filmTheatre.showingTime(LocalDate.now().plusDays(1), selectedMovie);
        int cinema = scan.nextInt();

        while ((cinema < 0 || cinema > this.filmTheatre.getCinemas().length)
                || (!this.filmTheatre.getCinema(cinema).containsMovie(selectedMovie))) {
            System.out.println("Invalid input. Please try again");
            cinema = scan.nextInt();
        }

        scan.nextLine();

        System.out.println("Please select a seat");

        for (int i = 0; i < this.numberOfTickets; i++) {
            String temp = "";

            this.filmTheatre.getCinema(cinema).printSeats();

            System.out.print("Row: ");
            String row = scan.nextLine().toLowerCase();

            char rowInChar = row.charAt(0);

            while ((int) rowInChar < 97
                    || (int) rowInChar >= this.filmTheatre.getCinema(movie).getRow() + 97) {
                System.out.println("Invalid row. Please try again");
                row = scan.nextLine();

                rowInChar = row.charAt(0);
            }

            temp += row.toUpperCase();

            System.out.print("Column: ");
            int column = scan.nextInt();

            while (column < 0 || column > this.filmTheatre.getCinema(movie).getColumn()) {
                scan.nextLine();

                System.out.println("Invalid column. Please try again");
                column = scan.nextInt();
            }

            temp += Integer.toString(column);
            seats.add(temp);

            this.filmTheatre.getCinema(cinema).bookSeat((int) rowInChar - 97, column - 1);

            scan.nextLine();
        }

        this.filmTheatre.getCinema(cinema).printSeats();

        //this.detailedTicket(cinema, seats);
    }

    /*
    * This method calculates the ticket price 
    * based on the user's membership status and the number of people attending the movie.
     */
//    public String calculateTicket() {
//        String output = "";
//
//        if (this.user.getMembership().equals(Membership.NON_MEMBER)) {
//            NonMemberTicket ticket = new NonMemberTicket(this.numberOfPeople, this.selectedMovie.getMovieType());
//
//            output += ticket.printTicket();
//        }
//        if (this.user.getMembership().equals(Membership.REWARDS_MEMBER)) {
//            RewardsMemberTicket ticket = new RewardsMemberTicket(this.numberOfPeople, this.selectedMovie.getMovieType());
//            this.user.updatePointBalance((int) ticket.getTotalPrice());
//
//            output += ticket.printTicket();
//        }
//        if (this.user.getMembership().equals(Membership.REWARDS_VIP)) {
//            RewardsVIPTicket ticket = new RewardsVIPTicket(this.numberOfPeople, this.selectedMovie.getMovieType());
//            this.user.updatePointBalance((int) (ticket.getTotalPrice() * 2));
//            output += ticket.printTicket();
//        }
//
//        return output;
//    }

    /*
    * This method prints a detailed ticket for the selected movie with information such as 
    * movie name, cinema number, show time, seat numbers, and ticket price. 
    * It also writes the ticket details to a file
     */
//    
}
