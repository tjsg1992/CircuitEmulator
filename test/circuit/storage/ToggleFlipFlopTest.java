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
		myClock.powerOn();
		myClock.powerOff();
		myClock.powerOn();
	}

}
