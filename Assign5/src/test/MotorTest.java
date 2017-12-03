package test;

import static org.junit.Assert.*;

import motor.Motor;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

//import motor.Motor;
import time.MyTimer;

public class MotorTest {
	private static MyTimer timer;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		timer = new MyTimer();
		new Thread(timer).start();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		timer.cancelTimer();
	}

//	//test fails if motor does not stop within 10.1 seconds
//	@Test(timeout=15000)
//	public void testMotorTravelTime() {
//		//create a motor and add to the timer as an observer
//		Motor motor = new Motor(timer);
//
//		motor.turnOn();
//
//		while(motor.isOn()){
//		}
//
//		//if test makes it here before 10.1 seconds then test passes
//	}
//
//	//test fails if motor does not stop within 5.1 seconds
//	@Test(timeout=7000)
//	public void testSettingMotorTravelTime() {
//		//create a motor and add to the timer as an observer
//		Motor motor = new Motor(timer);
//
//		motor.turnOn(5000);
//		while(motor.isOn()){
//		}
//
//		//if test makes it here before 5.1 seconds then test passes
//	}
//
//	@Test
//	public void testMotorTurnsOn(){
//		Motor motor = new Motor(timer);
//
//		motor.turnOn();
//
//		assertTrue(motor.isOn());
//	}
//
//	@Test
//	public void testMotorTurnsOff(){
//		Motor motor = new Motor(timer);
//
//		motor.turnOff();
//
//		assertTrue(!motor.isOn());
//	}
//
//	@Test
//	public void testMotorOnWhenAlreadyOn(){
//		Motor motor = new Motor(timer);
//
//		motor.turnOn();
//		assertTrue(motor.isOn());
//
//		motor.turnOn();
//		assertTrue(motor.isOn());
//	}

	@Test(timeout = 10100)
	public void testMotorTimeoutWhenAlreadyOn(){
		Motor motor = new Motor(timer);

		motor.turnOn();
		assertTrue(motor.isOn());

		motor.turnOn();
		assertTrue(motor.isOn());

		while(motor.isOn()){
		}

		try{
			Thread.sleep(3000);
		}catch (InterruptedException e) {
			fail("Thread failed");
		}

		System.out.println("Post loop on thread:" + Thread.currentThread());

		// passes if motor turns off before 10.1s
		assertTrue(true);
	}

//	@Test
//	public void testMotorOffWhenAlreadyOff(){
//		Motor motor = new Motor(timer);
//
//		motor.turnOff();
//		assertTrue(!motor.isOn());
//
//		motor.turnOff();
//		assertTrue(!motor.isOn());
//	}

}
