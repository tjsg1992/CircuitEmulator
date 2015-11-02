package circuit.storage;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import transistor.Connection;

public class ETDFlipFlopTest {
	
	private Connection myClock;
	private Connection myData;
	private ETDFlipFlop myLatch;

	@Before
	public void setUp() throws Exception {
		myClock = new Connection();
		myData = new Connection();
		myLatch = new ETDFlipFlop(myData, myClock);
	}

	@Test
	public void test() throws InterruptedException {
		assertFalse(myLatch.getOutputA().hasPower());
		assertTrue(myLatch.getOutputB().hasPower());
		
		myData.powerOn();
		Thread.sleep(5);
		assertFalse(myLatch.getOutputA().hasPower());
		assertTrue(myLatch.getOutputB().hasPower());
		
		myData.powerOff();
		Thread.sleep(5);
		assertFalse(myLatch.getOutputA().hasPower());
		assertTrue(myLatch.getOutputB().hasPower());
		
		myClock.powerOn();
		Thread.sleep(5);
		assertFalse(myLatch.getOutputA().hasPower());
		assertTrue(myLatch.getOutputB().hasPower());
		
		myClock.powerOff();
		Thread.sleep(5);
		assertFalse(myLatch.getOutputA().hasPower());
		assertTrue(myLatch.getOutputB().hasPower());
		
		myData.powerOn();
		Thread.sleep(5);
		assertFalse(myLatch.getOutputA().hasPower());
		assertTrue(myLatch.getOutputB().hasPower());
		
		myClock.powerOn();
		Thread.sleep(5);
		assertTrue(myLatch.getOutputA().hasPower());
		assertFalse(myLatch.getOutputB().hasPower());
		
		myData.powerOff();
		Thread.sleep(5);
		assertTrue(myLatch.getOutputA().hasPower());
		assertFalse(myLatch.getOutputB().hasPower());
		
		myClock.powerOff();
		Thread.sleep(5);
		assertTrue(myLatch.getOutputA().hasPower());
		assertFalse(myLatch.getOutputB().hasPower());
		
		myClock.powerOn();
		Thread.sleep(5);
		assertFalse(myLatch.getOutputA().hasPower());
		assertTrue(myLatch.getOutputB().hasPower());
		
		myData.powerOn();
		Thread.sleep(5);
		assertFalse(myLatch.getOutputA().hasPower());
		assertTrue(myLatch.getOutputB().hasPower());
		
		myClock.powerOff();
		Thread.sleep(5);
		assertFalse(myLatch.getOutputA().hasPower());
		assertTrue(myLatch.getOutputB().hasPower());
		
		myData.powerOff();
		Thread.sleep(5);
		assertFalse(myLatch.getOutputA().hasPower());
		assertTrue(myLatch.getOutputB().hasPower());
		
		myClock.powerOn();
		Thread.sleep(5);
		assertFalse(myLatch.getOutputA().hasPower());
		assertTrue(myLatch.getOutputB().hasPower());
		
		myClock.powerOff();
		Thread.sleep(5);
		assertFalse(myLatch.getOutputA().hasPower());
		assertTrue(myLatch.getOutputB().hasPower());
		
		myData.powerOn();
		Thread.sleep(5);
		assertFalse(myLatch.getOutputA().hasPower());
		assertTrue(myLatch.getOutputB().hasPower());
		
		myClock.powerOn();
		Thread.sleep(5);
		assertTrue(myLatch.getOutputA().hasPower());
		assertFalse(myLatch.getOutputB().hasPower());
	}

}
