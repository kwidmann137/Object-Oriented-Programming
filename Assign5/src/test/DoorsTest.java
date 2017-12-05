package test;

import doors.Doors;
import motor.Motor;

//import motor.Motor;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import time.MyTimer;

import static org.junit.Assert.assertTrue;

public class DoorsTest {
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

    @Test(timeout=5100)
    public void testDoorOpenTime() throws InterruptedException {
        //create a motor and add to the timer as an observer
        Doors doors = new Doors(timer);

        assertTrue(!doors.areOpen());

        doors.open();

        assertTrue(doors.areOpen());

        while(doors.areOpen()){
            Thread.sleep(500);
        }

        //if test makes it here before 5.1 seconds then test passes
        assertTrue(true);
    }

    @Test
    public void testDoorsOpen(){
        Doors doors = new Doors(timer);

        assertTrue(!doors.areOpen());

        doors.open();
        assertTrue(doors.areOpen());
    }

    @Test
    public void testDoorsClose() throws InterruptedException {
        Doors doors = new Doors(timer);

        doors.open();
        assertTrue(doors.areOpen());

        doors.close();

        Thread.sleep(5000);

        assertTrue(!doors.areOpen());
    }

    @Test
    public void testDoorTimeoutWhenAlreadyOpen() throws InterruptedException {
        Doors doors = new Doors(timer);

        assertTrue(!doors.areOpen());

        doors.open();
        assertTrue(doors.areOpen());

        Thread.sleep(3000);

        doors.open();
        assertTrue(doors.areOpen());

        Thread.sleep(3000);

        assertTrue(doors.areOpen());

        Thread.sleep(3000);
        assertTrue(!doors.areOpen());
    }

    @Test
    public void testDoorsClosedWhenAlreadyClosed(){
        Doors doors = new Doors(timer);

        assertTrue(!doors.areOpen());

        doors.close();

        assertTrue(!doors.areOpen());
    }

}
