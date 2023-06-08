package part2;

/*
Data class serves as a simple container for storing boolean flags that represent different states or conditions within an application. 
It provides getter and setter methods to access and modify these flags.
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
