package Builders;

import Models.Planet;
import Models.PlanetAttributes;
import Models.PlanetFileGateway;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

public class PlanetBuilder {

    /**
     *Parses the file and creates a planet form its contents
     * @param file - The file to load
     * @return the planet object representing the file contents
     * @throws IOException - if unable to read file;
     */
    public static Planet BuildPlanetFromFile(File file) throws IOException {
        Planet planet = new Planet();

        HashMap<PlanetAttributes, String> attributes;

        attributes = PlanetFileGateway.getPropertiesFromFile(file);

        planet.setName(attributes.getOrDefault(PlanetAttributes.NAME, ""));
        planet.setDiameter(Double.parseDouble(attributes.getOrDefault(PlanetAttributes.DIAMETER, "-1")));
        planet.setTemperature(Double.parseDouble(attributes.getOrDefault(PlanetAttributes.TEMPERATURE, "-500")));
        planet.setNumberOfMoons(Integer.parseInt(attributes.getOrDefault(PlanetAttributes.MOONS, "-1")));
        String imageUrl = attributes.getOrDefault(PlanetAttributes.IMAGE_URL, "/Images/no_image.png");
        if(!imageUrl.equals("/Images/no_image.png")){
            imageUrl = imageUrl.substring(5);
        }
        File imageFile = new File(imageUrl);
        planet.setImageUrlFromFile(imageFile);

        return planet;
    }
}
