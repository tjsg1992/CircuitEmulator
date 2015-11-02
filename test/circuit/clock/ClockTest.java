package circuit.clock;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class ClockTest {
	private Clock myClock;

	@Before
	public void setUp() throws Exception {
		myClock = new Clock();
		myClock.start();
	}

	@Test
	public void test() throws InterruptedException {
		assertFalse(myClock.getOutput().hasPower());
		
		Thread.sleep(1050);
		assertFalse(myClock.getOutput().hasPower());
		
		Thread.sleep(1050);
		assertFalse(myClock.getOutput().hasPower());
		
		Thread.sleep(1050);
		assertTrue(myClock.getOutput().hasPower());
		
		Thread.sleep(1050);
		assertTrue(myClock.getOutput().hasPower());
	}

}
