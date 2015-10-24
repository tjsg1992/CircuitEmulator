package circuit.combinational;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import transistor.Connection;

public class DecoderTest {
	private Connection connectionA;
	private Connection connectionB;
	private Connection[] myInputConnections;
	
	private Decoder myDecoder;
	private Connection[] myOutputConnections;

	@Before
	public void setUp() throws Exception {
		connectionA = new Connection();
		connectionB = new Connection();
		
		myInputConnections = new Connection[2];
		myInputConnections[0] = connectionA;
		myInputConnections[1] = connectionB;
		
		myDecoder = new Decoder(myInputConnections);
		myOutputConnections = myDecoder.getOutputConnections();
	}

	@Test
	public void bothConnectionsOffTest() {
		connectionA.powerOff();
		connectionB.powerOff();
		
		assertTrue(myOutputConnections[0].hasPower());
		assertFalse(myOutputConnections[1].hasPower());
		assertFalse(myOutputConnections[2].hasPower());
		assertFalse(myOutputConnections[3].hasPower());
	}
	
	@Test
	public void connectionAOffTest() {
		connectionA.powerOff();
		connectionB.powerOn();
		
		assertFalse(myOutputConnections[0].hasPower());
		assertTrue(myOutputConnections[1].hasPower());
		assertFalse(myOutputConnections[2].hasPower());
		assertFalse(myOutputConnections[3].hasPower());
	}
	
	@Test
	public void connectionBOffTest() {
		connectionA.powerOn();
		connectionB.powerOff();
		
		assertFalse(myOutputConnections[0].hasPower());
		assertFalse(myOutputConnections[1].hasPower());
		assertTrue(myOutputConnections[2].hasPower());
		assertFalse(myOutputConnections[3].hasPower());
	}
	
	@Test
	public void bothConnectionsOnTest() {
		connectionA.powerOn();
		connectionB.powerOn();
		
		assertFalse(myOutputConnections[0].hasPower());
		assertFalse(myOutputConnections[1].hasPower());
		assertFalse(myOutputConnections[2].hasPower());
		assertTrue(myOutputConnections[3].hasPower());
	}
	
	@Test
	public void connectionToggleTest() {
		connectionA.powerOn();
		connectionB.powerOn();
		connectionA.powerOff();
		connectionB.powerOff();
		connectionA.powerOn();
		connectionB.powerOn();		
		
		assertFalse(myOutputConnections[0].hasPower());
		assertFalse(myOutputConnections[1].hasPower());
		assertFalse(myOutputConnections[2].hasPower());
		assertTrue(myOutputConnections[3].hasPower());
		
		connectionA.powerOff();
		connectionB.powerOff();
		
		assertTrue(myOutputConnections[0].hasPower());
		assertFalse(myOutputConnections[1].hasPower());
		assertFalse(myOutputConnections[2].hasPower());
		assertFalse(myOutputConnections[3].hasPower());
		
		connectionA.powerOn();
		
		assertFalse(myOutputConnections[0].hasPower());
		assertFalse(myOutputConnections[1].hasPower());
		assertTrue(myOutputConnections[2].hasPower());
		assertFalse(myOutputConnections[3].hasPower());
	}

}
