package part2;

import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author jamie
 */
public class SignUp extends JFrame implements Observer
{
    private JPanel signUpPanel;
    
    private JLabel firstName;
    private JLabel lastName;
    private JLabel username;
    private JLabel password;
    private JLabel membership;
    
    private JTextField firstNameInput;
    private JTextField lastNameInput;
    private JTextField usernameInput;
    private JTextField passwordInput;
    private JComboBox membershipComboBox;
    
    private JButton signUpButton;
    private JButton homeButton;
    
    private JLabel message;
    
    public SignUp()
    {
        this.signUpPanel = new JPanel();
        
        this.firstName = new JLabel("First name");
        this.lastName = new JLabel("Last name");
        this.username = new JLabel("Username");
        this.password = new JLabel("Password");
        this.membership = new JLabel("Membership");
        
        this.firstNameInput = new JTextField();
        this.lastNameInput = new JTextField();
        this.usernameInput = new JTextField();
        this.passwordInput = new JTextField();
        
        String[] membershipType = {"Non-member", "Rewards member", "Rewards VIP"};
        this.membershipComboBox = new JComboBox(membershipType);
        
        this.signUpButton = new JButton("Sign Up");
        this.homeButton = new JButton("Home");
        
        this.message = new JLabel();
        
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(370, 600);
        this.setLocationRelativeTo(null);
        
        this.setLocationAndSize();
        this.addComponents();
        
        this.add(this.signUpPanel);
        this.setVisible(true);
    }
    
    public void setLocationAndSize()
    {
        this.firstName.setBounds(50, 150, 100, 30);
        this.lastName.setBounds(50, 200, 100, 30);
        this.username.setBounds(50, 250, 100, 30);
        this.password.setBounds(50, 300, 100, 30);
        this.membership.setBounds(50, 350, 100, 30);
        
        this.firstNameInput.setBounds(150, 150, 150, 30);
        this.lastNameInput.setBounds(150, 200, 150, 30);
        this.usernameInput.setBounds(150, 250, 150, 30);
        this.passwordInput.setBounds(150, 300, 150, 30);
        this.membershipComboBox.setBounds(150, 350, 150, 30);
        
        this.signUpButton.setBounds(250, 420, 100, 30);
        this.homeButton.setBounds(30, 30, 100, 30);
    }
    
    public void addComponents()
    {
        this.signUpPanel.add(this.firstName);
        this.signUpPanel.add(this.lastName);
        this.signUpPanel.add(this.username);
        this.signUpPanel.add(this.password);
        this.signUpPanel.add(this.membership);
        
        this.signUpPanel.add(this.firstNameInput);
        this.signUpPanel.add(this.lastNameInput);
        this.signUpPanel.add(this.usernameInput);
        this.signUpPanel.add(this.passwordInput);
        this.signUpPanel.add(this.membershipComboBox);
        
        this.signUpPanel.add(this.signUpButton);
        this.signUpPanel.add(this.homeButton);
    }
    
    public void addActionListener(ActionListener listener)
    {
        this.membershipComboBox.addActionListener(listener);
        this.signUpButton.addActionListener(listener);
        this.homeButton.addActionListener(listener);
    }

    @Override
    public void update(Observable o, Object arg) 
    {
        Data data = (Data) arg;
        
        if (!data.getSignUpFlag())
        {
            this.usernameInput.setText("");
            this.passwordInput.setText("");
            
            this.message.setText("The username already exists.");
        }
        else if (data.getHomeFlag())
        {
            
        }
    }
}