package part2;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author jamie
 */
public class SignUpController implements ActionListener
{
    private User user;
    private SignUp signUp;
    
    public SignUpController(User user, SignUp signUp)
    {
        this.user = user;
        this.signUp = signUp;
        
        this.signUp.addActionListener(this);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) 
    {
        if (e.getSource() == this.signUp.getSignUpButton())
        {
            String userName = this.signUp.getUsernameInput().getText();
            String password = this.signUp.getPasswordInput().getText();
            this.user.checkCredential(userName, password);
            
            
        }
        
        if (e.getSource() == this.signUp.getMembershipComboBox())
        {
            String membership = (String) this.signUp.getMembershipComboBox().getSelectedItem();
            this.user.setMembership(membership);
        }
        
        if (e.getSource() == this.signUp.getHomeButton()) 
        {
            this.signUp.dispose();
            this.signUp.fta.setVisible(true);
        }
    }
    
}
