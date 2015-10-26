package circuit.storage;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import transistor.Connection;

public class FallingEdgeLatchTest {
	private Connection myInput;
	private Connection myWE;
	private FallingEdgeLatch myLatch;

	@Before
	public void setUp() throws Exception {
		myInput = new Connection();
		myWE = new Connection();
		myLatch = new FallingEdgeLatch(myInput, myWE);
	}

	@Test
	public void quiscentStateTest() {
		assertTrue(myLatch.getOutputA().hasPower());
		assertFalse(myLatch.getOutputB().hasPower());
	}
	
	@Test
	public void onlyChangeDataTest() {
		myInput.powerOn();
		
		assertTrue(myLatch.getOutputA().hasPower());
		assertFalse(myLatch.getOutputB().hasPower());
	}
	
	@Test
	public void changeDataAndWETest() {
		myInput.powerOff();
		myWE.powerOn();
		myWE.powerOff();
		
		assertFalse(myLatch.getOutputA().hasPower());
		assertTrue(myLatch.getOutputB().hasPower());
	}
	
	@Test
	public void switchStatesTest() {
		myInput.powerOff();
		myWE.powerOn();
		myWE.powerOff();
		
		assertFalse(myLatch.getOutputA().hasPower());
		assertTrue(myLatch.getOutputB().hasPower());
		
		myInput.powerOn();
		myWE.powerOn();
		myWE.powerOff();
		
		assertTrue(myLatch.getOutputA().hasPower());
		assertFalse(myLatch.getOutputB().hasPower());
	}
	
	@Test
	public void onlyWriteOnFallingEdgeTest() {
		myInput.powerOff();
		myWE.powerOn();
		myWE.powerOff();
		assertFalse(myLatch.getOutputA().hasPower());
		assertTrue(myLatch.getOutputB().hasPower());
		
		myInput.powerOn();
		assertFalse(myLatch.getOutputA().hasPower());
		assertTrue(myLatch.getOutputB().hasPower());
		
		myWE.powerOn();
		assertFalse(myLatch.getOutputA().hasPower());
		assertTrue(myLatch.getOutputB().hasPower());
		
		myWE.powerOff();
		assertTrue(myLatch.getOutputA().hasPower());
		assertFalse(myLatch.getOutputB().hasPower());		
	}

}
