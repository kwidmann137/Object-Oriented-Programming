package doors;

import states.DoorState;
import states.TimedState;

public class ClosedState implements DoorState {

    private Doors doors;

    ClosedState(Doors doors){
        this.doors = doors;
    }

    @Override
    public void open() {
        doors.setOpen();
    }

    @Override
    public void close() {

    }

    @Override
    public void updateTime() {

    }

    @Override
    public String toString(){
        return "Closed";
    }

}
