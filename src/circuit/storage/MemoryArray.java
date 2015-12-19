package circuit.storage;

import gate.AndGate;
import gate.OrGate;
import circuit.combinational.Decoder;
import transistor.Connection;

/**
 * A Memory Array is an (often) large collection of registers.<br>
 * Set up to only read from or write to one register at a time.<br>
 * Use RegisterFile to read from two registers at once.
 *
 * @author Taylor Gorman
 * @version 1.0, Dec 19, 2015
 */
public class MemoryArray {
	private Connection myWE;
	private Decoder myAddressDecoder;
	
	private Connection[] myInputs;
	private Register[] myRegisters;
	
	private AndGate[] myWriteGates;
	private AndGate[][] myRegisterReadGates;
	private OrGate[] myOutputGates;
	
	private Connection[] myOutputConnections;
	
	public final int numRegisters;
	public final int registerSize;
	
	/*
	 * TODO
	 * Take some time to rewrite this class to where is uses the new Multiplexer object.
	 */	
	
	/**
	 * Construct an array of memory.<br>
	 * The size of the memory is determined by the parameters passed to it.<br>
	 * The number of registers in the memory is equal to the square of decoder lines passed.<br>
	 * The size of each register in the memory is equal to the number of input connections passed.
	 * @param theInputConnections the number of input connections
	 * @param theDecoderConnections the lines from a decoder to switch registers
	 * @param theWE the write-enable line, which can be powered on to write the inputs to the current register
	 */
	public MemoryArray(Connection[] theInputConnections,
			Connection[] theDecoderConnections, Connection theWE) {
		
		myInputs = theInputConnections;
		myWE = theWE;
		
		registerSize = theInputConnections.length;
		numRegisters = (int) Math.pow(2, theDecoderConnections.length); 
		
		myAddressDecoder = new Decoder(theDecoderConnections);
		combineDecoderAndWE();
		setupRegisters();
		setupRegisterOutput();
	}
	
	/*
	 * Only when the current register is selected, and WE is enabled, should contents be written.
	 * Thus, each register has an AND Gate, where its decoder line and the WE line are joined.
	 * The output of these Gates goes into the WE line of the register.
	 */
	private void combineDecoderAndWE() {
		myWriteGates = new AndGate[numRegisters];
		
		for(int i = 0; i < numRegisters; i++) {
			Connection[] andGateConnection = {myAddressDecoder.getOutputConnections()[i], myWE};
			myWriteGates[i] = new AndGate(andGateConnection);
		}
	}
	
	/*
	 * Create each register, which is connected to the inputs and the WE line.
	 */
	private void setupRegisters() {
		myRegisters = new Register[numRegisters];
		
		for(int i = 0; i < numRegisters; i++) {
			myRegisters[i] = new Register(myInputs, myWriteGates[i].getOutput());
		}
	}
	
	/*
	 * Each Register Latch has an AND Gate that its output and a decoder line links to.
	 * This allows the register's contents to be read whenever its decoder line is asserted.
	 * 
	 * Each column of these AND Gates are then fed into an OR Gate, so that we have a number of
	 * outputs equal to the number of latches in each register. Thus, this allows us to assert a decoder
	 * line, and read the contents of one register.
	 */
	private void setupRegisterOutput() {
		myRegisterReadGates = new AndGate[numRegisters][registerSize];
		myOutputGates = new OrGate[registerSize];
		myOutputConnections = new Connection[registerSize];
		
		/*
		 * For each latch of each register, create an AND Gate that links the decoder line for that
		 * register and the output for that latch.
		 */
		for(int i = 0; i < numRegisters; i++) {
			for(int j = 0; j < registerSize; j++) {
				Connection[] andGateInputs = {myAddressDecoder.getOutputConnections()[i], myRegisters[i].getOutputConnections()[j]};
				myRegisterReadGates[i][j] = new AndGate(andGateInputs);
			}
		}
		
		/*
		 * For each column of latches, we will be connecting them to an OR Gate.
		 * For each latch in the column, connect the output of its AND Gate to the OR Gate.
		 * Then, set the output connections of the memory to be the OR Gate outputs.
		 */
		for(int registerColumn = 0; registerColumn < registerSize; registerColumn++) {
			Connection[] orGateInputs = new Connection[numRegisters];
			
			for(int latchNum = 0; latchNum < numRegisters; latchNum++) {
				orGateInputs[latchNum] = myRegisterReadGates[latchNum][registerColumn].getOutput();
			}
			
			myOutputGates[registerColumn] = new OrGate(orGateInputs);
			myOutputConnections[registerColumn] = myOutputGates[registerColumn].getOutput();
		}
		
		
	}
	
	/**
	 * Return the output connections of the Memory Array.<br>
	 * By asserting a specific decoder line, the contents of a single register
	 * will be available here.
	 * @return the output connections
	 */
	public Connection[] getOutputConnections() {
		return myOutputConnections;
	}
}
