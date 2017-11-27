package movie.model;

public class MovieSingleton {

    private static Movie movie;

    public synchronized static Movie getMovieInstance(){
        if(movie == null){
            movie = new Movie("", Movie.DEFAULT_YEAR, "", "", 0);

        }
        return movie;
    }

}
