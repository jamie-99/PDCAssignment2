package part2;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

/*
The SignInController class acts as the controller for the Sign In functionality in the Film Theatre App.
It handles user actions and interacts with the DBManager and SignIn classes.
*/
public class SignInController implements ActionListener
{
    DBManager dbManager;
    SignIn signIn;
        
    /*
    Constructs a SignInController object with the specified DBManager and SignIn instances.
    It registers itself as an ActionListener to the SignIn window components.
    */
    public SignInController(DBManager dbManager, SignIn signIn)
    {
        this.dbManager = dbManager;
        this.signIn = signIn;
        
        //Register itself as an ActionListener to the SignIn window components
        this.signIn.addActionListenr(this);
    }
    
    /*
    Handles the actionPerformed event triggered by user actions on the Sign In window components.
    It performs the appropriate actions based on the event source.
    */
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
                //Valid credentials, sign in successful. Set user information in the SignIn's User object.
                this.signIn.user.setUsername(inputUsername);
                this.signIn.user.setPassword(inputPassword);
                this.signIn.user.setMembershipType(this.dbManager.getMembershipID(inputUsername));
                
                //Close the Sign In window
                this.signIn.dispose();
                
                //Open the Movies window
                Movies movie = new Movies(this.signIn.fta, this.dbManager);
            }
            else if (!data.getSignInFlag())
            {
                //Invalid credentials, display error message
                JOptionPane.showMessageDialog(this.signIn, "Invalid Username or Password");
            }
        } 
        
        if (e.getSource() == this.signIn.getResetButton())
        {
            //User clicked the Reset button, clear input fields
            this.signIn.getUserNameTextField().setText("");
            this.signIn.getPasswordTextField().setText("");
        }
        
        if(e.getSource() == this.signIn.getHomeButton())
        {
            //User clicked the Home button, return to the home screen
            this.signIn.dispose();
            this.signIn.fta.setVisible(true);
        }
    } 
}