package movie.model;

import java.util.Observable;
import java.util.Observer;

public class Movie {
	private String movieTitle;
	private int releaseYear;
	private String director;
	private String writer;
	private int rating;
	private Observable observableMovie;

	public static final int DEFAULT_YEAR = -1;
	
	public Movie(String title, int releaseYear, String director, String writer, int rating) {
		this.movieTitle = title;
		this.releaseYear = releaseYear;
		this.director = director;
		this.writer = writer;
		this.rating = rating;
		this.observableMovie = new ObservableMovie();
	}

	public String getMovieTitle() {
		return movieTitle;
	}

	public void setMovieTitle(String movieTitle) {
		this.movieTitle = movieTitle;
		this.notifyObservers(this);
	}

	public int getReleaseYear() {
		return releaseYear;
	}

	public void setReleaseYear(int releaseYear) {
		this.releaseYear = releaseYear;
		this.notifyObservers(this);
	}

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
		this.notifyObservers(this);
	}

	public String getWriter() {
		return writer;
	}

	public void setWriter(String writer) {
		this.writer = writer;
		this.notifyObservers(this);
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
		this.notifyObservers(this);
	}

	public void addObserver(Observer observer){
		this.observableMovie.addObserver(observer);
	}

	public void deleteObserver(Observer observer){
		this.observableMovie.deleteObserver(observer);
	}

	public void deleteObservers(){
		this.observableMovie.deleteObservers();
	}

	void notifyObservers(){
		this.observableMovie.notifyObservers();
	}

	void notifyObservers(Object arg){
		this.observableMovie.notifyObservers(arg);
	}
}
