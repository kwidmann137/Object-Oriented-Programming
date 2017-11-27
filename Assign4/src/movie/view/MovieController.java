package movie.view;

import java.net.URL;
import java.util.Objects;
import java.util.Observer;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.fxml.Initializable;
import movie.model.Movie;
import movie.model.MovieSingleton;

public class MovieController implements Initializable {

    @FXML
    private TextField movieTitle;

    @FXML
    private TextField director;

    @FXML
    private TextField releaseYear;

    @FXML
    private TextField writer;

    @FXML
    private Label ratingText;

    @FXML
    private Slider ratingSlider;

    private Observer movieObserver;

    public MovieController() {

    }
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {

        setTextFilters();

        setTextListeners();

        setObservers();

	}

	public void cleanup(){
        MovieSingleton.getMovieInstance().deleteObserver(movieObserver);
    }

	private void setTextFilters(){

        Pattern validReleaseYear = Pattern.compile("[0-9]*");

        UnaryOperator<TextFormatter.Change> filter = c -> {
            String text = c.getControlNewText();
            if (validReleaseYear.matcher(text).matches()) {
                return c ;
            } else {
                return null ;
            }
        };

        TextFormatter<Integer> textFormatter = new TextFormatter<>(filter);
        releaseYear.setTextFormatter(textFormatter);
    }

    private void setTextListeners(){
        Movie movie = MovieSingleton.getMovieInstance();

        releaseYear.textProperty().addListener((observable, oldValue, newValue) -> {
            try{
                int newReleaseYear = Integer.parseInt(newValue);
                movie.setReleaseYear(newReleaseYear);
            }catch (NumberFormatException e){
                movie.setReleaseYear(Movie.DEFAULT_YEAR);
            }
        });

        director.textProperty().addListener((observable, oldValue, newValue) -> movie.setDirector(newValue));

        writer.textProperty().addListener((observable, oldValue, newValue) -> movie.setWriter(newValue));

        movieTitle.textProperty().addListener((observable, oldValue, newValue) -> movie.setMovieTitle(newValue));

        ratingSlider.valueProperty().addListener((observable, oldValue, newValue) -> movie.setRating(newValue.intValue()));
    }

    private void setObservers(){

        movieObserver = (observable, arg) -> {
            if(arg instanceof Movie){
                Movie movie = (Movie) arg;

                if(!Objects.equals(movie.getDirector(), director.getText())){
                    director.setText(movie.getDirector());
                }
                if(!Objects.equals(movie.getMovieTitle(), movieTitle.getText())){
                    movieTitle.setText(movie.getMovieTitle());
                }
                if(!Objects.equals(movie.getWriter(), writer.getText())){
                    writer.setText(movie.getWriter());
                }
                if(movie.getReleaseYear() == Movie.DEFAULT_YEAR){
                    releaseYear.setText("");
                }else{
                    int newYear = movie.getReleaseYear();
                    int displayedYear;
                    if (Objects.equals(releaseYear.getText(), "")) {
                        displayedYear = Movie.DEFAULT_YEAR;
                    }else{
                        displayedYear = Integer.parseInt(releaseYear.getText());
                    }
                    if (displayedYear != newYear) {
                        releaseYear.textProperty().set(String.valueOf(newYear));
                    }
                }
                if(movie.getRating() != ratingSlider.getValue()){
                    ratingText.setText(String.valueOf(movie.getRating()));
                    ratingSlider.setValue(movie.getRating());
                }
            }
        };

        MovieSingleton.getMovieInstance().addObserver(movieObserver);
    }
}
