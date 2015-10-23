package gate;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import transistor.Connection;

public class NandGateTest {
	private Connection myInputConnectionA;
	private Connection myInputConnectionB;
	private Connection myInputConnectionC;
	private Connection myInputConnectionD;
	
	private Connection[] myInputs;
	private NandGate myTestGate;
	private NandGate myTestGateManyInputs;
	
	@Before
	public void setUp() throws Exception {
		myInputConnectionA = new Connection();
		myInputConnectionB = new Connection();
		myInputConnectionC = new Connection();
		myInputConnectionD = new Connection();
		 
		myInputs = new Connection[2];
		myInputs[0] = myInputConnectionA;
		myInputs[1] = myInputConnectionB;
		 
		myTestGate = new NandGate(myInputs);
		 
		myInputs = new Connection[4];
		myInputs[0] = myInputConnectionA;
		myInputs[1] = myInputConnectionB;
		myInputs[2] = myInputConnectionC;
		myInputs[3] = myInputConnectionD;

		myTestGateManyInputs = new NandGate(myInputs);
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
	
	@Test
	public void manyInputAllOnTest() {
		myInputConnectionA.powerOn();
		myInputConnectionB.powerOn();
		myInputConnectionC.powerOn();
		myInputConnectionD.powerOn();
		
		assertFalse(myTestGateManyInputs.getOutput().hasPower());
	}
	
	@Test
	public void manyInputHalfOnTest() {
		myInputConnectionA.powerOn();
		myInputConnectionB.powerOn();
		myInputConnectionC.powerOff();
		myInputConnectionD.powerOff();
		
		assertTrue(myTestGateManyInputs.getOutput().hasPower());
	}
	
	@Test
	public void manyInputAllOffTest() {
		myInputConnectionA.powerOff();
		myInputConnectionB.powerOff();
		myInputConnectionC.powerOff();
		myInputConnectionD.powerOff();
		
		assertTrue(myTestGateManyInputs.getOutput().hasPower());
	}

}
