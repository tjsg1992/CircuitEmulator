package circuit.clock;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import transistor.Connection;

public class RippleCounterTest {
	private Connection myClock;
	private RippleCounter myCounter;

	@Before
	public void setUp() throws Exception {
		myClock = new Connection();
		myCounter = new RippleCounter(myClock);
	}

	@Test
	public void test() throws InterruptedException {
		Thread.sleep(0, 50);
		assertFalse(myCounter.getOutputConnections()[0].hasPower());
		assertFalse(myCounter.getOutputConnections()[1].hasPower());
		
		myClock.powerOn();
		Thread.sleep(0, 1000);
		assertTrue(myCounter.getOutputConnections()[0].hasPower());
		assertFalse(myCounter.getOutputConnections()[1].hasPower());
		
		myClock.powerOff();
		Thread.sleep(0, 1000);
		assertTrue(myCounter.getOutputConnections()[0].hasPower());
		assertFalse(myCounter.getOutputConnections()[1].hasPower());
		
		myClock.powerOn();
		Thread.sleep(0, 1000);
		assertFalse(myCounter.getOutputConnections()[0].hasPower());
		assertTrue(myCounter.getOutputConnections()[1].hasPower());
		
		myClock.powerOff();
		Thread.sleep(0, 1000);
		assertFalse(myCounter.getOutputConnections()[0].hasPower());
		assertTrue(myCounter.getOutputConnections()[1].hasPower());
		
		myClock.powerOn();
		Thread.sleep(0, 1000);
		assertTrue(myCounter.getOutputConnections()[0].hasPower());
		assertTrue(myCounter.getOutputConnections()[1].hasPower());
		
		myClock.powerOff();
		Thread.sleep(0, 1000);
		assertTrue(myCounter.getOutputConnections()[0].hasPower());
		assertTrue(myCounter.getOutputConnections()[1].hasPower());
		
		myClock.powerOn();
		Thread.sleep(0, 1000);
		assertFalse(myCounter.getOutputConnections()[0].hasPower());
		assertFalse(myCounter.getOutputConnections()[1].hasPower());
	}
	
	@Test
	public void actualClockTest() throws InterruptedException {
		Clock clock = new Clock();
		myCounter = new RippleCounter(clock.getOutput());
		
		clock.start();		
		Thread.sleep(5 - System.currentTimeMillis() % 5 + 2);

		assertTrue(myCounter.getOutputConnections()[0].hasPower());
		assertFalse(myCounter.getOutputConnections()[1].hasPower());
		
		Thread.sleep(10);
		assertFalse(myCounter.getOutputConnections()[0].hasPower());
		assertTrue(myCounter.getOutputConnections()[1].hasPower());
		
		Thread.sleep(10);
		assertTrue(myCounter.getOutputConnections()[0].hasPower());
		assertTrue(myCounter.getOutputConnections()[1].hasPower());
		
		Thread.sleep(10);
		assertFalse(myCounter.getOutputConnections()[0].hasPower());
		assertFalse(myCounter.getOutputConnections()[1].hasPower());
		
		Thread.sleep(10);
		assertTrue(myCounter.getOutputConnections()[0].hasPower());
		assertFalse(myCounter.getOutputConnections()[1].hasPower());
		
		Thread.sleep(40);
		assertTrue(myCounter.getOutputConnections()[0].hasPower());
		assertFalse(myCounter.getOutputConnections()[1].hasPower());
		
		Thread.sleep(400);
		assertTrue(myCounter.getOutputConnections()[0].hasPower());
		assertFalse(myCounter.getOutputConnections()[1].hasPower());
	}

}
