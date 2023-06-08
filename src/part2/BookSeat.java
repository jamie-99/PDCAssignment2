package part2;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author jamie
 */
public class BookSeat extends JFrame implements ActionListener
{
    private Container container = new Container();
    
    private JLabel message = new JLabel("Please select your seats.");
    private JButton[][] seatButtons = new JButton[10][10];
    private JButton confirmButton = new JButton("Confirm seats");
    private JButton homeButton = new JButton("Home");
    private JButton previousButton = new JButton("Previous");
    
    public BookSeat()
    {
        this.setTitle("Film Theatre App");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        
        this.addSeatButtons();
        this.addActionEvent();
        
        this.setLocationAndSize();
        this.addComponents();
        
        
        this.add(this.container);
        
        this.setSize(710, 900);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    public void setLocationAndSize()
    {
        this.message.setBounds(270, 20, 200, 30);
        this.confirmButton.setBounds(260, 770, 150, 50);
        this.homeButton.setBounds(590, 20, 100, 30);
        this.previousButton.setBounds(10, 20, 100, 30);
    }
    
    public void addComponents()
    {
        this.container.add(this.message);
        this.container.add(this.confirmButton);
        this.container.add(this.homeButton);
        this.container.add(this.previousButton);
    }
    
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
                this.seatButtons[i][j].setSize(50, 50);
                this.seatButtons[i][j].addActionListener(this);
                seatsPanel.add(this.seatButtons[i][j]);
            }
            
            alphabet++;
        }
        
        seatsPanel.setBounds(45, 100, 600, 600);
        this.container.add(seatsPanel);
    }
    
    public void addActionEvent()
    {
        this.confirmButton.addActionListener(this);
        this.homeButton.addActionListener(this);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) 
    {
        if (e.getSource() == this.confirmButton) 
        {
            for (int i = 0; i < this.seatButtons.length; i++) 
            {
                for (int j = 0; j < this.seatButtons.length; i++) 
                {
                    if (seatButtons[i][j].isEnabled()) 
                    {
                        seatButtons[i][j].setEnabled(false);
                        break;
                    }
                }
            }
        } 
        else 
        {
            JButton seatButton = (JButton) e.getSource();
            if (seatButton.isEnabled()) 
            {
                seatButton.setEnabled(false);
            }
        }
    }
    
    public static void main(String[] args) 
    {
        BookSeat bs = new BookSeat();
    }
}
