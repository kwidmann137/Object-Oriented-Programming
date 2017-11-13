package Models;

import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableMap;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Locale;

public class PlanetValidator {

    private ObservableMap<PlanetAttributes, String> errors;

    PlanetValidator(){
        this.errors = FXCollections.observableMap(new HashMap<>());
    }

    boolean validatePlanet(Planet planet){

        validateName(planet.getName());
        validateDiameter(String.valueOf(planet.getDiameter()));
        validateTemperature(String.valueOf(planet.getTemperature()));
        validateMoons(String.valueOf(planet.getNumberOfMoons()));

        return !this.hasErrors();
    }

    /**
     * Validates a planet name meets all requirements
     * @param name - string to be validated
     * @return - true if valid, false otherwise
     */
    public boolean validateName(String name){

        errors.remove(PlanetAttributes.NAME);

        if(name.length() < 1 || name.length() > 255){
            errors.put(PlanetAttributes.NAME, "Name must be between 1 and 255 characters");
            return false;
        }

        return true;
    }

    /**
     * Validates the temperature meets all requirements
     * @param temp - string to be validated
     * @return - true if valid, false otherwise
     */
    public boolean validateTemperature(String temp){

        errors.remove(PlanetAttributes.TEMPERATURE);

        double validTemp;

        if(!temp.matches("^-?[0-9,]+[.]?[0-9]*$")){
            errors.put(PlanetAttributes.TEMPERATURE, "Must be a number");
            return false;
        }

        try{
            validTemp = NumberFormat.getNumberInstance(Locale.US).parse(temp).doubleValue();
        }catch(ParseException e){
            errors.put(PlanetAttributes.TEMPERATURE, "Must be a number");
            return false;
        }

        return validateTemperature(validTemp);
    }

    boolean validateTemperature(Double temp){

        errors.remove(PlanetAttributes.TEMPERATURE);

        if(temp < -273.15 || temp > 500){
            errors.put(PlanetAttributes.TEMPERATURE, "Must be between -273.15C and 500C");
            return false;
        }

        return true;

    }

    /**
     * Validates a diameter meets all requirements
     * @param diameter - string to be validated
     * @return - true if valid, false otherwise
     */
    public boolean validateDiameter(String diameter){

        errors.remove(PlanetAttributes.DIAMETER);

        double validDiameter;

        if(!diameter.matches("^[0-9,]+[.]?[0-9]*$")){
            errors.put(PlanetAttributes.DIAMETER, "Must be a number");
            return false;
        }

        try{
            validDiameter = NumberFormat.getNumberInstance(Locale.US).parse(diameter).doubleValue();
        }catch(ParseException e){
            errors.put(PlanetAttributes.DIAMETER, "Must be a number");
            return false;
        }

        return validateDiameter(validDiameter);
    }

    boolean validateDiameter(double diameter){

        errors.remove(PlanetAttributes.DIAMETER);

        if(diameter < 0 || diameter > 200000){
            errors.put(PlanetAttributes.DIAMETER, "Diameter must be between 0 and 200,000");
            return false;
        }

        return true;
    }

    /**
     * Validtes the moons meets all requirements
     * @param moons - string to be validated
     * @return  - true if valid, false otherwise
     */
    public boolean validateMoons(String moons){

        errors.remove(PlanetAttributes.MOONS);

        int validMoons;

        try{
            validMoons = NumberFormat.getNumberInstance(Locale.US).parse(moons).intValue();
        }catch(ParseException e){
            errors.put(PlanetAttributes.MOONS, "Moons must be an integer");
            return false;
        }

        return validateMoons(validMoons);
    }

    boolean validateMoons(int moons){
        errors.remove(PlanetAttributes.MOONS);

        if(moons < 0 || moons > 1000){
            errors.put(PlanetAttributes.MOONS, "Number of moons must be between 0 and 1,000");
            return false;
        }

        return true;
    }

    boolean validateImageFile(File file){

        errors.remove(PlanetAttributes.IMAGE_URL);

        if(file == null){
            errors.put(PlanetAttributes.IMAGE_URL, "No file given.");
            return false;
        }

        if(!file.exists()){
            errors.put(PlanetAttributes.IMAGE_URL, "File does not exist");
            return false;
        }

        if(!file.canRead()){
            errors.put(PlanetAttributes.IMAGE_URL, "Can not read file");
            return false;
        }

        try {
            if(!Files.probeContentType(file.toPath()).contains("image")){
                errors.put(PlanetAttributes.IMAGE_URL, "Invalid image file. Valid files are .png, .bmp, .jpg, .jpeg");
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return true;

    }

    private boolean hasErrors(){
        return !errors.isEmpty();
    }

    /**
     * Determines if an attribute currently has an errors
     * @param attribute - attribute to check for errors
     * @return - True if attribute has errors, false otherwise
     */
    public boolean hasError(PlanetAttributes attribute){
        return errors.containsKey(attribute);
    }

    /**
     * Gets an error for hte specific attribute
     * @param attribute - Attribute to retrieve error for
     * @return - String for error, null otherwise
     */
    public String getError(PlanetAttributes attribute){
        return errors.get(attribute);
    }

    void addErrorListener(MapChangeListener<PlanetAttributes, String> listener){
        this.errors.addListener(listener);
    }

}
