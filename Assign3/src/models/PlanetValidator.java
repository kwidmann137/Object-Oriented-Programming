package models;

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

    private ObservableMap<PlanetAttribute, String> errors;

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

    public boolean validateName(String name){

        errors.remove(PlanetAttribute.NAME);

        if(name.length() < 1 || name.length() > 255){
            errors.put(PlanetAttribute.NAME, "Name must be between 1 and 255 characters");
            return false;
        }

        return true;
    }

    public boolean validateTemperature(String temp){

        errors.remove(PlanetAttribute.TEMPERATURE);

        double validTemp;

        try{
            validTemp = NumberFormat.getNumberInstance(Locale.US).parse(temp).doubleValue();
        }catch(ParseException e){
            errors.put(PlanetAttribute.TEMPERATURE, "Must be a number");
            return false;
        }

        return validateTemperature(validTemp);
    }

    boolean validateTemperature(Double temp){

        errors.remove(PlanetAttribute.TEMPERATURE);

        if(temp < -273.15 || temp > 500){
            errors.put(PlanetAttribute.TEMPERATURE, "Must be between -273.15C and 500C");
            return false;
        }

        return true;

    }

    public boolean validateDiameter(String diameter){

        errors.remove(PlanetAttribute.DIAMETER);

        int validDiameter;
        try{
            validDiameter = NumberFormat.getNumberInstance(Locale.US).parse(diameter).intValue();
        }catch(ParseException e){
            errors.put(PlanetAttribute.DIAMETER, "Diameter must be an integer");
            return false;
        }

        return validateDiameter(validDiameter);
    }

    boolean validateDiameter(int diameter){

        errors.remove(PlanetAttribute.DIAMETER);

        if(diameter < 0 || diameter > 200000){
            errors.put(PlanetAttribute.DIAMETER, "Diameter must be between 0 and 200,000");
            return false;
        }

        return true;
    }


    public boolean validateMoons(String moons){

        errors.remove(PlanetAttribute.MOONS);

        int validMoons;

        try{
            validMoons = NumberFormat.getNumberInstance(Locale.US).parse(moons).intValue();
        }catch(ParseException e){
            errors.put(PlanetAttribute.MOONS, "Moons must be an integer");
            return false;
        }

        return validateMoons(validMoons);
    }

    boolean validateMoons(int moons){
        errors.remove(PlanetAttribute.MOONS);

        if(moons < 0 || moons > 1000){
            errors.put(PlanetAttribute.MOONS, "Number of moons must be between 0 and 1,000");
            return false;
        }

        return true;
    }

    boolean validateImageFile(File file){

        errors.remove(PlanetAttribute.IMAGE_URL);

        boolean validFile;

        try {
            validFile =  file != null && file.exists() && file.canRead() && Files.probeContentType(file.toPath()).contains("image");
        } catch (IOException e) {
            errors.put(PlanetAttribute.IMAGE_URL, "Invalid image file. Valid files are .png, .bmp, .jpg, .jpeg");
            return false;
        }

        if(!validFile){
            errors.put(PlanetAttribute.IMAGE_URL, "Invalid image file supplied.  Valid files are .png, .bmp, .jpg, .jpeg");
            return false;
        }

        return true;

    }

    private boolean hasErrors(){
        return !errors.isEmpty();
    }

    void addErrorListener(MapChangeListener<PlanetAttribute, String> listener){
        this.errors.addListener(listener);
    }

}
