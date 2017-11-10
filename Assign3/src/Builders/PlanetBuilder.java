package Builders;

import models.Planet;
import models.PlanetAttribute;
import models.PlanetFileGateway;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class PlanetBuilder {

    public static Planet BuildPlanetFromFile(File file) throws IOException {
        Planet planet = new Planet();

        HashMap<PlanetAttribute, String> attributes;

        attributes = PlanetFileGateway.getPropertiesFromFile(file);

        planet.setName(attributes.getOrDefault(PlanetAttribute.NAME, ""));
        planet.setDiameter(Integer.parseInt(attributes.getOrDefault(PlanetAttribute.DIAMETER, "-1")));
        planet.setTemperature(Double.parseDouble(attributes.getOrDefault(PlanetAttribute.TEMPERATURE, "-500")));
        planet.setNumberOfMoons(Integer.parseInt(attributes.getOrDefault(PlanetAttribute.MOONS, "-1")));
        File imageFile = new File(attributes.getOrDefault(PlanetAttribute.IMAGE_URL, "/images/no_image.png"));
        planet.setImageUrlFromFile(imageFile);

        return planet;
    }
}
