package test;

import doors.Doors;
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

    private static MyTimer timer;

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
        Motor motor = new Motor(timer);
        Doors doors = new Doors(timer);
        Elevator elevator = new Elevator(motor, doors);

        int startFloor = elevator.getCurrentFloor();
        elevator.goUp();

        while(elevator.getMotor().isOn()  || !elevator.getDoors().areOpen()){
        }
        int secondFloor = elevator.getCurrentFloor();

        assertTrue(secondFloor > startFloor);

        elevator.goUp();

        while(elevator.getMotor().isOn()  || !elevator.getDoors().areOpen()){
        }
        int thirdFloor = elevator.getCurrentFloor();

        assertTrue(thirdFloor > secondFloor);
    }

    @Test
    public void testElevatorGoesDown() throws InterruptedException {
        Motor motor = new Motor(timer);
        Doors doors = new Doors(timer);
        Elevator elevator = new Elevator(motor, doors);

        elevator.setCurrentFloor(3);

        int startFloor = elevator.getCurrentFloor();
        assertTrue(startFloor == 3);

        elevator.goDown();
        while(elevator.getMotor().isOn()  || !elevator.getDoors().areOpen()){
        }

        int secondFloor = elevator.getCurrentFloor();
        assertTrue(secondFloor < startFloor);

        elevator.goDown();
        while(elevator.getMotor().isOn()  || !elevator.getDoors().areOpen()){
        }

        int thirdFloor = elevator.getCurrentFloor();
        assertTrue(thirdFloor < secondFloor);
    }

    @Test
    public void testElevatorDoesNotGoBelowBottomFloor() throws InterruptedException {
        Motor motor = new Motor(timer);
        Doors doors = new Doors(timer);
        Elevator elevator = new Elevator(motor, doors);

        int startFloor = elevator.getCurrentFloor();
        elevator.goDown();
        while(elevator.getMotor().isOn()){
        }

        int endFloor = elevator.getCurrentFloor();

        assertTrue(startFloor == endFloor);
    }

    @Test public void testElevatorDoesNotGoAboveTopFloor() throws InterruptedException {
        Motor motor = new Motor(timer);
        Doors doors = new Doors(timer);
        Elevator elevator = new Elevator(motor, doors);

        elevator.setCurrentFloor(elevator.TOP_FLOOR);

        int startFloor = elevator.getCurrentFloor();
        assertTrue(startFloor == elevator.TOP_FLOOR);

        elevator.goUp();
        while(elevator.getMotor().isOn()){
        }

        int endFloor = elevator.getCurrentFloor();
        assertTrue(startFloor == endFloor);
    }

    @Test
    public void testDoorsCloseBeforeGoingUp(){
        Motor motor = new Motor(timer);
        Doors doors = new Doors(timer);
        Elevator elevator = new Elevator(motor, doors);

        elevator.openDoors();
        assertTrue(elevator.getDoors().areOpen());

        elevator.goUp();
        assertTrue(!elevator.getDoors().areOpen());
    }

    @Test
    public void testDoorsCloseBeforeGoingDown(){
        Motor motor = new Motor(timer);
        Doors doors = new Doors(timer);
        Elevator elevator = new Elevator(motor, doors);

        elevator.setCurrentFloor(elevator.BOTTOM_FLOOR + 1);

        elevator.openDoors();
        assertTrue(elevator.getDoors().areOpen());

        elevator.goDown();
        assertTrue(!elevator.getDoors().areOpen());
    }
}
