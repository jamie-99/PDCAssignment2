package part2;

/*
Membership Enum class stores the representation of 3 different membership status.
*/
public enum Membership 
{
    NON_MEMBER("Non-Member"), REWARDS_MEMBER("Rewards Member"), REWARDS_VIP("Rewards VIP");

    private final String membership;

    Membership(String membership) 
    {
        this.membership = membership;
    }

    public String getMembership() 
    {
        return this.membership;
    }
}
