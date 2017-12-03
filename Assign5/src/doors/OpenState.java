package doors;

import states.DoorState;
import states.TimedState;
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

    }

    @Override
    public void close() {
        doors.setClosed();
    }

    @Override
    public void updateTime() {
        msOpen += MyTimer.MS_PER_TICK;
        if(msOpen > doors.TIME_TO_CLOSE_MS){
            doors.setClosed();
        }
    }

    @Override
    public String toString(){
        return "Open";
    }

}
