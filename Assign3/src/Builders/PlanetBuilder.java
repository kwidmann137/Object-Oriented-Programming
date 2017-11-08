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

        planet.setName(attributes.get(PlanetAttribute.NAME));
        planet.setDiameter(attributes.get(PlanetAttribute.DIAMETER));
        planet.setTemperature(attributes.get(PlanetAttribute.TEMPERATURE));
        planet.setNumberOfMoons(attributes.get(PlanetAttribute.MOONS));
        planet.setImageFile(new File(attributes.get(PlanetAttribute.IMAGE_FILE)));

        return planet;
    }
}
