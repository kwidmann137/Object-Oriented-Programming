package elevator;

import motor.Motor;
import states.ElevatorState;

public class IdleState implements ElevatorState {

    private Elevator elevator;
    IdleState(Elevator elevator){
        this.elevator = elevator;
    }

    @Override
    public void up() {
        elevator.getDoors().close();
        int floorsLeft = elevator.TOP_FLOOR - elevator.getCurrentFloor();
        int motorTimeout = getMotorTimeout(floorsLeft);
        elevator.getMotor().turnOn(motorTimeout);
        elevator.setGoingUpState();
    }

    @Override
    public void down() {
        elevator.getDoors().close();
        int floorsLeft = elevator.getCurrentFloor() - elevator.BOTTOM_FLOOR;
        int motorTimeout = getMotorTimeout(floorsLeft);
        elevator.getMotor().turnOn(motorTimeout);
        elevator.setGoingDownState();
    }

    private int getMotorTimeout(int floorsLeft){

        int timeout = floorsLeft * elevator.MS_PER_FLOOR;

        if(timeout < Motor.MAX_RUN_TIME_MS){
            return timeout;
        }else{
            return Motor.MAX_RUN_TIME_MS;
        }

    }

    @Override
    public void openDoors() {
        elevator.getDoors().open();
    }

    @Override
    public void closeDoors() {
        elevator.getDoors().close();
    }

    @Override
    public void handleMotorStateChange(Motor motor) {

    }

    @Override
    public String toString(){
        return "Idle";
    }
}
