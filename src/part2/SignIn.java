package part2;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 *
 * @author jamie
 */
public class SignIn extends JFrame implements ActionListener
{
    Container container;
    JLabel userName;
    JLabel password;
    JTextField userNameTextField;
    JTextField passwordTextField;
    JButton loginButton;
    JButton resetButton;
    JCheckBox showPassword;
    
    public SignIn()
    {
        
    }
    
    @Override
    public void actionPerformed(ActionEvent e) 
    {
        throw new UnsupportedOperationException("Not supported yet.");
    } 
}