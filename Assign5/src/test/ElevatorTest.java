package test;

import elevator.Elevator;
import motor.Motor;
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
    public void testElevatorGoesUp() throws InterruptedException {
        Elevator elevator = new Elevator(timer);

        int startFloor = elevator.getCurrentFloor();
        elevator.sendUp();

        Thread.sleep(Motor.MAX_RUN_TIME_MS);
        int secondFloor = elevator.getCurrentFloor();

        assertTrue(secondFloor > startFloor);

        elevator.sendUp();

        Thread.sleep(Motor.MAX_RUN_TIME_MS);
        int thirdFloor = elevator.getCurrentFloor();

        assertTrue(thirdFloor > secondFloor);
    }

    @Test
    public void testElevatorGoesDown() throws InterruptedException {
        Elevator elevator = new Elevator(timer);

        elevator.setCurrentFloor(3);

        int startFloor = elevator.getCurrentFloor();
        assertTrue(startFloor == 3);

        elevator.sendDown();
        Thread.sleep(Motor.MAX_RUN_TIME_MS);

        int secondFloor = elevator.getCurrentFloor();
        assertTrue(secondFloor < startFloor);

        elevator.sendDown();
        Thread.sleep(Motor.MAX_RUN_TIME_MS);

        int thirdFloor = elevator.getCurrentFloor();
        assertTrue(thirdFloor < secondFloor);
    }

    @Test
    public void testElevatorDoesNotGoBelowBottomFloor() throws InterruptedException {
        Elevator elevator = new Elevator(timer);

        int startFloor = elevator.getCurrentFloor();
        elevator.sendDown();
        Thread.sleep(Motor.MAX_RUN_TIME_MS);

        int endFloor = elevator.getCurrentFloor();

        assertTrue(startFloor == endFloor);
    }

    @Test public void testElevatorDoesNotGoAboveTopFloor() throws InterruptedException {
        Elevator elevator = new Elevator(timer);

        elevator.setCurrentFloor(elevator.TOP_FLOOR);

        int startFloor = elevator.getCurrentFloor();
        assertTrue(startFloor == elevator.TOP_FLOOR);

        elevator.sendUp();
        Thread.sleep(Motor.MAX_RUN_TIME_MS);

        int endFloor = elevator.getCurrentFloor();
        assertTrue(startFloor == endFloor);
    }

    @Test
    public void testDoorsCloseBeforeGoingUp(){
        Elevator elevator = new Elevator(timer);

        elevator.openDoors();
        assertTrue(elevator.getDoors().areOpen());

        elevator.sendUp();
        assertTrue(!elevator.getDoors().areOpen());
    }

    @Test
    public void testDoorsCloseBeforeGoinDown(){
        Elevator elevator = new Elevator(timer);

        elevator.openDoors();
        assertTrue(elevator.getDoors().areOpen());

        elevator.sendDown();
        assertTrue(!elevator.getDoors().areOpen());
    }
}
