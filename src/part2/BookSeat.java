package part2;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/*
BookSeat represents a GUI window for booking seats in a film theater application. Here's a breakdown of the class and its components.
The class extends the JFrame class and implements the ActionListener interface, which allows it to handle user actions such as button clicks.
*/
public class BookSeat extends JFrame implements ActionListener
{
    private Container container = new Container();
    
    public FilmTheatreApp fta;
    public DBManager dbManager;
    public TimeAndPeople tap;
    
    private String time;
    private int seatsToBook;
    private int seatsBooked;
    private HashSet<String> bookedSeats = new HashSet();
    
    private JLabel message = new JLabel("Please select your seats.");
    private JButton[][] seatButtons = new JButton[10][10];
    private JButton confirmButton = new JButton("Confirm seats");
    private JButton signOut = new JButton("Sign out");
    private JButton previousButton = new JButton("Previous");
    
    /*
    The constructor initializes the instance variables and sets up the frame's layout and components. It also sets the size and visibility of the frame.
    */
    public BookSeat(FilmTheatreApp fta, DBManager dbManager, TimeAndPeople tap, String time, int numberOfPeople)
    {
        this.fta = fta;
        this.dbManager = dbManager;
        this.tap = tap;
        this.time = time;
        this.seatsToBook = numberOfPeople;
        this.seatsBooked = 0;
        
        this.setTitle("Film Theatre App");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        
        this.addSeatButtons();
        this.addActionEvent();
        
        this.setLocationAndSize();
        this.addComponents();
        
        
        this.add(this.container);
        
        this.setSize(710, 760);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void setLocationAndSize()
    {
        this.message.setBounds(270, 20, 200, 30);
        this.confirmButton.setBounds(260, 680, 150, 30);
        this.signOut.setBounds(590, 20, 100, 30);
        this.previousButton.setBounds(10, 20, 100, 30);
    }

    public void addComponents()
    {
        this.container.add(this.message);
        this.container.add(this.confirmButton);
        this.container.add(this.signOut);
        this.container.add(this.previousButton);
    }
    
    /*
    The addSeatButtons method creates a grid of seat buttons (JButtons) using nested loops. 
    Each button represents a seat in the theater. The buttons are added to a panel, which is then added to the container.
    */
    public void addSeatButtons()
    {
        JPanel seatsPanel = new JPanel(new GridLayout(10,10));
        
        Character alphabet = 'A';

        for (int i = 0; i < this.seatButtons.length; i++)
        {
            for (int j = 0; j < this.seatButtons.length; j++)
            {
                String seat = alphabet.toString() + (j + 1);
                this.seatButtons[i][j] = new JButton(seat);
                this.seatButtons[i][j].setSize(5, 5);
                this.seatButtons[i][j].addActionListener(this);
                seatsPanel.add(this.seatButtons[i][j]);
            }
            
            alphabet++;
        }
        
        seatsPanel.setBounds(70, 90, 560, 560);
        this.container.add(seatsPanel);
    }
    
    public void addActionEvent()
    {
        this.confirmButton.addActionListener(this);
        this.signOut.addActionListener(this);
        this.previousButton.addActionListener(this);
    }
    
    /*
    The actionPerformed method is invoked when a button is clicked. It handles the corresponding actions based on the button clicked. 
    If the previous button is clicked, it closes the current frame and shows the previous frame. If the sign out button is clicked, 
    it closes all related frames and shows the main application frame. If the confirm button is clicked, it hides the current frame and 
    creates an instance of the PrintTicket class. If any seat button is clicked, it checks if the seat is available, books it if available, 
    and provides appropriate messages.
    */
    @Override
    public void actionPerformed(ActionEvent e) 
    {
        if (e.getSource() == this.previousButton)
        {
            this.dispose();
            this.tap.setVisible(true);
        }
        
        if (e.getSource() == this.signOut)
        {
            this.dispose();
            this.tap.dispose();
            this.tap.movies.dispose();
            this.fta.setVisible(true);
        }
        
        if (e.getSource() == this.confirmButton) 
        {
            this.setVisible(false);
            
            PrintTicket pt = new PrintTicket(this.fta, this.dbManager, this);
        } 
        else 
        {
            JButton seatButton = (JButton) e.getSource();
            if (seatButton.isEnabled()) 
            {
                if (seatsBooked == seatsToBook) 
                {
                    JOptionPane.showMessageDialog(this, "You have already booked the maximum number of seats.", "Seat Booking", JOptionPane.INFORMATION_MESSAGE);
                } 
                else 
                {
                    seatButton.setEnabled(false);
                    bookedSeats.add(seatButton.getText());
                    seatsBooked++;
                    
                    if (seatsBooked == seatsToBook) 
                    {
                        JOptionPane.showMessageDialog(this, "You have successfully booked " + seatsBooked + " seat(s).", "Seat Booking", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            } 
            else 
            {
                JOptionPane.showMessageDialog(this, "Seat already booked.", "Seat Booking", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    public HashSet<String> getBookedSeats() 
    {
        return bookedSeats;
    }
    
    public int getSeatsToBook()
    {
        return this.seatsToBook;
    }
    
    public String getTime()
    {
        return this.time;
    }
}