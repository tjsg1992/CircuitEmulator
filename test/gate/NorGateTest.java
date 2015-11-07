package gate;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import transistor.Connection;

public class NorGateTest {	
	private Connection myInputConnectionA;
	private Connection myInputConnectionB;
	private Connection myInputConnectionC;
	private Connection myInputConnectionD;
	
	private Connection[] myInputs;
	private NorGate myTestGate;
	private NorGate myTestGateManyInputs;

	@Before
	public void setUp() throws Exception {
		myInputConnectionA = new Connection();
		myInputConnectionB = new Connection();
		myInputConnectionC = new Connection();
		myInputConnectionD = new Connection();
		 
		myInputs = new Connection[2];
		myInputs[0] = myInputConnectionA;
		myInputs[1] = myInputConnectionB;
		 
		myTestGate = new NorGate(myInputs);
		
		myInputs = new Connection[4];
		myInputs[0] = myInputConnectionA;
		myInputs[1] = myInputConnectionB;
		myInputs[2] = myInputConnectionC;
		myInputs[3] = myInputConnectionD;
		
		myTestGateManyInputs = new NorGate(myInputs);
	}

//	@Test
//	public void bothOutputsOnTest() {
//		myInputConnectionA.powerOn();
//		myInputConnectionB.powerOn();
//		assertFalse(myTestGate.getOutput().hasPower());
//	}
//	
//	@Test
//	public void bothOutputsOffTest() {
//		myInputConnectionA.powerOff();
//		myInputConnectionB.powerOff();
//		assertTrue(myTestGate.getOutput().hasPower());
//	}
//	
	@Test
	public void oneOutputOnTest() {
		myInputConnectionA.powerOn();
		myInputConnectionB.powerOff();
		assertFalse(myTestGate.getOutput().hasPower());
		
		myInputConnectionA.powerOff();
		myInputConnectionB.powerOn();
		assertFalse(myTestGate.getOutput().hasPower());
	}
//	
//	@Test
//	public void manyInputAllOnTest() {
//		myInputConnectionA.powerOn();
//		myInputConnectionB.powerOn();
//		myInputConnectionC.powerOn();
//		myInputConnectionD.powerOn();
//		
//		assertFalse(myTestGateManyInputs.getOutput().hasPower());
//	}
//	
//	@Test
//	public void manyInputHalfOnTest() {
//		myInputConnectionA.powerOn();
//		myInputConnectionB.powerOn();
//		myInputConnectionC.powerOff();
//		myInputConnectionD.powerOff();
//		
//		assertFalse(myTestGateManyInputs.getOutput().hasPower());
//	}
//	
//	@Test
//	public void manyInputAllOffTest() {
//		myInputConnectionA.powerOff();
//		myInputConnectionB.powerOff();
//		myInputConnectionC.powerOff();
//		myInputConnectionD.powerOff();
//		
//		assertTrue(myTestGateManyInputs.getOutput().hasPower());
//	}
}
