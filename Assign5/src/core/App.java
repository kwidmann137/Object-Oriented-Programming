package core;

import elevator.Elevator;
import time.MyTimer;

public class App {

    public static void main(String[] args){
        MyTimer timer = new MyTimer();
        new Thread(timer).start();

        Elevator elevator = new Elevator(timer);

        elevator.sendUp();

        long startTime = System.currentTimeMillis();
        long currentTime = System.currentTimeMillis();
        while((currentTime - startTime) < 20000){
            currentTime = System.currentTimeMillis();
        }

        timer.cancelTimer();

    }
}
