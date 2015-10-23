package transistor;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class PTypeTransistorTest {
	
	private Connection myInputConnection;
	private PTypeTransistor myTestTransistor;

	@Before
	public void setUp() throws Exception {
		myInputConnection = new Connection();
		myInputConnection.powerOn();
		
		myTestTransistor = new PTypeTransistor(new Source(), myInputConnection);
		myInputConnection.addOutputTransistor(myTestTransistor);
	}

	@Test
	public void outputWhenInputIsOnTest() {
		assertFalse(myTestTransistor.getOutput().hasPower());
	}
	
	@Test
	public void outputWhenInputIsOffTest() {
		myInputConnection.powerOff();
		assertTrue(myTestTransistor.getOutput().hasPower());
	}
	
	@Test
	public void outputRemainsConsistentTest() {
		myInputConnection.powerOn();
		myInputConnection.powerOff();
		myInputConnection.powerOn();
		assertFalse(myTestTransistor.getOutput().hasPower());
		myInputConnection.powerOff();
		assertTrue(myTestTransistor.getOutput().hasPower());
	}
	
	
	
	

}
