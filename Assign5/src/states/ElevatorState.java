package states;

import motor.Motor;

public interface ElevatorState {

    void up();

    void down();

    void openDoors();

    void closeDoors();

    void handleMotorStateChange(Motor motor);

}
