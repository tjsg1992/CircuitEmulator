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
		Thread.sleep(5);
		assertFalse(myCounter.getOutputConnections()[0].hasPower());
		assertFalse(myCounter.getOutputConnections()[1].hasPower());
		
		myClock.powerOn();
		Thread.sleep(5);
		assertTrue(myCounter.getOutputConnections()[0].hasPower());
		assertFalse(myCounter.getOutputConnections()[1].hasPower());
		
		myClock.powerOff();
		Thread.sleep(5);
		assertTrue(myCounter.getOutputConnections()[0].hasPower());
		assertFalse(myCounter.getOutputConnections()[1].hasPower());
		
		myClock.powerOn();
		Thread.sleep(5);
		assertFalse(myCounter.getOutputConnections()[0].hasPower());
		assertTrue(myCounter.getOutputConnections()[1].hasPower());
		
		myClock.powerOff();
		Thread.sleep(5);
		assertFalse(myCounter.getOutputConnections()[0].hasPower());
		assertTrue(myCounter.getOutputConnections()[1].hasPower());
		
		myClock.powerOn();
		Thread.sleep(5);
		assertTrue(myCounter.getOutputConnections()[0].hasPower());
		assertTrue(myCounter.getOutputConnections()[1].hasPower());
		
		myClock.powerOff();
		Thread.sleep(5);
		assertTrue(myCounter.getOutputConnections()[0].hasPower());
		assertTrue(myCounter.getOutputConnections()[1].hasPower());
		
		myClock.powerOn();
		Thread.sleep(5);
		assertFalse(myCounter.getOutputConnections()[0].hasPower());
		assertFalse(myCounter.getOutputConnections()[1].hasPower());
	}

}
