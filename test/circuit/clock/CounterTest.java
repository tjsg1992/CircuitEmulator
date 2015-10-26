package circuit.clock;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import transistor.Connection;

public class CounterTest {
	private Connection myClock;
	private Counter myCounter;

	@Before
	public void setUp() throws Exception {
		myClock = new Connection();
		myCounter = new Counter(myClock, 4);
	}

	@Test
	public void defaultStateTest() {
		assertFalse(myCounter.getOutputConnections()[0].hasPower());
		assertFalse(myCounter.getOutputConnections()[1].hasPower());
		assertFalse(myCounter.getOutputConnections()[2].hasPower());
		assertFalse(myCounter.getOutputConnections()[3].hasPower());
	}
	
	@Test
	public void clockOffTest() {
		myClock.powerOn();
		myClock.powerOff();
		assertTrue(myCounter.getOutputConnections()[0].hasPower());
		assertTrue(myCounter.getOutputConnections()[1].hasPower());
		assertTrue(myCounter.getOutputConnections()[2].hasPower());
		assertTrue(myCounter.getOutputConnections()[3].hasPower());
	}
	
//	@Test
//	public void toggleTest() {
//		myClock.powerOff();
//		assertTrue(myCounter.getOutputConnections()[0].hasPower());
//		assertFalse(myCounter.getOutputConnections()[1].hasPower());
//		assertFalse(myCounter.getOutputConnections()[2].hasPower());
//		assertFalse(myCounter.getOutputConnections()[3].hasPower());
//		
//		myClock.powerOn();
//		assertFalse(myCounter.getOutputConnections()[0].hasPower());
//		assertTrue(myCounter.getOutputConnections()[1].hasPower());
//		assertFalse(myCounter.getOutputConnections()[2].hasPower());
//		assertFalse(myCounter.getOutputConnections()[3].hasPower());
//		
//		myClock.powerOff();
//		assertTrue(myCounter.getOutputConnections()[0].hasPower());
//		assertTrue(myCounter.getOutputConnections()[1].hasPower());
//		assertFalse(myCounter.getOutputConnections()[2].hasPower());
//		assertFalse(myCounter.getOutputConnections()[3].hasPower());
//	}

}
