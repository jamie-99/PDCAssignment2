package part2;

/*
The Ticket class represents an abstract ticket in the Film Theatre App.
It provides common properties and abstract methods for different types of tickets.
*/
public abstract class Ticket 
{
    protected double normalMoviePrice = 17.0;
    protected double _3DMoviePrice = 18.50;
    protected double _4DMoviePrice = 22.0;
    protected double IMAXMoviePrice = 20.50;
    protected double totalPrice;
    
    public abstract double normalMoviePrice();
    public abstract double _3DMoviePrice();
    public abstract double _4DMoviePrice();
    public abstract double IMAXMoviePrice();
    public abstract double getTotalPrice();
}
