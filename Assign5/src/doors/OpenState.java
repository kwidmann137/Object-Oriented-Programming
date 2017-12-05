package doors;

import states.DoorState;
import time.MyTimer;

public class OpenState implements DoorState {

    private Doors doors;
    private int msOpen;

    OpenState(Doors doors)
    {
        this.doors = doors;
        this.msOpen = 0;
    }


    @Override
    public void open() {
        msOpen = 0;
    }

    @Override
    public void close() {
        doors.setClosedState();
    }

    @Override
    public void updateTime() {
        msOpen += MyTimer.MS_PER_TICK;
        if(msOpen >= Doors.TIME_TO_CLOSE_MS){
            doors.setClosedState();
        }
    }

    @Override
    public String toString(){
        return "Open";
    }

}
