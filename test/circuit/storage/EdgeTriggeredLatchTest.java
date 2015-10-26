package circuit.storage;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import transistor.Connection;

public class EdgeTriggeredLatchTest {
	
	private Connection myDataLine;
	private Connection myWELine;
	private EdgeTriggeredLatch myETLatch;

	@Before
	public void setUp() throws Exception {
		myDataLine = new Connection();
		myWELine = new Connection();
		myETLatch = new EdgeTriggeredLatch(myDataLine, myWELine);
	}

	@Test
	public void quiscentStateTest() {
		assertTrue(myETLatch.getOutputA().hasPower());
		assertFalse(myETLatch.getOutputB().hasPower());
	}
	
	@Test
	public void powerDataOnlyTest() {
		myDataLine.powerOn();
		assertTrue(myETLatch.getOutputA().hasPower());
		assertFalse(myETLatch.getOutputB().hasPower());
	}
	
	@Test
	public void powerDataAndWETest() {
		myETLatch.printStatus();
		myDataLine.powerOff();
		myETLatch.printStatus();
		myWELine.powerOn();
		myWELine.powerOff();
		myETLatch.printStatus();
		
		assertFalse(myETLatch.getOutputA().hasPower());
		assertTrue(myETLatch.getOutputB().hasPower());
	}
	
	@Test
	public void stateChangesOnlyOnEdgeTest() {
		myDataLine.powerOff();
		myWELine.powerOn();
		myWELine.powerOff();
		myDataLine.powerOn();
		
		assertFalse(myETLatch.getOutputA().hasPower());
		assertTrue(myETLatch.getOutputB().hasPower());	
		myWELine.powerOn();	
		assertFalse(myETLatch.getOutputA().hasPower());
		assertTrue(myETLatch.getOutputB().hasPower());	
		
		myWELine.powerOff();		
		assertTrue(myETLatch.getOutputA().hasPower());
		assertFalse(myETLatch.getOutputB().hasPower());
	}
	
	
	
	

}
