package part2;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 *
 * @author jamie
 */
public class NonMemberTicket extends Ticket
{
    private int numberOfPeople;
    private MovieType movieType;
    private double discountRate;
    
    public NonMemberTicket(int numberOfPeople, MovieType movieType, double discountRate)
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
        BigDecimal bd = new BigDecimal(super.totalPrice * this.discountRate).setScale(2, RoundingMode.HALF_UP);
        super.totalPrice = bd.doubleValue();
        
        return super.totalPrice;
    }
}