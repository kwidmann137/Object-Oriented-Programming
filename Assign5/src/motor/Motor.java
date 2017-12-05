package motor;

import elevator.Elevator;
import states.ElevatorState;
import states.MotorState;
import time.MyTimer;

import java.util.Observable;
import java.util.Observer;

public class Motor extends Observable implements Observer{

    private OnState on;
    private OffState off;
    private MotorState currentState;
    public static final int MAX_RUN_TIME_MS = 10000;
    private int timeToRun;

    public Motor(MyTimer timer)
    {
        timer.addObserver(this);
        this.on = new OnState(this);
        this.off = new OffState(this);
        this.setState(off);
        this.timeToRun = MAX_RUN_TIME_MS;
    }

    public void turnOff(){
        this.currentState.off();
    }

    public void turnOn(){
        if(!this.isOn()){
            this.timeToRun = MAX_RUN_TIME_MS;
        }
        this.currentState.on();
    }

    public void turnOn(int msToRun){
        if(!this.isOn()){
            this.timeToRun = msToRun;
        }
        this.currentState.on();
    }

    void setOn(){
        this.setState(on);
    }

    void setOff(){
        this.setState(off);
    }

    public boolean isOn(){
        return this.currentState == on;
    }

    public int getRunTime(){
        return this.timeToRun;
    }

    void setState(MotorState state){
        this.currentState = state;
        System.out.println("Motor is " + currentState.toString().toLowerCase());
        this.setChanged();
        this.notifyObservers();
    }

    @Override
    public void update(Observable observable, Object o) {
        this.currentState.updateTime();
    }
}
