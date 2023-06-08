package part2;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 *
 * @author jamie
 */
public class PrintTicket extends JFrame implements ActionListener
{
    public FilmTheatreApp fta;
    public DBManager dbManager;
    public BookSeat bookSeat;
    
    private JLabel titleLabel;
    private JLabel bookedSeats;
    private JButton finishButton = new JButton("Finish");
    
    public PrintTicket(FilmTheatreApp fta, DBManager dbManager, BookSeat bookSeat)
    {
        this.fta = fta;
        this.dbManager = dbManager;
        this.bookSeat = bookSeat;
        
        this.titleLabel = new JLabel(this.bookSeat.tap.title);
        this.bookedSeats = new JLabel(this.storeBookedSeats());
    }
    
    public String storeBookedSeats()
    {
        String seats = "";
        
        for (String s : this.bookSeat.getBookedSeats())
        {
            seats += s + " ";
        }
        
        return seats;
    }

    @Override
    public void actionPerformed(ActionEvent e) 
    {
        
    }
}
