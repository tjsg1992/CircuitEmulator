package transistor;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import transistor.Connection;

public class ConnectionTest {
	
	private Connection myConnection;

	@Before
	public void setUp() throws Exception {
		myConnection = new Connection();
	}

	@Test
	public void newConnectionIsOffTest() {
		assertFalse(myConnection.hasPower());
	}
	
	@Test
	public void powerOnNewConnectionTest() {
		myConnection.powerOn();
		assertTrue(myConnection.hasPower());
	}
	
	@Test
	public void powerOffOnConnectionTest() {
		myConnection.powerOn();
		myConnection.powerOff();
		assertFalse(myConnection.hasPower());
	}
	
	@Test
	public void powerOffOffConnectionTest() {
		myConnection.powerOff();
		assertFalse(myConnection.hasPower());
	}
	
	@Test
	public void powerOnOnConnectionTest() {
		myConnection.powerOn();
		myConnection.powerOn();
		assertTrue(myConnection.hasPower());
	}
	
	@Test
	public void powerSwitchMultipleTimesTest() {
		myConnection.powerOn();
		myConnection.powerOff();
		myConnection.powerOn();
		assertTrue(myConnection.hasPower());
	}

}
