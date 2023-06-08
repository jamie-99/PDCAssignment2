package part2;

import java.awt.Container;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/*
This class acts as View in MVC.
*/
public class SignIn extends JFrame implements Observer
{
    private Container container = new Container();
    private DBManager dbManager;
    
    public FilmTheatreApp fta;
    public User user;
    
    private JPanel signInPanel;
    private JLabel userName;
    private JLabel password;
    private JTextField userNameTextField;
    private JTextField passwordTextField;
    private JButton signInButton;
    private JButton resetButton;
    private JButton homeButton;
    private JLabel message;
    
    /*
    This is the constructor of the class. It initializes a SignIn object with the provided fta and dbManager. 
    It sets up the GUI components, including labels, text fields, buttons, and their positions.
    */
    public SignIn(FilmTheatreApp fta, DBManager dbManager)
    {
        this.signInPanel = new JPanel();
        this.fta = fta;
        this.dbManager = dbManager;
        this.user = new User();
        
        this.userName = new JLabel("Username");
        this.password = new JLabel("Password");
        this.userNameTextField = new JTextField();
        this.passwordTextField = new JTextField();
        this.signInButton = new JButton("Sign in");
        this.resetButton = new JButton("Reset");
        this.homeButton = new JButton("Home");
        this.message = new JLabel();
        
        this.setTitle("Sign In");
        this.setLocationAndSize();
        this.addCompomentsToContainer();
        this.add(this.container);
        
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(370, 600);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setVisible(true);
    }
    
    public void setLocationAndSize()
    {
        this.userName.setBounds(50, 150, 100, 30);
        this.password.setBounds(50, 220, 100, 30);
        this.getUserNameTextField().setBounds(150, 150, 150, 30);
        this.getPasswordTextField().setBounds(150, 220, 150, 30);
        this.getSignInButton().setBounds(50, 300, 100, 30);
        this.getResetButton().setBounds(200, 300, 100, 30);
        this.getHomeButton().setBounds(20, 20, 80, 25);
    }
    
    public void addCompomentsToContainer()
    {
        this.container.add(this.userName);
        this.container.add(this.password);
        this.container.add(this.getUserNameTextField());
        this.container.add(this.getPasswordTextField());
        this.container.add(this.getSignInButton());
        this.container.add(this.getResetButton());
        this.container.add(this.getHomeButton());
    }
    
    public void addActionListenr(ActionListener listener)
    {
        this.getSignInButton().addActionListener(listener);
        this.getResetButton().addActionListener(listener);
        this.getHomeButton().addActionListener(listener);
    } 

    /*
    This method is called when the observed object notifies its observers. 
    It updates the SignIn object based on the received arg object. If the sign-in flag is false, it clears the username and 
    password text fields and displays a message indicating incorrect credentials. If the home flag is true, it disposes of the 
    sign-in window and makes the fta (FilmTheatreApp) window visible.
    */
    @Override
    public void update(Observable o, Object arg) 
    {
        Data data = (Data) arg;
        
        if(!data.getSignInFlag())
        {
            this.getUserNameTextField().setText("");
            this.getPasswordTextField().setText("");
            this.message.setText("Wrong username or password");
        }
        else if (data.getHomeFlag())
        {
            this.dispose();
            
            this.fta.setVisible(true);
        }
    }
    
    public JButton getSignInButton() 
    {
        return signInButton;
    }

    public JButton getResetButton() 
    {
        return resetButton;
    }

    public JButton getHomeButton() 
    {
        return homeButton;
    }  
    
    public JTextField getUserNameTextField() 
    {
        return userNameTextField;
    }

    public JTextField getPasswordTextField() 
    {
        return passwordTextField;
    }
}