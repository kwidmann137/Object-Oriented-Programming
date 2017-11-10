package models;

import javafx.collections.MapChangeListener;

import java.io.File;

public class Planet {
    private String name;
    private int diameter;
    private double temperature;
    private int numberOfMoons;
    private String imageUrl;
    private PlanetValidator validator;

    public Planet(){
        this.name = "";
        this.diameter = -1;
        this.temperature = -500;
        this.numberOfMoons = -1;
        this.imageUrl = "/images/no_image.png";
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

    public void setDiameter(int diameter){
        if(validator.validateDiameter(diameter)){
            this.diameter = diameter;
        }
    }

    public int getDiameter() {
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

    public void addErrorListener(MapChangeListener<PlanetAttribute, String> listener){
        this.validator.addErrorListener(listener);
    }

    public void saveToFile(File file) throws Exception {
        if(!validator.validatePlanet(this)){
            throw new Exception("Failed to save planet.  Correct errors and try again.");
        }

        PlanetFileGateway.savePlanetToFile(this, file);
    }
}
