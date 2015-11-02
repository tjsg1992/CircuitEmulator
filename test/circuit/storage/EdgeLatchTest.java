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
		//fallingLatch = new EdgeLatch(myInput, myWE, true);
	}

//	@Test
//	public void quiscentStateTest() {
//		assertFalse(risingLatch.getOutputA().hasPower());
//		assertTrue(risingLatch.getOutputB().hasPower());
//	}
//	
//	@Test
//	public void onlyChangeDataTest() {
//		myInput.powerOn();
//		
//		assertFalse(risingLatch.getOutputA().hasPower());
//		assertTrue(risingLatch.getOutputB().hasPower());
//	}
//	
//	@Test
//	public void changeDataAndWETest() {
//		myInput.powerOff();
//		myWE.powerOn();
//		myWE.powerOff();
//		
//		assertFalse(risingLatch.getOutputA().hasPower());
//		assertTrue(risingLatch.getOutputB().hasPower());
//	}
	
	@Test
	public void switchStatesTest() {
		System.out.println("Starting Test");
		myInput.powerOn();
		myWE.powerOn();
		try {
			Thread.sleep(2);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertTrue(risingLatch.getOutputA().hasPower());
		assertFalse(risingLatch.getOutputB().hasPower());
	}
	
	@Test
	public void fallingEdgeTest() {	
		
		assertFalse(risingLatch.getOutputA().hasPower());
		assertTrue(risingLatch.getOutputB().hasPower());
		myInput.powerOn();
		
		myWE.powerOn();
		try {
			Thread.sleep(20);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertTrue(risingLatch.getOutputA().hasPower());
		assertFalse(risingLatch.getOutputB().hasPower());
		
		myInput.powerOff();
		assertTrue(risingLatch.getOutputA().hasPower());
		assertFalse(risingLatch.getOutputB().hasPower());
		
		myWE.powerOff();
		try {
			Thread.sleep(20);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertFalse(risingLatch.getOutputA().hasPower());
		assertTrue(risingLatch.getOutputB().hasPower());
		
		myInput.powerOn();
		try {
			Thread.sleep(20);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertFalse(risingLatch.getOutputA().hasPower());
		assertTrue(risingLatch.getOutputB().hasPower());
		
		
		myWE.powerOn();
		try {
			Thread.sleep(20);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertTrue(risingLatch.getOutputA().hasPower());
		assertFalse(risingLatch.getOutputB().hasPower());
	}

}
