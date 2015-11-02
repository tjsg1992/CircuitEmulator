package circuit.storage;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import transistor.Connection;

public class TFlipFlopTest {
	private Connection myToggle;
	private Connection myClock;
	private TFlipFlop myLatch;

	@Before
	public void setUp() throws Exception {
		myToggle = new Connection();
		myClock = new Connection();
		myLatch = new TFlipFlop(myClock, myToggle);
		
		myToggle.powerOff();
		myClock.powerOff();
	}

	@Test
	public void defaultTest() throws InterruptedException {
		assertFalse(myLatch.getOutputA().hasPower());
		
		myToggle.powerOn();
		Thread.sleep(1);
		assertFalse(myLatch.getOutputA().hasPower());
		
		myClock.powerOn();
		Thread.sleep(1);
		assertTrue(myLatch.getOutputA().hasPower());
		
		myToggle.powerOff();
		Thread.sleep(1);
		assertTrue(myLatch.getOutputA().hasPower());
		
		myClock.powerOff();
		Thread.sleep(1);
		assertTrue(myLatch.getOutputA().hasPower());
		
		myClock.powerOn();
		Thread.sleep(1);
		assertTrue(myLatch.getOutputA().hasPower());
		
		myToggle.powerOn();
		Thread.sleep(1);
		assertTrue(myLatch.getOutputA().hasPower());
		
		myClock.powerOff();
		Thread.sleep(1);
		assertTrue(myLatch.getOutputA().hasPower());
		
		myClock.powerOn();
		Thread.sleep(1);
		assertFalse(myLatch.getOutputA().hasPower());
		
		myToggle.powerOff();
		Thread.sleep(1);
		assertFalse(myLatch.getOutputA().hasPower());
		
		myClock.powerOff();
		Thread.sleep(1);
		assertFalse(myLatch.getOutputA().hasPower());
		
		myToggle.powerOn();
		Thread.sleep(1);
		assertFalse(myLatch.getOutputA().hasPower());
		
		myClock.powerOn();
		Thread.sleep(1);
		assertTrue(myLatch.getOutputA().hasPower());
		
		myClock.powerOff();
		Thread.sleep(1);
		assertTrue(myLatch.getOutputA().hasPower());
		
		myToggle.powerOff();
		Thread.sleep(1);
		assertTrue(myLatch.getOutputA().hasPower());
		
		myClock.powerOn();
		Thread.sleep(1);
		assertTrue(myLatch.getOutputA().hasPower());
		
		myToggle.powerOn();
		Thread.sleep(1);
		assertTrue(myLatch.getOutputA().hasPower());
	}

}
