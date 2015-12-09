package main;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import circuit.storage.MemoryArray;

import transistor.Connection;

public class MemoryLoaderTest {
	private Connection[] myInputs;
	private Connection[] myDecoderInputs;
	private Connection[] myOutputs;
	private Connection myWE;
	private MemoryArray myMemory;
	private MemoryLoader myLoader;

	@Before
	public void setUp() throws Exception {
		myInputs = new Connection[8];
		myDecoderInputs = new Connection[3];
		myWE = new Connection();
		
		for(int i = 0; i < 8; i++) {
			myInputs[i] = new Connection();
		}
		for(int i = 0; i < 3; i++) {
			myDecoderInputs[i] = new Connection();
		}
		
		myMemory = new MemoryArray(myInputs, myDecoderInputs, myWE);
		myOutputs = myMemory.getOutputConnections();
		
		myLoader = new MemoryLoader(myInputs, myDecoderInputs,
				myWE, "memory-load-test1.txt");
	}

	@Test
	public void basicLoadTest() {
		myLoader.loadMemory();
		
		//Decoder set to 000
		for(int i = 0; i < 7; i++) {
			assertFalse(myOutputs[i].hasPower());
		}
		assertTrue(myOutputs[7].hasPower());
		
		//Decoder set to 001
		myDecoderInputs[0].powerOn();		
		assertFalse(myOutputs[0].hasPower());
		assertFalse(myOutputs[1].hasPower());
		assertFalse(myOutputs[2].hasPower());
		assertFalse(myOutputs[3].hasPower());
		assertTrue(myOutputs[4].hasPower());
		assertFalse(myOutputs[5].hasPower());
		assertFalse(myOutputs[6].hasPower());
		assertTrue(myOutputs[7].hasPower());
		
		//Decoder set to 010
		myDecoderInputs[0].powerOff();
		myDecoderInputs[1].powerOn();
		assertFalse(myOutputs[0].hasPower());
		assertTrue(myOutputs[1].hasPower());
		assertFalse(myOutputs[2].hasPower());
		assertTrue(myOutputs[3].hasPower());
		assertFalse(myOutputs[4].hasPower());
		assertTrue(myOutputs[5].hasPower());
		assertFalse(myOutputs[6].hasPower());
		assertTrue(myOutputs[7].hasPower());
		
		//Decoder set to 111
		myDecoderInputs[0].powerOn();
		myDecoderInputs[2].powerOn();
		assertTrue(myOutputs[7].hasPower());
		assertTrue(myOutputs[7].hasPower());
		assertTrue(myOutputs[7].hasPower());
		assertFalse(myOutputs[4].hasPower());
		assertFalse(myOutputs[4].hasPower());
		assertFalse(myOutputs[4].hasPower());
		assertFalse(myOutputs[4].hasPower());
		assertTrue(myOutputs[7].hasPower());
	}

}
