package circuit.storage;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import transistor.Connection;

public class RSLatchTest {
	Connection myInputS, myInputR;
	RSLatch myLatch;

	@Before
	public void setUp() throws Exception {
		myInputS = new Connection();
		myInputR = new Connection();
		
		myInputS.powerOn();
		myInputR.powerOn();
		myLatch = new RSLatch(myInputS, myInputR);
	}

	@Test
	public void quiescentStateTest() {
		assertFalse(myLatch.getOutputA().hasPower());
		assertTrue(myLatch.getOutputB().hasPower());
	}
	
	@Test
	public void clearInputRTest() {
		myInputR.powerOff();
		assertFalse(myLatch.getOutputA().hasPower());
		assertTrue(myLatch.getOutputB().hasPower());	
		
		myInputR.powerOn();
		assertFalse(myLatch.getOutputA().hasPower());
		assertTrue(myLatch.getOutputB().hasPower());
		
		myInputR.powerOff();
		assertFalse(myLatch.getOutputA().hasPower());
		assertTrue(myLatch.getOutputB().hasPower());
	}
	
	@Test
	public void clearInputSTest() {
		myInputS.powerOff();
		assertTrue(myLatch.getOutputA().hasPower());
		assertFalse(myLatch.getOutputB().hasPower());
		
		myInputS.powerOn();
		assertTrue(myLatch.getOutputA().hasPower());
		assertFalse(myLatch.getOutputB().hasPower());
		
		myInputS.powerOff();
		assertTrue(myLatch.getOutputA().hasPower());
		assertFalse(myLatch.getOutputB().hasPower());
	}

}
