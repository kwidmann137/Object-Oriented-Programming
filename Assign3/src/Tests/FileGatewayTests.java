package Tests;

import Builders.PlanetBuilder;
import Exceptions.SaveException;
import models.Planet;
import org.junit.jupiter.api.Executable;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Created by kyle on 11/10/17.
 */
public class FileGatewayTests {



    @Test
    public void testSaveWithInvalidData() throws IOException, SaveException {
        Planet testPlanet = new Planet();
        testPlanet.setName("");

        Executable closureToTest = () -> {
            File testSaveFile = new File("./src/Tests/TestSaveFile");
            testPlanet.saveToFile(testSaveFile);
        };

        assertThrows(SaveException.class, closureToTest);

    }

    @Test
    public void testOpenWithInvalidPropertyFile(){
        File invalidPropertyFile = new File("./src/Tests/InvalidPropertyFile");
        Planet testPlanet;
        try {
            // Can't test the image since it is stored with a full path which varies
            testPlanet = PlanetBuilder.BuildPlanetFromFile(invalidPropertyFile);
            assertEquals("Neptune", testPlanet.getName());
            assertEquals(10000, testPlanet.getDiameter());
            assertEquals(-21.5, testPlanet.getTemperature());
            assertEquals(-1, testPlanet.getNumberOfMoons() );

        } catch (IOException e) {
            fail("Failed to open file and build planet");
        }
    }

    @Test
    public void testOpenWithFileMissingFields(){

    }
}
