package elevator;

import doors.Doors;
import motor.Motor;
import states.ElevatorState;

public class GoingUpState implements ElevatorState {

    private Elevator elevator;
    private boolean motorRunning = false;
    private boolean doorsOpen = false;

    GoingUpState(Elevator elevator){
        this.elevator = elevator;
    }

    @Override
    public void up() {
    }

    @Override
    public void down() {

    }

    @Override
    public void openDoors() {

    }

    @Override
    public void closeDoors() {

    }

    @Override
    public void handleMotorStateChange(Motor motor) {
        this.motorRunning = motor.isOn();

        if(!motorRunning){
            elevator.setIdle();
            updateElevatorFloor();
            elevator.openDoors();
        }
    }

    private void updateElevatorFloor(){

        int floorsTraveled = Math.round(elevator.getMotor().getRunTime()/elevator.MS_PER_FLOOR);
        int currentFloor = elevator.getCurrentFloor() + floorsTraveled;

        if(currentFloor > elevator.TOP_FLOOR){
            currentFloor = elevator.TOP_FLOOR;
        }

        elevator.setCurrentFloor(currentFloor);

    }

    @Override
    public void handleDoorStateChange(Doors doors) {
        this.doorsOpen = doors.areOpen();
    }

    @Override
    public String toString(){
        return "Going up";
    }
}
