package gate;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import transistor.Connection;

public class OrGateTest {
	private Connection myInputConnectionA;
	private Connection myInputConnectionB;
	private Connection[] myInputs;
	private OrGate myTestGate;

	@Before
	public void setUp() throws Exception {
		myInputConnectionA = new Connection();
		myInputConnectionB = new Connection();
		 
		myInputs = new Connection[2];
		myInputs[0] = myInputConnectionA;
		myInputs[1] = myInputConnectionB;
		 
		myTestGate = new OrGate(myInputs);
	}

	@Test
	public void bothOutputsOnTest() {
		myInputConnectionA.powerOn();
		myInputConnectionB.powerOn();
		assertTrue(myTestGate.getOutput().hasPower());
	}
	
	@Test
	public void bothOutputsOffTest() {
		myInputConnectionA.powerOff();
		myInputConnectionB.powerOff();
		assertFalse(myTestGate.getOutput().hasPower());
	}
	
	@Test
	public void oneOutputOnTest() {
		myInputConnectionA.powerOn();
		myInputConnectionB.powerOff();
		assertTrue(myTestGate.getOutput().hasPower());
		
		myInputConnectionA.powerOff();
		myInputConnectionB.powerOn();
		assertTrue(myTestGate.getOutput().hasPower());
	}

}
