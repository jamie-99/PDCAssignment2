package part2;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

/*
FilmTheatreApp is a graphical user interface (GUI) application that represents the main window of a film theatre application.
The class extends the JFrame class, which represents the main window of a GUI application.
The class also implements the ActionListener interface, allowing it to handle action events.
*/
public class FilmTheatreApp extends JFrame implements ActionListener
{
    public SignIn signInWindow;
    
    private Container container;
    private JLabel message;
    private JLabel welcomeMessage;
    private JButton signIn;
    
    private DBManager dbManager;
    
    /*
    The constructor initializes the DBManager object and sets up the user interface components of the window. 
    It creates a container, labels, and a button, and configures their layout, size, and position. 
    It also sets up the frame properties such as title, visibility, size, position, close operation, and resizability.
    */
    public FilmTheatreApp() 
    {
        this.dbManager = new DBManager();
        
        this.container = getContentPane();
        this.welcomeMessage = new JLabel("Welcome to FilmTheatre App!");
        this.signIn = new JButton("Sign in");
        
        this.setLayoutManager();
        this.setLocationAndSize();
        this.addCompomentsToContainer();
        this.addActionEvent();
        
        this.frame();
    }
    
    /*
    This method sets the layout manager for the container to null. 
    This means that the components added to the container will have custom positioning using absolute coordinates.
    */
    public void setLayoutManager()
    {
        this.container.setLayout(null);
    }
    
    /*
    This method sets the location and size of the user interface components. 
    It defines the bounding rectangles for the welcomeMessage label and the signIn button using the setBounds method.
    */
    public void setLocationAndSize()
    {
        this.welcomeMessage.setBounds(90, 150, 200, 30);
        this.signIn.setBounds(120, 300, 100, 30);
    }
    
    /*
    This method adds the user interface components (welcomeMessage label and signIn button) to the container using the add method.
    */
    public void addCompomentsToContainer()
    {
        this.container.add(this.welcomeMessage);
        this.container.add(this.signIn);
    }
    
    /*
    This method adds an action event listener to the signIn button using the addActionListener method. 
    The class itself implements the ActionListener interface, so it handles the action event in the actionPerformed method.
    */
    public void addActionEvent()
    {
        this.signIn.addActionListener(this);
    }
    
    /*
    This method configures the properties of the frame. It sets the title of the frame, makes it visible, sets its size, centers it on the screen, 
    sets the default close operation to exit the application when the frame is closed, and disables resizing.
    */
    public void frame()
    {
        this.setTitle("Fiml Theatre App");
        this.setVisible(true);
        this.setSize(370, 600);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
    }  

    /*
    This method is overridden from the ActionListener interface. It handles action events triggered by user interaction with components. 
    In this case, it checks if the event source is the signIn button. If so, it hides the current window, creates a SignIn window, 
    initializes a SignInController, and adds the SignIn window as an observer to the DBManager.
    */
    @Override
    public void actionPerformed(ActionEvent e) 
    {
        if (e.getSource() == signIn)
        {
            this.setVisible(false);
            
            signInWindow = new SignIn(this, this.dbManager);
            SignInController sic = new SignInController(this.dbManager, signInWindow);
            this.dbManager.addObserver(signInWindow);
        }
    }
    
    public static void main(String[] args) 
    {
        FilmTheatreApp fta = new FilmTheatreApp();
    }
}