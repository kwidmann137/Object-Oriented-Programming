package Models;

import Exceptions.SaveException;
import javafx.collections.MapChangeListener;

import java.io.File;
import java.io.IOException;

public class Planet {
    private String name;
    private double diameter;
    private double temperature;
    private int numberOfMoons;
    private String imageUrl;
    private PlanetValidator validator;

    /**
     * Object representing a planet
     */
    public Planet(){
        this.name = "";
        this.diameter = -1;
        this.temperature = -500;
        this.numberOfMoons = -1;
        this.imageUrl = "/Images/no_image.png";
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

    public void setDiameter(double diameter){
        if(validator.validateDiameter(diameter)){
            this.diameter = diameter;
        }
    }

    public double getDiameter() {
        return diameter;
    }

    public void setTemperature(Double temperature){
        if(validator.validateTemperature(temperature)){
            this.temperature = temperature;
        }

    }

    public double getTemperature() {
        return temperature;
    }

    public void setNumberOfMoons(int moons)
    {
        if(validator.validateMoons(moons)){
            this.numberOfMoons = moons;
        }
    }

    public int getNumberOfMoons() {
        return numberOfMoons;
    }

    public void setImageUrlFromFile(File file){
        if(validator.validateImageFile(file)){
            this.imageUrl = file.toURI().toString();
        }
    }

    public String getImageUrl(){
        return imageUrl;
    }

    public PlanetValidator getValidator(){
        return this.validator;
    }

    /**
     * Adds a listener to the specific planet attribute in the change listener
     * @param listener - change listener for the specific attribute
     */
    public void addErrorListener(MapChangeListener<PlanetAttributes, String> listener){
        this.validator.addErrorListener(listener);
    }

    /**
     * Saves the planet to the given file if all fields are valid
     * @param file - file to save to
     * @throws SaveException - If planet is invalid
     * @throws IOException - if error while saving to file
     */
    public void saveToFile(File file) throws SaveException, IOException {
        if(!validator.validatePlanet(this) || validator.hasErrors()){
            throw new SaveException("Failed to save planet.  Correct errors and try again.");
        }

        PlanetFileGateway.savePlanetToFile(this, file);
    }
}
