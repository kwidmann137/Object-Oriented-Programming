package doors;

import elevator.Elevator;
import states.DoorState;
import time.MyTimer;

import java.util.Observable;
import java.util.Observer;

public class Doors extends Observable implements Observer {

    private DoorState currentState;
    private ClosedState closed;
    private OpenState open;
    static final int TIME_TO_CLOSE_MS = 5000;

    public Doors(MyTimer timer)
    {
        timer.addObserver(this);
        this.open = new OpenState(this);
        this.closed = new ClosedState(this);
        this.setState(closed);
    }

    public void open(){
        this.currentState.open();
    }

    public void close(){
        this.currentState.close();
    }

    public boolean areOpen(){
        return this.currentState == open;
    }

    void setState(DoorState state){
        this.currentState = state;
        System.out.println("Elevator doors are " + currentState.toString().toLowerCase());
        this.setChanged();
        this.notifyObservers();
    }

    void setOpen(){
        this.setState(open);
    }

    void setClosed(){
        this.setState(closed);
    }

    @Override
    public void update(Observable observable, Object o) {
        this.currentState.updateTime();
    }


}
