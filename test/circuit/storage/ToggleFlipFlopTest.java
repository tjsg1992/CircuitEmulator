package circuit.storage;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import transistor.Connection;

public class ToggleFlipFlopTest {
	private Connection myClock;
	private ToggleFlipFlop myLatch;

	@Before
	public void setup() {
		myClock = new Connection();
		myLatch = new ToggleFlipFlop(myClock);
	}
	@Test
	public void test() {
		myLatch.printStatus();
		assertFalse(myLatch.getOutputA().hasPower());
		myClock.powerOn();
		myLatch.printStatus();
		assertFalse(myLatch.getOutputA().hasPower());
		myClock.powerOff();
		myLatch.printStatus();
		assertTrue(myLatch.getOutputA().hasPower());
		myClock.powerOn();
		myLatch.printStatus();
		assertTrue(myLatch.getOutputA().hasPower());
		myClock.powerOff();
		myLatch.printStatus();
		//assertFalse(myLatch.getOutputA().hasPower());
		myClock.powerOn();
		myLatch.printStatus();
		//assertFalse(myLatch.getOutputA().hasPower());
		myClock.powerOff();
		myLatch.printStatus();
		//assertTrue(myLatch.getOutputA().hasPower());
	}

}
