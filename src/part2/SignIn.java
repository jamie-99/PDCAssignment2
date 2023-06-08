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

/**
 *
 * @author jamie
 */
public class SignIn extends JFrame implements Observer
{
    private Container container = new Container();
    private DBManager dbManager;
    
    public FilmTheatreApp fta;
    
    private JPanel signInPanel;
    private JLabel userName;
    private JLabel password;
    private JTextField userNameTextField;
    private JTextField passwordTextField;
    private JButton signInButton;
    private JButton resetButton;
    private JButton homeButton;
    private JLabel message;
    
    public SignIn(FilmTheatreApp fta, DBManager dbManager)
    {
        this.signInPanel = new JPanel();
        this.fta = fta;
        this.dbManager = dbManager;
        
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
    
    public static void main(String[] args) 
    {
        //SignIn signIn = new SignIn();
    }
}