package circuit.storage;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import transistor.Connection;

public class EdgeLatchTest {
	private Connection myInput;
	private Connection myWE;
	private EdgeLatch risingLatch;
	private EdgeLatch fallingLatch;

	@Before
	public void setUp() throws Exception {
		myInput = new Connection();
		myWE = new Connection();
		risingLatch = new EdgeLatch(myInput, myWE, true);
		fallingLatch = new EdgeLatch(myInput, myWE, false);
	}

	@Test
	public void quiscentStateTest() {
		assertFalse(risingLatch.getOutputA().hasPower());
		assertTrue(risingLatch.getOutputB().hasPower());
	}
	
	@Test
	public void onlyChangeDataTest() {
		myInput.powerOn();
		
		assertFalse(risingLatch.getOutputA().hasPower());
		assertTrue(risingLatch.getOutputB().hasPower());
	}
	
	@Test
	public void changeDataAndWETest() {
		myInput.powerOff();
		myWE.powerOn();
		myWE.powerOff();
		
		assertFalse(risingLatch.getOutputA().hasPower());
		assertTrue(risingLatch.getOutputB().hasPower());
	}
	
	@Test
	public void switchStatesTest() {
		myInput.powerOff();
		myWE.powerOn();
		myWE.powerOff();
		
		assertFalse(risingLatch.getOutputA().hasPower());
		assertTrue(risingLatch.getOutputB().hasPower());
		
		myInput.powerOn();
		myWE.powerOn();
		myWE.powerOff();
		
		assertTrue(risingLatch.getOutputA().hasPower());
		assertFalse(risingLatch.getOutputB().hasPower());
	}
	
	@Test
	public void fallingEdgeTest() {	
		
		assertFalse(fallingLatch.getOutputA().hasPower());
		assertTrue(risingLatch.getOutputB().hasPower());
		myWE.powerOn();
		assertFalse(fallingLatch.getOutputA().hasPower());
		assertTrue(risingLatch.getOutputB().hasPower());
		myInput.powerOn();
		assertFalse(fallingLatch.getOutputA().hasPower());
		assertTrue(risingLatch.getOutputB().hasPower());
		myWE.powerOff();
		assertTrue(fallingLatch.getOutputA().hasPower());
		assertFalse(fallingLatch.getOutputB().hasPower());
		myInput.powerOff();
		assertTrue(fallingLatch.getOutputA().hasPower());
		assertFalse(fallingLatch.getOutputB().hasPower());
		myInput.powerOn();
		assertTrue(fallingLatch.getOutputA().hasPower());
		assertFalse(fallingLatch.getOutputB().hasPower());
		myInput.powerOff();
		myWE.powerOn();
		assertTrue(fallingLatch.getOutputA().hasPower());
		assertFalse(fallingLatch.getOutputB().hasPower());
		myWE.powerOff();
		assertFalse(fallingLatch.getOutputA().hasPower());
		assertTrue(risingLatch.getOutputB().hasPower());
	}

}
