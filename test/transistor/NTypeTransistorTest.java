package transistor;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class NTypeTransistorTest {
	
	private Connection myInputConnection;
	private NTypeTransistor myTestTransistor;

	@Before
	public void setUp() throws Exception {
		myInputConnection = new Connection();
		myInputConnection.powerOn();
		
		myTestTransistor = new NTypeTransistor(new Source(), myInputConnection);
		myInputConnection.addOutputTransistor(myTestTransistor);
	}

	@Test
	public void outputWhenInputIsOnTest() {
		assertTrue(myTestTransistor.getOutput().hasPower());
	}
	
	@Test
	public void outputWhenInputIsOffTest() {
		myInputConnection.powerOff();
		assertFalse(myTestTransistor.getOutput().hasPower());
	}
	
	@Test
	public void outputRemainsConsistentTest() {
		myInputConnection.powerOn();
		myInputConnection.powerOff();
		myInputConnection.powerOn();
		assertTrue(myTestTransistor.getOutput().hasPower());
		myInputConnection.powerOff();
		assertFalse(myTestTransistor.getOutput().hasPower());
	}
	
	
	
	

}
