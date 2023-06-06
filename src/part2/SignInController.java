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
        if (e.getSource() == this.signIn.getSignInButton())
        {
            String inputUsername = this.signIn.getUserNameTextField().getText();
            String inputPassword = this.signIn.getPasswordTextField().getText();
            
            this.user.checkCredential(inputUsername, inputPassword);
        } 
        
        if (e.getSource() == this.signIn.getResetButton())
        {
            this.signIn.getUserNameTextField().setText("");
            this.signIn.getPasswordTextField().setText("");
        }
        
        if(e.getSource() == this.signIn.getHomeButton())
        {
            
        }
    } 
}