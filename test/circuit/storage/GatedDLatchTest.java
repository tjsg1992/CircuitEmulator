package circuit.storage;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import transistor.Connection;

public class GatedDLatchTest {
	private Connection myConnectionD;
	private Connection myConnectionWE;
	private GatedDLatch myLatch;

	@Before
	public void setUp() throws Exception {
		myConnectionD = new Connection();
		myConnectionWE = new Connection();
		
		myLatch = new GatedDLatch(myConnectionD, myConnectionWE);
	}

	@Test
	public void quiescentStateTest() {
		assertTrue(myLatch.getOutputA().hasPower());
		assertFalse(myLatch.getOutputB().hasPower());
	}
	
	@Test
	public void writesOnlyWhenEnabledTest() {
		myConnectionD.powerOn();
		
		assertTrue(myLatch.getOutputA().hasPower());
		assertFalse(myLatch.getOutputB().hasPower());
		
		myConnectionD.powerOff();
		
		assertTrue(myLatch.getOutputA().hasPower());
		assertFalse(myLatch.getOutputB().hasPower());
	}
	
	@Test
	public void WriteWhenDIsOff() {
		myConnectionD.powerOff();
		myConnectionWE.powerOn();
		myConnectionWE.powerOff();
		
		assertFalse(myLatch.getOutputA().hasPower());
		assertTrue(myLatch.getOutputB().hasPower());
	}
	
	@Test
	public void writeWhenDIsOn() {
		myConnectionD.powerOn();
		myConnectionWE.powerOn();
		myConnectionWE.powerOff();
		
		assertTrue(myLatch.getOutputA().hasPower());
		assertFalse(myLatch.getOutputB().hasPower());
	}
	
	@Test
	public void toggleDTest() {
		myConnectionD.powerOff();
		myConnectionWE.powerOn();
		myConnectionWE.powerOff();
		
		assertFalse(myLatch.getOutputA().hasPower());
		assertTrue(myLatch.getOutputB().hasPower());
		
		myConnectionD.powerOn();
		myConnectionWE.powerOn();
		myConnectionWE.powerOff();
		
		assertTrue(myLatch.getOutputA().hasPower());
		assertFalse(myLatch.getOutputB().hasPower());
		
		myConnectionD.powerOff();
		myConnectionWE.powerOn();
		myConnectionWE.powerOff();
		
		assertFalse(myLatch.getOutputA().hasPower());
		assertTrue(myLatch.getOutputB().hasPower());
	}


}
