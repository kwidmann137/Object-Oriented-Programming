package motor;

import states.MotorState;
import time.MyTimer;

public class OnState implements MotorState{

    private Motor motor;
    private int msRun;

    OnState(Motor motor){
        this.motor = motor;
        msRun = 0;
    }

    @Override
    public void on() {

    }

    @Override
    public void off() {
        motor.setOffState();
    }

    @Override
    public void updateTime() {
        msRun += MyTimer.MS_PER_TICK;
        if(msRun >= motor.getRunTime()){
            msRun = 0;
            motor.setOffState();
        }
    }

    @Override
    public String toString(){
        return "On";
    }
}
