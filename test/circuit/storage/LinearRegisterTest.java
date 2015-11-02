package circuit.storage;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import transistor.Connection;

public class LinearRegisterTest {
	private Connection[] myInputConnections;
	private Connection myWE;
	private LinearRegister myRegister;

	@Before
	public void setUp() throws Exception {
		myInputConnections = new Connection[8];
		myWE = new Connection();
		
		for(int i = 0; i < 8; i++) {
			myInputConnections[i] = new Connection();
		}
		
		myRegister = new LinearRegister(myInputConnections, myWE);
	}

	@Test
	public void quiescentStateTest() {
		for(int i = 0; i < 8; i++) {
			assertFalse(myRegister.getOutputConnections()[i].hasPower());
		}
	}
	
	@Test
	public void clearRegisterTest() {
		myWE.powerOn();
		myWE.powerOff();
		
		for(int i = 0; i < 8; i++) {
			assertFalse(myRegister.getOutputConnections()[i].hasPower());
		}
	}
	
	@Test
	public void storeValueOneTest() {
		myInputConnections[0].powerOn();
		
		for(int i = 0; i < 8; i++) {
			assertFalse(myRegister.getOutputConnections()[i].hasPower());
		}
		
		myWE.powerOn();
		myWE.powerOff();
		
		assertTrue(myRegister.getOutputConnections()[0].hasPower());
		for(int i = 1; i < 8; i++) {
			assertFalse(myRegister.getOutputConnections()[i].hasPower());
		}
	}
	
	@Test
	public void storeValueFiveTest() {
		myInputConnections[0].powerOn();
		myInputConnections[2].powerOn();
		
		myWE.powerOn();
		myWE.powerOff();
		
		myInputConnections[0].powerOff();
		myInputConnections[3].powerOn();
		
		for(int i = 0; i < 8; i++) {
			if(i == 0 || i == 2) {
				assertTrue(myRegister.getOutputConnections()[i].hasPower());
			} else {
				assertFalse(myRegister.getOutputConnections()[i].hasPower());
			}
		}
	}
	
	@Test
	public void toggleStateTest() {
		myWE.powerOn();
		myWE.powerOff();
		
		for(int i = 0; i < 8; i++) {
			assertFalse(myRegister.getOutputConnections()[i].hasPower());
		}
		
		myInputConnections[3].powerOn();
		myWE.powerOn();
		myWE.powerOff();
		myInputConnections[3].powerOff();
		
		assertTrue(myRegister.getOutputConnections()[3].hasPower());
		
		myWE.powerOn();
		myWE.powerOff();
		
		assertFalse(myRegister.getOutputConnections()[3].hasPower());
	}

}
