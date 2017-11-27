package movie.view;

import java.net.URL;
import java.util.Objects;
import java.util.Observable;
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

public class MovieController implements Initializable, Observer {

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

    /**
     * Initializes text filters, listeners and observers
     * @param location
     * @param resources
     */
	@Override
	public void initialize(URL location, ResourceBundle resources) {

        setTextFilters();

        setTextListeners();

        MovieSingleton.getMovieInstance().addObserver(this);

	}

	private void setTextFilters(){

        Pattern validReleaseYear = Pattern.compile("[0-9]{0,4}");

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

    /**
     * Sets the update method for the observed Movie instance
     * @param o - observable object
     * @param arg - Object which should represent a Movie instance
     */
    @Override
    public void update(Observable o, Object arg) {
        if(arg instanceof Movie){
            Movie movie = (Movie) arg;

            director.setText(movie.getDirector());
            movieTitle.setText(movie.getMovieTitle());
            writer.setText(movie.getWriter());
            if(movie.getReleaseYear() == Movie.DEFAULT_YEAR){
                releaseYear.setText("");
            }else{
                releaseYear.setText(String.valueOf(movie.getReleaseYear()));
            }
            ratingText.setText(String.valueOf(movie.getRating()));
            ratingSlider.setValue(movie.getRating());
        }

    }
}
