package test;

import motor.Motor;

//import motor.Motor;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import time.MyTimer;

import static org.junit.Assert.assertTrue;

public class MotorTest {
	private volatile static MyTimer timer;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		timer = new MyTimer();
		new Thread(timer).start();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		timer.cancelTimer();
	}

	//test fails if motor does not stop within 10.1 seconds
	@Test(timeout=10100)
	public void testMotorTravelTime() throws InterruptedException {
		//create a motor and add to the timer as an observer
		Motor motor = new Motor(timer);

		motor.turnOn();

		while(motor.isOn()){
			Thread.sleep(100);
		}

		//if test makes it here before 10.1 seconds then test passes
		assertTrue(true);
	}

	//test fails if motor does not stop within 5.1 seconds
	@Test(timeout=5100)
	public void testSettingMotorTravelTime() throws InterruptedException {
		//create a motor and add to the timer as an observer
		Motor motor = new Motor(timer);

		motor.turnOn(5000);
		while(motor.isOn()){
			Thread.sleep(100);
		}

		//if test makes it here before 5.1 seconds then test passes
		assertTrue(true);
	}

	@Test
	public void testMotorTurnsOn(){
		Motor motor = new Motor(timer);

		motor.turnOn();

		assertTrue(motor.isOn());
	}

	@Test
	public void testMotorTurnsOff(){
		Motor motor = new Motor(timer);

		motor.turnOff();

		assertTrue(!motor.isOn());
	}

	@Test
	public void testMotorOnWhenAlreadyOn(){
		Motor motor = new Motor(timer);

		motor.turnOn();
		assertTrue(motor.isOn());

		motor.turnOn();
		assertTrue(motor.isOn());
	}

	@Test(timeout = 10100)
	public void testMotorTimeoutWhenAlreadyOn() throws InterruptedException {
		Motor motor = new Motor(timer);

		motor.turnOn();
		assertTrue(motor.isOn());

		motor.turnOn();
		assertTrue(motor.isOn());

		while(motor.isOn()){
			Thread.sleep(100);
		}

		// passes if motor turns off before 10.1s
		assertTrue(true);
	}

	@Test(timeout = 3100)
	public void testCustomMotorTimeoutWhenAlreadyOn() throws InterruptedException {
		Motor motor = new Motor(timer);

		motor.turnOn(3000);
		assertTrue(motor.isOn());

		motor.turnOn();
		assertTrue(motor.isOn());

		while(motor.isOn()){
			Thread.sleep(100);
		}

		// passes if motor turns off before 10.1s
		assertTrue(true);
	}

	@Test
	public void testMotorOffWhenAlreadyOff(){
		Motor motor = new Motor(timer);

		motor.turnOff();
		assertTrue(!motor.isOn());

		motor.turnOff();
		assertTrue(!motor.isOn());
	}

}
