package models;

import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableMap;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class PlanetValidator {

    private ObservableMap<PlanetAttribute, String> errors;
    private List<String> allowedFileTypes = Arrays.asList("png", "bmp", "jpeg", "jpg");

    public PlanetValidator(){
        this.errors = FXCollections.observableMap(new HashMap<>());
    }

    boolean validatePlanet(Planet planet){

        validateName(planet.getName());
        validateDiameter(String.valueOf(planet.getDiameter()));
        validateTemperature(String.valueOf(planet.getTemperature()));
        validateMoons(String.valueOf(planet.getNumberOfMoons()));

        return this.hasErrors();
    }

    boolean validateName(String name){

        errors.remove(PlanetAttribute.NAME);

        if(name.length() < 1 || name.length() > 255){
            errors.put(PlanetAttribute.NAME, "Name must be between 1 and 255 characters");
            return false;
        }

        return true;
    }

    boolean validateTemperature(String temp){

        errors.remove(PlanetAttribute.TEMPERATURE);

        double validTemp;

        try{
            validTemp = NumberFormat.getNumberInstance(Locale.US).parse(temp).doubleValue();
        }catch(ParseException e){
            errors.put(PlanetAttribute.TEMPERATURE, "Must be a number");
            return false;
        }

        if(validTemp < -273.15 || validTemp > 500){
            errors.put(PlanetAttribute.TEMPERATURE, "Must be between -273.15C and 500C");
            return false;
        }

        return true;
    }

    boolean validateDiameter(String diameter){

        errors.remove(PlanetAttribute.DIAMETER);

        int validDiameter;
        try{
            validDiameter = NumberFormat.getNumberInstance(Locale.US).parse(diameter).intValue();
        }catch(ParseException e){
            errors.put(PlanetAttribute.DIAMETER, "Diameter must be an integer");
            return false;
        }

        if(validDiameter < 0 || validDiameter > 200000){
            errors.put(PlanetAttribute.DIAMETER, "Diameter must be between 0 and 200,000");
            return false;
        }

        return true;
    }

    boolean validateMoons(String moons){

        errors.remove(PlanetAttribute.MOONS);

        double validMoons;
        try{
            validMoons = NumberFormat.getNumberInstance(Locale.US).parse(moons).intValue();
        }catch(ParseException e){
            errors.put(PlanetAttribute.MOONS, "Moons must be an integer");
            return false;
        }

        if(validMoons < 0 || validMoons > 1000){
            errors.put(PlanetAttribute.MOONS, "Number of moons must be between 0 and 1,000");
            return false;
        }
        return true;
    }

    boolean validateImageFile(File file){

        errors.remove(PlanetAttribute.IMAGE_FILE);

        boolean validFile;

        try {
            validFile =  file != null && file.exists() && file.canRead() && Files.probeContentType(file.toPath()).contains("image");
        } catch (IOException e) {
            errors.put(PlanetAttribute.IMAGE_FILE, "Invalid image file supplied");
            return false;
        }

        if(!validFile){
            errors.put(PlanetAttribute.IMAGE_FILE, "Invalid image file supplied");
            return false;
        }

        return true;

    }

    public String getAllErrors()
    {
        StringBuilder allErrors = new StringBuilder();
        String formattedError;
        for(PlanetAttribute attribute : PlanetAttribute.values()){
            if(errors.containsKey(attribute)){
                formattedError = String.format("%c %s\n", 0x2022, errors.get(attribute));
                allErrors.append(formattedError);
            }
        }
        return allErrors.toString();
    }

    public String getError(PlanetAttribute attribute){
        return errors.get(attribute);
    }

    public boolean hasErrors(){
        return !errors.isEmpty();
    }

    void addErrorListener(MapChangeListener<PlanetAttribute, String> listener){
        this.errors.addListener(listener);
    }

}
