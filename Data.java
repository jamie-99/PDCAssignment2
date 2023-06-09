package part2;

/*
Data class serves as a simple container for storing boolean flags that represent different states or conditions within an application. 
It provides getter and setter methods to access and modify these flags.
 */
public class Data {

    private boolean signInFlag;
    private boolean homeFlag;

    public Data() {
        this.signInFlag = false;
        this.homeFlag = false;
    }

    public boolean getSignInFlag() {
        return signInFlag;
    }

    public void setSignInFlag(boolean signInFlag) {
        this.signInFlag = signInFlag;
    }

    public boolean getHomeFlag() {
        return homeFlag;
    }

    public void setHomeFlag(boolean homeFlag) {
        this.homeFlag = homeFlag;
    }
}
