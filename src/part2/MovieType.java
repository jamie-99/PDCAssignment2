package part2;

/*
MovieType Enum store representation of 4 different movie types.
*/
public enum MovieType 
{
    _3D("3D"), _4D("4D"), IMAX("IMAX"), NORMAL("Normal");
    
    private final String movieType;
    
    MovieType(String movieType)
    {
        this.movieType = movieType;
    }
    
    public String getMovieType()
    {
        return this.movieType;
    }
}
