package part2;

import java.awt.Container;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 *
 * @author jamie
 */
public class SignIn extends JFrame implements Observer
{
    Container container;
    JLabel userName;
    JLabel password;
    JTextField userNameTextField;
    JTextField passwordTextField;
    JButton signInButton;
    JButton resetButton;
    JButton homeButton;
    JLabel message;
    
    public SignIn()
    {
        this.userName = new JLabel("Username");
        this.password = new JLabel("Password");
        this.userNameTextField = new JTextField();
        this.passwordTextField = new JTextField();
        this.signInButton = new JButton("Sign in");
        this.resetButton = new JButton("Reset");
        this.homeButton = new JButton("Home");
        this.message = new JLabel();
        
        this.setLayoutManager();
        this.setLocationAndSize();
        this.addCompomentsToContainer();
        this.frame();
    }
    
    public void setLayoutManager()
    {
        this.container.setLayout(null);
    }
    
    public void setLocationAndSize()
    {
        this.userName.setBounds(50, 150, 100, 30);
        this.password.setBounds(50, 220, 100, 30);
        this.userNameTextField.setBounds(150, 150, 150, 30);
        this.passwordTextField.setBounds(150, 220, 150, 30);
        this.signInButton.setBounds(50, 300, 100, 30);
        this.resetButton.setBounds(200, 300, 100, 30);
        this.homeButton.setBounds(30, 30, 100, 30);
    }
    
    public void addCompomentsToContainer()
    {
        this.container.add(this.userName);
        this.container.add(this.password);
        this.container.add(this.userNameTextField);
        this.container.add(this.passwordTextField);
        this.container.add(this.signInButton);
        this.container.add(this.resetButton);
        this.container.add(this.homeButton);
    }
    
    public void addActionListenr(ActionListener listener)
    {
        this.signInButton.addActionListener(listener);
        this.resetButton.addActionListener(listener);
        this.homeButton.addActionListener(listener);
    } 
    
    public void frame()
    {
        this.setTitle("Sign in");
        this.setVisible(true);
        this.setSize(370, 600);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
    }

    @Override
    public void update(Observable o, Object arg) 
    {
        Data data = (Data) arg;
        
        if(!data.getSignInFlag())
        {
            this.userNameTextField.setText("");
            this.passwordTextField.setText("");
            this.message.setText("Wrong username or password");
        }
        else if (data.getHomeFlag())
        {
            
        }
    }
    
    public static void main(String[] args) 
    {
        SignIn signIn = new SignIn();
    }
}