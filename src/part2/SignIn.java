package part2;

import java.awt.Container;
import java.awt.event.ActionEvent;
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
    
    public SignIn()
    {
        this.userName = new JLabel("Username");
        this.password = new JLabel("Password");
        this.userNameTextField = new JTextField();
        this.passwordTextField = new JTextField();
        this.signInButton = new JButton("Sign in");
        this.resetButton = new JButton("Reset");
        this.homeButton = new JButton("Home");
        
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
    }
    
    public void addCompomentsToContainer()
    {
        this.container.add(this.userName);
        this.container.add(this.password);
        this.container.add(this.userNameTextField);
        this.container.add(this.passwordTextField);
        this.container.add(this.signInButton);
        this.container.add(this.resetButton);
    }
    
    public void addActionListenr(ActionListener listener)
    {
        this.signInButton.addActionListener(listener);
        this.resetButton.addActionListener(listener);
    }
    
    public void actionPerformed(ActionEvent e) 
    {
        if (e.getSource() == this.signInButton)
        {
            String inputUsername = this.userNameTextField.getText();
            String inputPassword = this.passwordTextField.getText();
        }
        
        if (e.getSource() == this.resetButton)
        {
            this.userNameTextField.setText("");
            this.passwordTextField.setText("");
        }
    } 
    
    public void frame()
    {
        this.setTitle("Sign in");
        this.setVisible(true);
        this.setSize(370, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
    }

    @Override
    public void update(Observable o, Object arg) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}