package OpenAndSaveTests;

import Builders.PlanetBuilder;
import Exceptions.SaveException;
import Models.Planet;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Created by kyle on 11/10/17.
 */
public class FileGatewayTests {

    @Test(expected = SaveException.class)
    public void testSaveWithInvalidData() throws IOException, SaveException {
        Planet testPlanet = new Planet();
        testPlanet.setName("");

        File testSaveFile = new File("./Tests/OpenAndSaveTests/TestSaveFile");
        testPlanet.saveToFile(testSaveFile);

    }

    @Test
    public void testOpenWithInvalidPropertyFile(){
        File invalidPropertyFile = new File("./Tests/OpenAndSaveTests/InvalidPropertyFile");
        Planet testPlanet;
        try {
            // Can't test the image since it is stored with a full path which varies
            testPlanet = PlanetBuilder.BuildPlanetFromFile(invalidPropertyFile);
            assertEquals("Neptune", testPlanet.getName());
            assertEquals(10000, testPlanet.getDiameter(), 0.0);
            assertEquals(-21.5, testPlanet.getTemperature(), 0.0);
            //Should default to -1 since moons is not replace with an invalid property name
            assertEquals(-1, testPlanet.getNumberOfMoons() );

        } catch (IOException e) {
            fail("Failed to open file and build planet");
        }
    }

    @Test
    public void testOpenWithFileMissingFields(){
        File invalidPropertyFile = new File("./Tests/OpenAndSaveTests/MissingImageAndDiameterFile");
        Planet testPlanet;
        try {
            // Can't test the image since it is stored with a full path which varies
            testPlanet = PlanetBuilder.BuildPlanetFromFile(invalidPropertyFile);
            assertEquals("Neptune", testPlanet.getName());
            assertEquals(-1, testPlanet.getDiameter(), 0.0);
            assertEquals(-21.5, testPlanet.getTemperature(), 0.0);
            //Should default to -1 since moons is not replace with an invalid property name
            assertEquals(12, testPlanet.getNumberOfMoons() );

        } catch (IOException e) {
            fail("Failed to open file and build planet");
        }
    }

    @Test
    public void testSaveAndOpen() throws IOException, SaveException {
        File testSaveFile = new File("./Tests/OpenAndSaveTests/TestSaveFile");
        File testImageFile = new File("./Tests/OpenAndSaveTests/ValidImageFile.png");

        Planet testPlanet = new Planet();

        testPlanet.setName("Test Name");
        testPlanet.setDiameter(100188);
        testPlanet.setTemperature(180.00);
        testPlanet.setNumberOfMoons(18);
        testPlanet.setImageUrlFromFile(new File(testImageFile.toURI()));

        testPlanet.saveToFile(testSaveFile);

        Planet loadedPlanet = PlanetBuilder.BuildPlanetFromFile(testSaveFile);

        assertEquals("Test Name", loadedPlanet.getName());
        assertEquals(100188, loadedPlanet.getDiameter(), 0.0);
        assertEquals(180.00, loadedPlanet.getTemperature(), 0.0);
        assertEquals(18, loadedPlanet.getNumberOfMoons());
        assertEquals(testImageFile.toURI().toString(), testPlanet.getImageUrl());
    }

}
