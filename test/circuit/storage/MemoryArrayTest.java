package circuit.storage;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import transistor.Connection;

public class MemoryArrayTest {
	private Connection[] myInputs;
	private Connection[] myDecoderLines;
	private Connection myWE;
	
	private MemoryArray myMemory;

	@Before
	public void setUp() throws Exception {
		myInputs = new Connection[3];
		myDecoderLines = new Connection[2];
		myWE = new Connection();
		
		for(int i = 0; i < 3; i++){
			myInputs[i] = new Connection();
		}
		
		for(int i = 0; i < 2; i++){
			myDecoderLines[i] = new Connection();
		}
		
		myMemory = new MemoryArray(myInputs, myDecoderLines, myWE);
		clearArray();
	}

	@Test
	public void storeOneRegisterTest() {
		myInputs[0].powerOn();
		myInputs[2].powerOn();
		myWE.powerOn();
		myWE.powerOff();
		myInputs[0].powerOff();
		myInputs[2].powerOff();
		
		assertTrue(myMemory.getOutputConnections()[0].hasPower());
		assertFalse(myMemory.getOutputConnections()[1].hasPower());	
		assertTrue(myMemory.getOutputConnections()[2].hasPower());	
	}
	
	
	@Test
	public void storeThenSwitchTest() {
		myInputs[0].powerOn();
		myInputs[2].powerOn();
		myWE.powerOn();
		myWE.powerOff();
		
		myDecoderLines[0].powerOn();
		
		assertFalse(myMemory.getOutputConnections()[0].hasPower());
		assertFalse(myMemory.getOutputConnections()[1].hasPower());	
		assertFalse(myMemory.getOutputConnections()[2].hasPower());	
	}
	
	@Test
	public void storeMultipleRegisters() {
		myInputs[0].powerOn();
		myInputs[1].powerOn();
		myWE.powerOn();
		myWE.powerOff();
		
		assertTrue(myMemory.getOutputConnections()[0].hasPower());
		assertTrue(myMemory.getOutputConnections()[1].hasPower());	
		assertFalse(myMemory.getOutputConnections()[2].hasPower());	
		
		myDecoderLines[0].powerOn();
		myDecoderLines[1].powerOn();
		
		myInputs[0].powerOff();
		myInputs[2].powerOn();
		myWE.powerOn();
		myWE.powerOff();
		
		assertFalse(myMemory.getOutputConnections()[0].hasPower());
		assertTrue(myMemory.getOutputConnections()[1].hasPower());	
		assertTrue(myMemory.getOutputConnections()[2].hasPower());	
		
		myDecoderLines[0].powerOff();
		myDecoderLines[1].powerOff();
		
		assertTrue(myMemory.getOutputConnections()[0].hasPower());
		assertTrue(myMemory.getOutputConnections()[1].hasPower());	
		assertFalse(myMemory.getOutputConnections()[2].hasPower());	
		
		myDecoderLines[0].powerOn();
		myDecoderLines[1].powerOn();
		
		assertFalse(myMemory.getOutputConnections()[0].hasPower());
		assertTrue(myMemory.getOutputConnections()[1].hasPower());	
		assertTrue(myMemory.getOutputConnections()[2].hasPower());	
	}
	
	
	private void clearArray() {
		myWE.powerOn();
		myWE.powerOff();
		
		myDecoderLines[0].powerOn();
		myWE.powerOn();
		myWE.powerOff();
		
		myDecoderLines[0].powerOff();
		myDecoderLines[1].powerOn();
		myWE.powerOn();
		myWE.powerOff();
		
		myDecoderLines[0].powerOn();
		myWE.powerOn();
		myWE.powerOff();
		
		myDecoderLines[0].powerOff();
		myDecoderLines[1].powerOff();
	}

}
