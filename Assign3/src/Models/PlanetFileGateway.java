package Models;

import java.io.*;
import java.util.HashMap;
import java.util.Properties;

public class PlanetFileGateway {

    static void savePlanetToFile(Planet planet, File file) throws IOException {

        OutputStream outputFile = new FileOutputStream(file);

        // load a properties file
        Properties newProps = new Properties();

        // get the property value and print it out
        newProps.setProperty(PlanetAttributes.NAME.toString(), planet.getName());
        newProps.setProperty(PlanetAttributes.DIAMETER.toString(), String.valueOf(planet.getDiameter()));
        newProps.setProperty(PlanetAttributes.TEMPERATURE.toString(), String.valueOf(planet.getTemperature()));
        newProps.setProperty(PlanetAttributes.MOONS.toString(), String.valueOf(planet.getNumberOfMoons()));
        newProps.setProperty(PlanetAttributes.IMAGE_URL.toString(), planet.getImageUrl());

        newProps.store(outputFile, null);

        outputFile.close();
    }

    public static HashMap<PlanetAttributes, String> getPropertiesFromFile(File file) throws IOException {

        InputStream input = new FileInputStream(file);
        Properties fileProps = new Properties();
        fileProps.load(input);

        HashMap<PlanetAttributes, String> planetProperties = new HashMap<>();

        for(PlanetAttributes attribute : PlanetAttributes.values()){
            if(fileProps.getProperty(attribute.toString()) != null){
                planetProperties.put(attribute, fileProps.getProperty(attribute.toString()));
            }
        }

        input.close();

        return planetProperties;
    }
}
