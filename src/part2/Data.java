package part2;

/**
 *
 * @author jamie
 */
public class Data 
{
    private boolean signInFlag;
    private boolean signUpFlag;
    private boolean homeFlag;
    
    public Data()
    {
        this.signInFlag = false;
        this.signUpFlag = true;
        this.homeFlag = false;
    }

    public boolean getSignInFlag() 
    {
        return signInFlag;
    }

    public void setSignInFlag(boolean signInFlag) 
    {
        this.signInFlag = signInFlag;
    }

    public boolean getSignUpFlag() 
    {
        return signUpFlag;
    }

    public void setSignUpFlag(boolean signUpFlag) 
    {
        this.signUpFlag = signUpFlag;
    }

    public boolean getHomeFlag() 
    {
        return homeFlag;
    }

    public void setHomeFlag(boolean homeFlag) 
    {
        this.homeFlag = homeFlag;
    }
}
