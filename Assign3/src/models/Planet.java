package models;

import javafx.collections.MapChangeListener;
import javafx.scene.image.Image;

import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

public class Planet {
    private String name;
    private int diameter;
    private double temperature;
    private int numberOfMoons;
    private File imageFile;
    private Image image;
    private PlanetValidator validator;

    public Planet(){
        this.name = "";
        this.diameter = -1;
        this.temperature = -500;
        this.numberOfMoons = -1;
        this.imageFile = new File("/images/no_image.png");
        this.image = new Image(this.imageFile.toURI().toString());
        this.validator = new PlanetValidator();
    }

    public void setName(String name) {
        if(validator.validateName(name)){
            this.name = name;
        }

    }

    public String getName() {
        return name;
    }

    public void setDiameter(String diameter){
        if(validator.validateDiameter(diameter)){
            try {
                this.diameter = NumberFormat.getNumberInstance(Locale.US).parse(diameter).intValue();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    public int getDiameter() {
        return diameter;
    }

    public void setTemperature(String temperature){
        if(validator.validateTemperature(temperature)){
            this.temperature = Double.parseDouble(temperature);
        }

    }

    public double getTemperature() {
        return temperature;
    }

    public void setNumberOfMoons(String moons)
    {
        if(validator.validateMoons(moons)){
            this.numberOfMoons = Integer.parseInt(moons);
        }
    }

    public int getNumberOfMoons() {
        return numberOfMoons;
    }

    public void setImageFile(File file){
        if(validator.validateImageFile(file)){
            this.imageFile = file;
            this.image = new Image(this.imageFile.toURI().toString());
        }
    }

    public String getImageFilePath(){
        return this.imageFile.getPath();
    }

    public Image getImage(){
        return image;
    }

    public PlanetValidator getValidator(){
        return this.validator;
    }

    public void addErrorListener(MapChangeListener<PlanetAttribute, String> listener){
        this.validator.addErrorListener(listener);
    }

    public void saveToFile(File file) throws Exception {
        if(!validator.validatePlanet(this)){
            throw new Exception("Failed to save planet.  Correct errors and try again.");
        }

        PlanetFileGateway.savePlanetToFile(this, file);
    }

    public void addErrorListener(PlanetAttribute imageFile, Object o) {

    }
}
