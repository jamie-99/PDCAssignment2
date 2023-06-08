package part2;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 *
 * @author jamie
 */
public class TimeAndPeople extends JFrame implements ActionListener
{
    public FilmTheatreApp fta;
    public DBManager dbManager;

    private Movies movies;
    private String title;
    private String[] timeSlot;
    
    private Container container = new Container();
    public JLabel timeLabel = new JLabel("Time");
    public JLabel numberOfPeopleLabel = new JLabel("Number of people");
    public JComboBox timeComboBox;
    public JComboBox numberOfPeople;
    public JButton signOutButton = new JButton("Sign out");
    public JButton previousButton = new JButton("Previous");
    public JButton confirmButton = new JButton("Confirm");

    public TimeAndPeople(FilmTheatreApp fta, DBManager dbManager, Movies movies, String title)
    {
        this.fta = fta;
        this.dbManager = dbManager;
        this.movies = movies;
        this.title = title;
        
        String[] temp = this.dbManager.getMovieSchedule(this.title);
        this.timeSlot = this.moveArray(temp);
        this.timeComboBox = new JComboBox(this.timeSlot);
        
        String[] numberOfPeopleOptions = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
        this.numberOfPeople = new JComboBox(numberOfPeopleOptions);
        
        this.setLayoutManager();
        this.setLocationAndSize();
        this.addComponents();
        this.addActionListener();
        this.add(this.container);
        
        this.setTitle("Booking");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(370, 400);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
    
    public String[] moveArray(String[] timeSlot)
    {
        ArrayList tempList = new ArrayList();
        
        for (int i = 0; i < timeSlot.length; i++)
        {
            if (timeSlot[i] != null)
            {
                tempList.add(timeSlot[i]);
            }
        }
        
        String[] time = new String[tempList.size()];
        for (int i = 0; i < time.length; i++)
        {
            time[i] = (String) tempList.get(i);
        }
        
        return time;
    }
    
    public void setLayoutManager()
    {
        this.container.setLayout(null);
    }
    
    public void setLocationAndSize()
    {
        this.timeLabel.setBounds(50, 100, 100, 30);
        this.timeComboBox.setBounds(170, 100, 100, 30);
        this.numberOfPeopleLabel.setBounds(50, 150, 100, 30);
        this.numberOfPeople.setBounds(170, 150, 100, 30);
        
        this.signOutButton.setBounds(210, 10, 130, 30);
        this.previousButton.setBounds(10, 10, 100, 30);
        this.confirmButton.setBounds(130, 220, 100, 30);
    }
    
    public void addComponents()
    {
        this.container.add(this.timeLabel);
        this.container.add(this.timeComboBox);
        this.container.add(this.numberOfPeopleLabel);
        this.container.add(this.numberOfPeople);
        this.container.add(this.signOutButton);
        this.container.add(this.previousButton);
        this.container.add(this.confirmButton);
    }
    
    public void addActionListener()
    {
        this.timeComboBox.addActionListener(this);
        this.numberOfPeople.addActionListener(this);
        this.signOutButton.addActionListener(this);
        this.previousButton.addActionListener(this);
        this.confirmButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) 
    {
        if (e.getSource() == this.signOutButton)
        {
            this.dispose();
            this.movies.dispose();
            this.fta.setVisible(true);
        }
        
        if (e.getSource() == this.previousButton)
        {
            this.dispose();
            this.movies.setVisible(true);
        }
        
        if (e.getSource() == this.confirmButton)
        {
            String time = (String) this.timeComboBox.getSelectedItem();
            int people = Integer.parseInt(this.numberOfPeople.getSelectedItem().toString());
            
            this.setVisible(false);
            BookSeat bookSeat = new BookSeat(this.fta, this.dbManager, this, time, people);
        }
    }
}
