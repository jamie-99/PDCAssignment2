package part2;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

/**
 *
 * @author jamie
 */
public class SignInController implements ActionListener
{
    DBManager dbManager;
    SignIn signIn;
    
    public SignInController(DBManager dbManager, SignIn signIn)
    {
        this.dbManager = dbManager;
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
            
            Data data = this.dbManager.checkCredential(inputUsername, inputPassword);
            
            if (data.getSignInFlag())
            {
                this.signIn.dispose();
                
                Movies movie = new Movies(this.signIn.fta, this.dbManager);
            }
            else if (!data.getSignInFlag())
            {
                JOptionPane.showMessageDialog(this.signIn, "Invalid Username or Password");
            }
        } 
        
        if (e.getSource() == this.signIn.getResetButton())
        {
            this.signIn.getUserNameTextField().setText("");
            this.signIn.getPasswordTextField().setText("");
        }
        
        if(e.getSource() == this.signIn.getHomeButton())
        {
            this.signIn.dispose();
            this.signIn.fta.setVisible(true);
        }
    } 
}