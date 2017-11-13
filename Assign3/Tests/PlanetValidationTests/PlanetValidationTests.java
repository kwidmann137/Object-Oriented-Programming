package PlanetValidationTests;

import Models.Planet;
import Models.PlanetAttributes;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.util.StringConverter;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertEquals;

public class PlanetValidationTests {

    private Planet testPlanet = new Planet();

    @Test
    public void testInvalidName(){
        testPlanet.setName("Neptune");
        testPlanet.setName("");
        PlanetAttributes name = PlanetAttributes.NAME;
        assertEquals(true, testPlanet.getValidator().hasError(name));
        assertEquals("Name must be between 1 and 255 characters", testPlanet.getValidator().getError(name));
        assertEquals("Neptune", testPlanet.getName());
    }

    @Test
    public void testInvalidTemperature(){
        testPlanet.setTemperature(100.00);
        testPlanet.setTemperature(-400.00);
        PlanetAttributes temperature = PlanetAttributes.TEMPERATURE;

        assertEquals(true, testPlanet.getValidator().hasError(temperature));
        assertEquals("Must be between -273.15C and 500C", testPlanet.getValidator().getError(temperature));

        testPlanet.getValidator().validateTemperature("abc");
        assertEquals(true, testPlanet.getValidator().hasError(temperature));
        assertEquals("Must be a number", testPlanet.getValidator().getError(temperature));

        assertEquals(100.00, testPlanet.getTemperature(), 0.00);
    }

    @Test
    public void testInvalidDiameter(){
        testPlanet.setDiameter(200);
        testPlanet.setDiameter(-400);
        PlanetAttributes diameter = PlanetAttributes.DIAMETER;

        assertEquals(true, testPlanet.getValidator().hasError(diameter));
        assertEquals("Diameter must be between 0 and 200,000", testPlanet.getValidator().getError(diameter));

        testPlanet.getValidator().validateDiameter("abc");
        assertEquals(true, testPlanet.getValidator().hasError(diameter));
        assertEquals("Must be a number", testPlanet.getValidator().getError(diameter));

        assertEquals(200, testPlanet.getDiameter(), 0.0);
    }

    @Test
    public void testInvalidNumberOfMoons(){
        testPlanet.setNumberOfMoons(200);
        PlanetAttributes numberOfMoons = PlanetAttributes.MOONS;

        testPlanet.setNumberOfMoons(-400);
        assertEquals(true, testPlanet.getValidator().hasError(numberOfMoons));
        assertEquals("Number of moons must be between 0 and 1,000", testPlanet.getValidator().getError(numberOfMoons));

        testPlanet.getValidator().validateMoons("abc");
        assertEquals(true, testPlanet.getValidator().hasError(numberOfMoons));
        assertEquals("Moons must be an integer", testPlanet.getValidator().getError(numberOfMoons));

        assertEquals(200, testPlanet.getNumberOfMoons());
    }

    @Test
    public void testImageFileInvalidFormat(){
        File validFile = new File("./Tests/PlanetValidationTests/ValidImageFile.png");
        File invalidFile = new File("./Tests/PlanetValidationTests/InvalidImageFile");

        testPlanet.setImageUrlFromFile(validFile);
        PlanetAttributes image_url = PlanetAttributes.IMAGE_URL;

        testPlanet.setImageUrlFromFile(invalidFile);
        assertEquals(true, testPlanet.getValidator().hasError(image_url));
        assertEquals("Invalid image file. Valid files are .png, .bmp, .jpg, .jpeg", testPlanet.getValidator().getError(image_url));

        assertEquals(validFile.toURI().toString(), testPlanet.getImageUrl());

    }

    @Test
    public void testImageFileNotExists(){
        File validFile = new File("./Tests/PlanetValidationTests/ValidImageFile.png");
        File invalidFile = new File("./Tests/PlanetValidationTests/NonexistantImageFile.png");

        testPlanet.setImageUrlFromFile(validFile);
        PlanetAttributes image_url = PlanetAttributes.IMAGE_URL;

        testPlanet.setImageUrlFromFile(invalidFile);
        assertEquals(true, testPlanet.getValidator().hasError(image_url));
        assertEquals("File does not exist", testPlanet.getValidator().getError(image_url));

        assertEquals(validFile.toURI().toString(), testPlanet.getImageUrl());
    }
}
