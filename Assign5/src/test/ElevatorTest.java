package test;

import elevator.Elevator;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import time.MyTimer;

import static org.junit.Assert.assertTrue;

/**
 * Created by kyle on 12/4/17.
 */
public class ElevatorTest {

    private volatile static MyTimer timer;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        timer = new MyTimer();
        new Thread(timer).start();
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        timer.cancelTimer();
    }

    @Test
    public void testElevatorGoesUp(){
        Elevator eleavtor = new Elevator(timer);

        eleavtor.sendUp();

    }
}
