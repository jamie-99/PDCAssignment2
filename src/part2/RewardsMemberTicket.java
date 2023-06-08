package part2;

/**
 *
 * @author jamie
 */
public class RewardsMemberTicket extends Ticket
{
    private int numberOfPeople;
    private MovieType movieType;
    private double discountRate;
    
    public RewardsMemberTicket(int numberOfPeople, MovieType movieType, double discountRate)
    {
        this.numberOfPeople = numberOfPeople;
        this.movieType = movieType;
        
        this.discountRate = discountRate;
        
        if (this.movieType.equals(MovieType.NORMAL))
        {
            this.totalPrice = this.normalMoviePrice();
        }
        
        if (this.movieType.equals(MovieType._3D))
        {
            this.totalPrice = this._3DMoviePrice();
        }
        
        if (this.movieType.equals(MovieType._4D))
        {
            this.totalPrice = this._4DMoviePrice();
        }
        
        if (this.movieType.equals(MovieType.IMAX))
        {
            this.totalPrice = this.IMAXMoviePrice();
        }
    }
    
    @Override
    public double normalMoviePrice() 
    {
        return this.numberOfPeople * super.normalMoviePrice;
    }

    @Override
    public double _3DMoviePrice() 
    {
        return this.numberOfPeople * super._3DMoviePrice;
    }

    @Override
    public double _4DMoviePrice() 
    {
        return this.numberOfPeople * super._4DMoviePrice;
    }

    @Override
    public double IMAXMoviePrice() 
    {
        return this.numberOfPeople * super.IMAXMoviePrice;
    }
    
    @Override
    public double getTotalPrice()
    {
        return super.totalPrice * this.discountRate;
    }
}
