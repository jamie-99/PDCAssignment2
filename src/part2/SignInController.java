package part2;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author jamie
 */
public class SignInController implements ActionListener
{
    User user;
    SignIn signIn;
    
    public SignInController(User user, SignIn signIn)
    {
        this.user = user;
        this.signIn = signIn;
        
        this.signIn.addActionListenr(this);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) 
    {
        if (e.getSource() == this.signIn.signInButton)
        {
            String inputUsername = this.signIn.userNameTextField.getText();
            String inputPassword = this.signIn.passwordTextField.getText();
            
            this.user.checkName(inputUsername, inputPassword);
        } 
        
        if (e.getSource() == this.signIn.resetButton)
        {
            this.signIn.userNameTextField.setText("");
            this.signIn.passwordTextField.setText("");
        }
    } 
}