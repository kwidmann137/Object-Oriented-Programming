package motor;

import states.MotorState;

public class OffState implements MotorState{

    private Motor motor;

    OffState(Motor motor){
        this.motor = motor;
    }

    @Override
    public void on() {
        motor.setOn();
    }

    @Override
    public void off() {

    }

    @Override
    public void updateTime() {

    }

    @Override
    public String toString(){
        return "Off";
    }
}
