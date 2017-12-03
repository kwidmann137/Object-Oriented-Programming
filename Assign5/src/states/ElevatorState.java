package states;

import doors.Doors;
import motor.Motor;

public interface ElevatorState {

    void up();

    void down();

    void openDoors();

    void closeDoors();

    void handleMotorStateChange(Motor motor);

    void handleDoorStateChange(Doors doors);

}
