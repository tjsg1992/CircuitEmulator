package gate;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import transistor.Connection;

public class NotGateTest {
	private Connection myInputConnection;
	private NotGate myTestGate;
	
	@Before
	public void setUp() throws Exception {
		 myInputConnection = new Connection();
		 myTestGate = new NotGate(myInputConnection);
	}

	@Test
	public void gateOutputWhenInputOnTest() {
		myInputConnection.powerOn();
		assertFalse(myTestGate.getOutput().hasPower());
	}
	
	@Test
	public void gateOutputWhenInputOffTest() {
		myInputConnection.powerOff();
		assertTrue(myTestGate.getOutput().hasPower());
	}
	
	@Test
	public void outputValidAfterTogglingTest() {
		myInputConnection.powerOn();
		myInputConnection.powerOff();
		
		myInputConnection.powerOn();
		assertFalse(myTestGate.getOutput().hasPower());
		
		myInputConnection.powerOff();
		assertTrue(myTestGate.getOutput().hasPower());
	}

}
