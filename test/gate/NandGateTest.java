package gate;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import transistor.Connection;

public class NandGateTest {
	private Connection myInputConnectionA;
	private Connection myInputConnectionB;
	private Connection[] myInputs;
	private NandGate myTestGate;
	
	@Before
	public void setUp() throws Exception {
		 myInputConnectionA = new Connection();
		 myInputConnectionB = new Connection();
		 
		 myInputs = new Connection[2];
		 myInputs[0] = myInputConnectionA;
		 myInputs[1] = myInputConnectionB;
		 
		 myTestGate = new NandGate(myInputs);
	}

	@Test
	public void bothOutputsOnTest() {
		myInputConnectionA.powerOn();
		myInputConnectionB.powerOn();
		assertFalse(myTestGate.getOutput().hasPower());
	}
	
	@Test
	public void bothOutputsOffTest() {
		myInputConnectionA.powerOff();
		myInputConnectionB.powerOff();
		assertTrue(myTestGate.getOutput().hasPower());
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
