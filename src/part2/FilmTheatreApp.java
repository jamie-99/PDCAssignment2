package part2;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 *
 * @author jamie
 */
public class FilmTheatreApp extends JFrame implements ActionListener
{
    private Container container;
    private JLabel message;
    private JLabel welcomeMessage;
    private JButton signIn;
    private JButton signUp;
    
    private DBManager dbManager;
    
    public FilmTheatreApp() 
    {
        this.dbManager = new DBManager();
        
        this.container = getContentPane();
        this.welcomeMessage = new JLabel("Welcome to FilmTheatre App!");
        this.signIn = new JButton("Sign in");
        this.signUp = new JButton("Sign up");
        
        this.setLayoutManager();
        this.setLocationAndSize();
        this.addCompomentsToContainer();
        this.addActionEvent();
        
        this.frame();
    }
    
    public void setLayoutManager()
    {
        this.container.setLayout(null);
    }
    
    public void setLocationAndSize()
    {
        this.welcomeMessage.setBounds(90, 150, 200, 30);
        this.signIn.setBounds(50, 300, 100, 30);
        this.signUp.setBounds(200, 300, 100, 30);
    }
    
    public void addCompomentsToContainer()
    {
        this.container.add(this.welcomeMessage);
        this.container.add(this.signIn);
        this.container.add(this.signUp);
    }
    
    public void addActionEvent()
    {
        this.signIn.addActionListener(this);
        this.signUp.addActionListener(this);
    }
    
    public void frame()
    {
        this.setTitle("Fiml Theatre App");
        this.setVisible(true);
        this.setSize(370, 600);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
    }  

    @Override
    public void actionPerformed(ActionEvent e) 
    {
        if (e.getSource() == signIn)
        {
            this.setVisible(false);
            
            SignIn signInWindow = new SignIn(this, this.dbManager);
            SignInController sic = new SignInController(this.dbManager, signInWindow);
            this.dbManager.addObserver(signInWindow);
        }
        
        if (e.getSource() == signUp)
        {
            this.setVisible(false);
            
            SignUp signUpWindow = new SignUp(this, this.dbManager);
            SignUpController suc = new SignUpController(dbManager, signUpWindow);
            this.dbManager.addObserver(signUpWindow);
        }
    }
    
    public static void main(String[] args) 
    {
        FilmTheatreApp fta = new FilmTheatreApp();
    }
}