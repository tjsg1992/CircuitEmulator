package circuit.storage;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import transistor.Connection;

public class GatedRegisterTest {
	private Connection[] inputConnections;
	private Connection clock;
	private GatedRegister register;

	@Before
	public void setUp() throws Exception {
		inputConnections = new Connection[4];
		clock = new Connection();
		
		for(int i = 0; i < 4; i++) {
			inputConnections[i] = new Connection();
		}
		
		register = new GatedRegister(inputConnections, clock);
	}

	@Test
	public void defaultStateTest() {
		for(int i = 0; i < 4; i++) {
			assertFalse(register.getOutputConnections()[i].hasPower());
		}
	}
	
	@Test
	public void setAndCheckTest() {
		inputConnections[0].powerOn();
		inputConnections[2].powerOn();
		
		for(int i = 0; i < 4; i++) {
			assertFalse(register.getOutputConnections()[i].hasPower());
		}
	}
	
	@Test
	public void clockOnAndSetTest() {
		clock.powerOn();
		inputConnections[0].powerOn();
		inputConnections[2].powerOn();
		
		for(int i = 0; i < 4; i++) {
			assertFalse(register.getOutputConnections()[i].hasPower());
		}
	}
	
	@Test
	public void setAndClockOnTest() throws InterruptedException {
		inputConnections[0].powerOn();
		inputConnections[2].powerOn();
		Thread.sleep(2);
		clock.powerOn();
		Thread.sleep(2);
		
		assertTrue(register.getOutputConnections()[0].hasPower());
		assertFalse(register.getOutputConnections()[1].hasPower());
		assertTrue(register.getOutputConnections()[2].hasPower());
		assertFalse(register.getOutputConnections()[3].hasPower());
	}

}
