package part2;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class PrintTicket extends JFrame implements ActionListener
{
    public FilmTheatreApp fta;
    public DBManager dbManager;
    public BookSeat bookSeat;
    
    private Movie movie;
    
    private double totalPrice;
    
    private Container container;
    private JLabel titleLabel;
    private JLabel timeLabel;
    private JLabel bookedSeats;
    private JLabel movieIcon;
    private JLabel totalPriceLabel;
    private JButton finishButton = new JButton("Finish");
    
    /*
    This is the constructor of the class. It initializes a PrintTicket object with the provided fta, dbManager, and bookSeat. 
    It sets up the GUI components, including labels, buttons, and their positions, and initializes the movie, title, time, booked seats, and total price.
    */
    public PrintTicket(FilmTheatreApp fta, DBManager dbManager, BookSeat bookSeat)
    {
        this.fta = fta;
        this.dbManager = dbManager;
        this.bookSeat = bookSeat;
        this.container = new Container();
        
        this.movie = this.bookSeat.tap.movies.findMovie(this.bookSeat.tap.title);
        
        this.titleLabel = new JLabel(this.movie.getTitle());
        this.timeLabel = new JLabel(this.bookSeat.getTime());
        this.movieIcon = this.movie.getMovieLabel();
        this.bookedSeats = new JLabel(this.storeBookedSeats());
        this.setTotalPrice();
        
        this.setTitle("Sign In");
        this.setLayoutManager();
        this.setLocationAndSize();
        this.addComponentsToContainer();
        this.addActionListener();
        
        this.add(this.container);
        
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(370, 500);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setVisible(true);
    }
    
    /*
    This method retrieves the list of booked seats from the bookSeat object and returns them as a formatted string.
    */
    public String storeBookedSeats()
    {
        String seats = "";
        
        for (String s : this.bookSeat.getBookedSeats())
        {
            seats += s + " ";
        }
        
        return seats;
    }
    
    /*
    This method calculates the total price based on the membership type, number of people, movie type, and discount rate. It creates the appropriate 
    ticket object (e.g., NonMemberTicket, RewardsMemberTicket, or RewardsVIPTicket) and sets the totalPrice variable.
    */
    public void setTotalPrice()
    {
        int membershipType = this.fta.signInWindow.user.getMembershipType();
        int numberOfPeople = this.bookSeat.getSeatsToBook();
        double discountRate = this.dbManager.getDiscountRate(membershipType);
        this.totalPrice = 0.0;
        
        if (membershipType == 1)
        {
            NonMemberTicket nmt = new NonMemberTicket(numberOfPeople, this.movie.getMovieType(), discountRate);
            this.totalPrice = nmt.getTotalPrice();
        }
        if (membershipType == 2)
        {
            RewardsMemberTicket rmt = new RewardsMemberTicket(numberOfPeople, this.movie.getMovieType(), discountRate);
            this.totalPrice = rmt.getTotalPrice();
        }
        if (membershipType == 3)
        {
            RewardsVIPTicket rvipt = new RewardsVIPTicket(numberOfPeople, this.movie.getMovieType(), discountRate);
            this.totalPrice = rvipt.getTotalPrice();
        }
        
        this.totalPriceLabel = new JLabel("$" + totalPrice);
    }
    
    public void setLayoutManager()
    {
        this.container.setLayout(null);
    }
    
    public void setLocationAndSize()
    {
        this.movieIcon.setBounds(70, 30, 192, 154);
        this.titleLabel.setBounds(20, 200, 100, 30);
        this.timeLabel.setBounds(20, 250, 100, 30);
        this.bookedSeats.setBounds(20, 300, 200, 30);
        this.totalPriceLabel.setBounds(20, 350, 150, 30);
        this.finishButton.setBounds(120, 420, 100, 30);
    }
    
    public void addComponentsToContainer()
    {
        this.container.add(this.movieIcon);
        this.container.add(this.titleLabel);
        this.container.add(this.timeLabel);
        this.container.add(this.bookedSeats);
        this.container.add(this.totalPriceLabel);
        this.container.add(this.finishButton);
    }
    
    public void addActionListener()
    {
        this.finishButton.addActionListener(this);
    }

    /*
    This method handles the actionPerformed event. If the event source is the finish button, it calls the addTicketToDB method and exits the program.
    */
    @Override
    public void actionPerformed(ActionEvent e) 
    {
        if (e.getSource() == this.finishButton)
        {
            this.addTicketToDB();
            System.exit(0);
        }
    }
    
    /*
    This method retrieves the necessary information (username, user ID, movie title, movie ID, time, and total price) and constructs an SQL query 
    to insert the ticket data into the database using the dbManager object.
    */
    public void addTicketToDB()
    {
        String username = this.fta.signInWindow.user.getUsername();
        int userID = this.dbManager.getUserID(username);
        
        String movieTitle = this.bookSeat.tap.title;
        int movieID = this.dbManager.getMovieID(movieTitle);
        
        String query = "INSERT INTO Ticket VALUES (" + userID + ", " + movieID + ", '" + this.timeLabel.getText() + "', " + this.totalPrice + ")";
        
        this.dbManager.updateDB(query);
    }
}