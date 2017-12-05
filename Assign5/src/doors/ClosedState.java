package doors;

import states.DoorState;

public class ClosedState implements DoorState {

    private Doors doors;

    ClosedState(Doors doors){
        this.doors = doors;
    }

    @Override
    public void open() {
        doors.setOpenState();
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
