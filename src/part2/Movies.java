package part2;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 *
 * @author jamie
 */
public class Movies extends JFrame implements ActionListener
{
    private ArrayList<Movie> movies;
    private int numberOfMovies;
    
    DBManager dbManager;
    FilmTheatreApp fta;
    
    private Container container = new Container();
    private JLabel message= new JLabel("Please select a movie from the list.");
    
    private JButton starWarsButton;
    private JButton minionsButton;
    private JButton avengersButton;
    private JButton superMarioButton;
    private JButton johnWickButton;
    
    private JLabel starWarsLabel;
    private JLabel minionsLabel;
    private JLabel avengersLabel;
    private JLabel superMarioLabel;
    private JLabel johnWickLabel;
    
    private JLabel starWarsIcon;
    private JLabel minionsIcon;
    private JLabel avengersIcon;
    private JLabel superMarioIcon;
    private JLabel johnWickIcon;
    
    private JButton signOut = new JButton("Sign out");
    
    public Movies(FilmTheatreApp fta, DBManager dbManager) 
    {
        this.dbManager = dbManager;
        this.fta = fta;
        
        this.movies = new ArrayList();
        this.movies = dbManager.retrieveMoviesFromDB();
        
        this.starWarsButton = new JButton(this.movies.get(0).getTitle());
        this.minionsButton = new JButton(this.movies.get(1).getTitle());
        this.avengersButton = new JButton(this.movies.get(2).getTitle());
        this.superMarioButton = new JButton(this.movies.get(3).getTitle());
        this.johnWickButton = new JButton(this.movies.get(4).getTitle());

        this.starWarsLabel = new JLabel(this.movies.get(0).movieDetail());
        this.minionsLabel = new JLabel(this.movies.get(1).movieDetail());
        this.avengersLabel = new JLabel(this.movies.get(2).movieDetail());
        this.superMarioLabel = new JLabel(this.movies.get(3).movieDetail());
        this.johnWickLabel = new JLabel(this.movies.get(4).movieDetail());
        
        this.addImageIcon();

        this.setLayoutManager();
        this.setLocationAndSizes();
        this.addCompomentsToContainer();
        this.addActionEvent();

        this.frame();
        
        this.add(this.container);
    }
    
    public void addImageIcon()
    {
        ImageIcon starWars = new ImageIcon("./resources/Star Wars.png");
        ImageIcon minions = new ImageIcon("./resources/Minions.png");
        ImageIcon avengers = new ImageIcon("./resources/Avengers.png");
        ImageIcon superMario = new ImageIcon("./resources/Super Mario.png");
        ImageIcon johnWick = new ImageIcon("./resources/John Wick.png");
        
        this.starWarsIcon = new JLabel(starWars);
        this.movies.get(0).setMovieLabel(this.starWarsIcon);
        this.minionsIcon = new JLabel(minions);
        this.movies.get(1).setMovieLabel(this.minionsIcon);
        this.avengersIcon = new JLabel(avengers);
        this.movies.get(2).setMovieLabel(this.avengersIcon);
        this.superMarioIcon = new JLabel(superMario);
        this.movies.get(3).setMovieLabel(this.superMarioIcon);
        this.johnWickIcon = new JLabel(johnWick);
        this.movies.get(4).setMovieLabel(this.johnWickIcon);
    }
    
    public void setLayoutManager()
    {
        this.container.setLayout(null);
    }
    
    public void setLocationAndSizes()
    {
        this.message.setBounds(180, 10, 300, 30);
        
        this.starWarsIcon.setBounds(20, 70, 180, 109);
        this.starWarsButton.setBounds(230, 70, 150, 30);
        this.starWarsLabel.setBounds(230, 100, 250, 30);
        
        this.minionsIcon.setBounds(20, 190, 181, 128);
        this.minionsButton.setBounds(230, 190, 150, 30);
        this.minionsLabel.setBounds(230, 220, 250, 30);
        
        this.avengersIcon.setBounds(20, 310, 192, 108);
        this.avengersButton.setBounds(230, 310, 150, 30);
        this.avengersLabel.setBounds(230, 340, 250, 30);
        
        this.superMarioIcon.setBounds(20, 440, 182, 154);
        this.superMarioButton.setBounds(230, 440, 150, 30);
        this.superMarioLabel.setBounds(230, 470, 250, 30);
        
        this.johnWickIcon.setBounds(20, 620, 181, 52);
        this.johnWickButton.setBounds(230, 620, 150, 30);
        this.johnWickLabel.setBounds(230, 650, 250, 30);
        
        this.signOut.setBounds(450, 20, 100, 30);
    }
    
    public void addCompomentsToContainer()
    {
        this.container.add(this.message);
        
        this.container.add(this.starWarsIcon);
        this.container.add(this.starWarsButton);
        this.container.add(this.starWarsLabel);
        
        this.container.add(this.minionsIcon);
        this.container.add(this.minionsButton);
        this.container.add(this.minionsLabel);
        
        this.container.add(this.avengersIcon);
        this.container.add(this.avengersButton);
        this.container.add(this.avengersLabel);
        
        this.container.add(this.superMarioIcon);
        this.container.add(this.superMarioButton);
        this.container.add(this.superMarioLabel);
        
        this.container.add(this.johnWickIcon);
        this.container.add(this.johnWickButton);
        this.container.add(this.johnWickLabel);
        
        this.container.add(this.signOut);
    }
    
    public void frame()
    {
        this.setTitle("Fiml Theatre App");
        this.setVisible(true);
        this.setSize(600, 750);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
    }
    
    public void addActionEvent()
    {
        this.starWarsButton.addActionListener(this);
        this.minionsButton.addActionListener(this);
        this.avengersButton.addActionListener(this);
        this.superMarioButton.addActionListener(this);
        this.johnWickButton.addActionListener(this);
        this.signOut.addActionListener(this);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) 
    {
        if (e.getSource() == this.starWarsButton)
        {
            this.setVisible(false);
            
            TimeAndPeople tap = new TimeAndPeople(this.fta, this.dbManager, this, this.starWarsButton.getText());
        }
        
        if (e.getSource() == this.minionsButton)
        {
            this.setVisible(false);
            
            TimeAndPeople tap = new TimeAndPeople(this.fta, this.dbManager, this, this.minionsButton.getText());
        }
        
        if (e.getSource() == this.avengersButton)
        {
            this.setVisible(false);
            
            TimeAndPeople tap = new TimeAndPeople(this.fta, this.dbManager, this, this.avengersButton.getText());
        }
        
        if (e.getSource() == this.superMarioButton)
        {
            this.setVisible(false);
            
            TimeAndPeople tap = new TimeAndPeople(this.fta, this.dbManager, this, this.superMarioButton.getText());
        }
        
        if (e.getSource() == this.johnWickButton)
        {
            this.setVisible(false);
            
            TimeAndPeople tap = new TimeAndPeople(this.fta, this.dbManager, this, this.johnWickButton.getText());
        }
        
        if (e.getSource() == this.signOut)
        {
            this.dispose();
            
            this.fta.setVisible(true);
        }
    }

    public ArrayList<Movie> getMovies() 
    {
        return this.movies;
    }
    
    public Movie findMovie(String title)
    {
        Movie movie = null;
        
        for (Movie m : this.movies)
        {
            if (m.getTitle().equalsIgnoreCase(title))
            {
                movie = m;
            }
        }
        
        return movie;
    }

    public Movie getMovie(int index) 
    {
        return this.movies.get(index - 1);
    }

    public int getArraySize() 
    {
        return this.movies.size();
    }
}
