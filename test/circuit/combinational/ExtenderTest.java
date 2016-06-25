package circuit.combinational;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import transistor.Connection;

public class ExtenderTest {
	Connection[] myInputLines;	
	Extender myExtender;

	@Before
	public void setUp() throws Exception {
		myInputLines = new Connection[5];
		
		for(int i = 0; i < 5; i++) {
			myInputLines[i] = new Connection();
		}
		
		//00000 --> 01010
		myInputLines[1].powerOn();
		myInputLines[3].powerOn();
		
		myExtender = new Extender(myInputLines, 16, false);
	}

	@Test
	public void positiveSignExtensionTest() {
		//0000000000001010
		assertFalse(myExtender.getOutputs()[0].hasPower());
		assertFalse(myExtender.getOutputs()[1].hasPower());
		assertFalse(myExtender.getOutputs()[10].hasPower());
		assertFalse(myExtender.getOutputs()[11].hasPower());
		assertTrue(myExtender.getOutputs()[12].hasPower());
		assertFalse(myExtender.getOutputs()[13].hasPower());
		assertTrue(myExtender.getOutputs()[14].hasPower());
		assertFalse(myExtender.getOutputs()[15].hasPower());
	}
	
	public void negativeSignExtensionTest() {
		//01010 --> 11010
		myInputLines[0].powerOn();
		myExtender = new Extender(myInputLines, 16, false);
		
		//1111111111111010
		assertTrue(myExtender.getOutputs()[0].hasPower());
		assertTrue(myExtender.getOutputs()[1].hasPower());
		assertTrue(myExtender.getOutputs()[10].hasPower());
		assertTrue(myExtender.getOutputs()[11].hasPower());
		assertTrue(myExtender.getOutputs()[12].hasPower());
		assertFalse(myExtender.getOutputs()[13].hasPower());
		assertTrue(myExtender.getOutputs()[14].hasPower());
		assertFalse(myExtender.getOutputs()[15].hasPower());
	}
	
	@Test
	public void zeroExtensionTest() {
		myExtender = new Extender(myInputLines, 16, true);
		
		//0000000000001010
		assertFalse(myExtender.getOutputs()[0].hasPower());
		assertFalse(myExtender.getOutputs()[1].hasPower());
		assertFalse(myExtender.getOutputs()[10].hasPower());
		assertFalse(myExtender.getOutputs()[11].hasPower());
		assertTrue(myExtender.getOutputs()[12].hasPower());
		assertFalse(myExtender.getOutputs()[13].hasPower());
		assertTrue(myExtender.getOutputs()[14].hasPower());
		assertFalse(myExtender.getOutputs()[15].hasPower());
		
		//01010 --> 11010
		myInputLines[0].powerOn();
		myExtender = new Extender(myInputLines, 16, true);
		
		//0000000000011010
		assertFalse(myExtender.getOutputs()[0].hasPower());
		assertFalse(myExtender.getOutputs()[1].hasPower());
		assertFalse(myExtender.getOutputs()[10].hasPower());
		assertTrue(myExtender.getOutputs()[11].hasPower());
		assertTrue(myExtender.getOutputs()[12].hasPower());
		assertFalse(myExtender.getOutputs()[13].hasPower());
		assertTrue(myExtender.getOutputs()[14].hasPower());
		assertFalse(myExtender.getOutputs()[15].hasPower());
	}

}
