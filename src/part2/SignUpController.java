package part2;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author jamie
 */
public class SignUpController implements ActionListener
{
    private DBManager dbManager;
    private SignUp signUp;
    
    private int userID = 2;
    
    public SignUpController(DBManager dbManager, SignUp signUp)
    {
        this.dbManager = dbManager;
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
            String firstName = this.signUp.getFirstNameInput().getText();
            String lastName = this.signUp.getLastNameInput().getText();
            
            String membership = (String) this.signUp.getMembershipComboBox().getSelectedItem();
            
            Data data = this.dbManager.checkUsernameExists(userName);
            
            if (!data.getSignUpFlag())
            {
                String query = "INSERT INTO Users VALUES (" + this.userID + ", " + userName + ", " + password + ", " 
                        + firstName + ", " + lastName + ")";
                
                this.dbManager.updateDB(query);
                
                this.userID++;
                
                this.signUp.dispose();
                
                Movies movie = new Movies(this.signUp.fta, this.signUp.dbManager);
            }
        }
        
        if (e.getSource() == this.signUp.getMembershipComboBox())
        {
            String membership = (String) this.signUp.getMembershipComboBox().getSelectedItem();
        }
        
        if (e.getSource() == this.signUp.getHomeButton()) 
        {
            this.signUp.dispose();
            this.signUp.fta.setVisible(true);
        }
    }
    
}
