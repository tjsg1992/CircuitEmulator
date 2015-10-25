package circuit.combinational;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import transistor.Connection;

public class MultiplexerTest {
	Connection[] myInputLines;
	Connection[] mySelectionLines;
	
	Multiplexer myMux;

	@Before
	public void setUp() throws Exception {
		myInputLines = new Connection[8];
		mySelectionLines = new Connection[3];
		
		for(int i = 0; i < 3; i++) {
			mySelectionLines[i] = new Connection();
		}
		
		for(int i = 0; i < 8; i++) {
			myInputLines[i] = new Connection();
		}
		
		myMux = new Multiplexer(myInputLines, mySelectionLines);
	}

	@Test
	public void allInputsOffTest() {
		assertFalse(myMux.getOutput().hasPower());
	}
	
	@Test
	public void setOne() {
		
		myInputLines[0].powerOn();
		assertTrue(myMux.getOutput().hasPower());		
		
		myInputLines[1].powerOn();
		assertTrue(myMux.getOutput().hasPower());
	}
	
	@Test
	public void allSelectionLinesTest() {
		myInputLines[0].powerOn();

		assertTrue(myMux.getOutput().hasPower());
		
		
		myInputLines[0].powerOff();		
		myInputLines[1].powerOn();
		mySelectionLines[0].powerOn();
		assertTrue(myMux.getOutput().hasPower());
		
		myInputLines[1].powerOff();
		myInputLines[2].powerOn();
		assertFalse(myMux.getOutput().hasPower());
		mySelectionLines[0].powerOff();
		mySelectionLines[1].powerOn();
		assertTrue(myMux.getOutput().hasPower());
		
		myInputLines[2].powerOff();
		myInputLines[3].powerOn();
		mySelectionLines[0].powerOn();
		assertTrue(myMux.getOutput().hasPower());
		
		myInputLines[3].powerOff();
		myInputLines[4].powerOn();
		mySelectionLines[0].powerOff();
		mySelectionLines[1].powerOff();
		mySelectionLines[2].powerOn();
		assertTrue(myMux.getOutput().hasPower());
		
		myInputLines[4].powerOff();
		myInputLines[5].powerOn();
		mySelectionLines[0].powerOn();
		assertTrue(myMux.getOutput().hasPower());
		
		myInputLines[5].powerOff();
		myInputLines[6].powerOn();
		mySelectionLines[0].powerOff();
		mySelectionLines[1].powerOn();
		assertTrue(myMux.getOutput().hasPower());
		
		myInputLines[6].powerOff();
		myInputLines[7].powerOn();
		mySelectionLines[0].powerOn();
		assertTrue(myMux.getOutput().hasPower());
	}

}
