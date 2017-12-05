package elevator;

import doors.Doors;
import motor.Motor;
import states.ElevatorState;
import time.MyTimer;

import java.util.Observable;
import java.util.Observer;

public class Elevator implements Observer {

    private Doors doors;
    private Motor motor;
    private GoingDownState goingDown;
    private GoingUpState goingUp;
    private IdleState idle;
    private ElevatorState currentState;
    //These should only be public for testing
    public final int BOTTOM_FLOOR = 1;
    public final int TOP_FLOOR = 5;
    final int MS_PER_FLOOR = 10000;
    private int currentFloor;

    public Elevator(MyTimer timer){
        this.motor = new Motor(timer);
        this.motor.addObserver(this);
        this.doors = new Doors(timer);
        this.doors.addObserver(this);
        this.goingUp = new GoingUpState(this);
        this.goingDown = new GoingDownState(this);
        this.idle = new IdleState(this);
        this.setState(idle);
        this.currentFloor = BOTTOM_FLOOR;
    }

    public void sendUp(){
        this.currentState.up();
    }

    public void sendDown(){
        this.currentState.down();
    }

    public void openDoors(){
        this.currentState.openDoors();
    }

    public void closeDoors(){
        this.currentState.closeDoors();
    }

    void setState(ElevatorState elevatorState){
        this.currentState = elevatorState;
        System.out.println("Elevator is " + this.currentState.toString().toLowerCase());
    }

    void setGoingUp(){
        this.setState(goingUp);
    }

    void setGoingDown(){
        this.setState(goingDown);
    }

    void setIdle(){
        this.setState(idle);
    }

    //this should only be public for testing
    public int getCurrentFloor(){
        return this.currentFloor;
    }

    //This should only be public for testing
    public void setCurrentFloor(int floor){
        this.currentFloor = floor;
        System.out.println("The elevator arrives at floor " + currentFloor);
    }

    //This should only be public for testing
    public Doors getDoors(){
        return this.doors;
    }

    //This should only be public for testing
    public Motor getMotor(){
        return this.motor;
    }

    @Override
    public void update(Observable observable, Object o) {
        if(observable instanceof Doors){
            this.currentState.handleDoorStateChange((Doors) observable);
        }else if(observable instanceof Motor){
            this.currentState.handleMotorStateChange((Motor) observable);
        }
    }
}
