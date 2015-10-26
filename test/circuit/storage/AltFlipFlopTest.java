package circuit.storage;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import transistor.Connection;

public class AltFlipFlopTest {
	private Connection myToggleLine;
	private Connection myClockLine;
	private AltFlipFlop myLatch;
	
	@Before
	public void setup() {
		myToggleLine = new Connection();
		myClockLine = new Connection();
		myLatch = new AltFlipFlop(myToggleLine, myClockLine);
	}

	@Test
	public void test() {
		assertFalse(myLatch.getOutputA().hasPower());
		myToggleLine.powerOn();
		myClockLine.powerOn();
		assertFalse(myLatch.getOutputA().hasPower());
		myClockLine.powerOff();
		assertTrue(myLatch.getOutputA().hasPower());
	}

}
