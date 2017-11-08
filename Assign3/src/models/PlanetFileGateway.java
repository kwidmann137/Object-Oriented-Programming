package models;

import java.io.*;
import java.util.HashMap;
import java.util.Properties;

public class PlanetFileGateway {

    public static void savePlanetToFile(Planet planet, File file) throws IOException {

        OutputStream outputFile = new FileOutputStream(file);

        // load a properties file
        Properties newProps = new Properties();

        // get the property value and print it out
        newProps.setProperty(PlanetAttribute.NAME.toString(), planet.getName());
        newProps.setProperty(PlanetAttribute.DIAMETER.toString(), String.valueOf(planet.getDiameter()));
        newProps.setProperty(PlanetAttribute.TEMPERATURE.toString(), String.valueOf(planet.getTemperature()));
        newProps.setProperty(PlanetAttribute.MOONS.toString(), String.valueOf(planet.getNumberOfMoons()));
        newProps.setProperty(PlanetAttribute.IMAGE_FILE.toString(), planet.getImageFilePath());

        newProps.store(outputFile, null);

        outputFile.close();
    }

    public static HashMap<PlanetAttribute, String> getPropertiesFromFile(File file) throws IOException {

        InputStream input = new FileInputStream(file);
        Properties fileProps = new Properties();
        fileProps.load(input);

        HashMap<PlanetAttribute, String> planetProperties = new HashMap<>();

        for(PlanetAttribute attribute : PlanetAttribute.values()){
            planetProperties.put(attribute, fileProps.getProperty(attribute.toString()));
        }

        input.close();

        return planetProperties;
    }
}
