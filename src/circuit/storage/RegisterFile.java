package circuit.storage;

import gate.AndGate;
import circuit.combinational.Decoder;
import circuit.combinational.Multiplexer;
import transistor.Connection;
import transistor.Junction;

/**
 * A series of Registers similar to MemoryArray.<br>
 * Unlike MemoryArray, however, each register maintains its own write
 * line so that two registers can be written to at once.
 * 
 * @author Taylor Gorman
 * @version 1.0, 12/19/15
 *
 */
public class RegisterFile {
	//Inputs
	private Connection[] myDRSelects, mySR1Selects, mySR2Selects;
	private Connection myREGLoad;
	private int myMemoryWidth;
	
	private int myNumRegisters;
	private Register[] myRegisters;
	
	//Returnable outputs
	private Connection[] mySR1Outputs;
	private Connection[] mySR2Outputs;
	private Junction[] myRegisterInputs;
	
	public RegisterFile(Connection[] theDRSelects, Connection[] theSR1Selects,
			Connection[] theSR2Selects, Connection theREGLoad, int theMemoryWidth) {		
		
		if(theDRSelects == null || theSR1Selects == null || theSR2Selects == null || theREGLoad == null) {
			throw new IllegalArgumentException("Register File received null argument.");
		}		
		if(theDRSelects.length != theSR1Selects.length || theDRSelects.length != theSR2Selects.length) {
			throw new IllegalArgumentException("Register File received selection lines of different lenghts.");
		}
		if(theMemoryWidth <= 0) {
			throw new IllegalArgumentException("Register File received zero or negative memory width.");
		}
		
		//Set inputs
		this.myDRSelects = theDRSelects;
		this.mySR1Selects = theSR1Selects;
		this.mySR2Selects = theSR2Selects;
		this.myREGLoad = theREGLoad;
		this.myMemoryWidth = theMemoryWidth;		
		myNumRegisters = (int) Math.pow(2, myDRSelects.length);
		
		//Declare array of Registers.
		myRegisters = new Register[myNumRegisters];
		
		//Every Register has the same input connections, with intermediary junction inputs.
		Connection[] inputConnections = new Connection[myMemoryWidth];
		myRegisterInputs = new Junction[myMemoryWidth];
		
		//Each Register has a different write line.
		Connection[] regWrites = new Connection[myNumRegisters];
		
		//For each column, create its input line and connect it to its junction input.
		for(int i = 0; i < myMemoryWidth; i++) {
			myRegisterInputs[i] = new Junction(new Connection());
			inputConnections[i] = myRegisterInputs[i].getOutput();
		}
		
		//Destination Decoder determines which write line to assert.
		Decoder destDecoder = new Decoder(myDRSelects);
		
		
		/*
		 * Each write line is only powered when the destination decoder asserts it and
		 * REG Load is active.
		 */
		for(int i = 0; i < myNumRegisters; i++) {
			AndGate weGate = new AndGate(destDecoder.getOutputConnections()[i], myREGLoad);
			regWrites[i] = weGate.getOutput();
			myRegisters[i] = new Register(inputConnections, regWrites[i]);
		}

		
		/*
		 * For each register column, create a multiplexer that assert a register's memory
		 * at that column.
		 * Then, group those multiplexers' outputs together.
		 * Repeat the process. Now, the user can assert SR1 and SR2 lines to choose a particular
		 * Register, and then view that Register's current outputs.
		 */
		
		//Group the columns together.
		Connection[][] registerColumns = new Connection[myMemoryWidth][myNumRegisters];		
		for(int i = 0; i < myMemoryWidth; i++) {
			for(int j = 0; j < myNumRegisters; j++) {
				registerColumns[i][j] = myRegisters[j].getOutputConnections()[i];
			}
		}
		
		Multiplexer[] sr1Muxes = new Multiplexer[myMemoryWidth];
		Multiplexer[] sr2Muxes = new Multiplexer[myMemoryWidth];
		
		mySR1Outputs = new Connection[myMemoryWidth];
		mySR2Outputs = new Connection[myMemoryWidth];
		
		for(int i = 0; i < myMemoryWidth; i++) {
			sr1Muxes[i] = new Multiplexer(registerColumns[i], mySR1Selects);
			sr2Muxes[i] = new Multiplexer(registerColumns[i], mySR2Selects);
			
			mySR1Outputs[i] = sr1Muxes[i].getOutput();
			mySR2Outputs[i] = sr2Muxes[i].getOutput();
		}
		
		//TESTING
		inputConnections[0].powerOn();
		regWrites[0].powerOn();
		regWrites[0].powerOff();	
		inputConnections[0].powerOff();
	}
	
	/**
	 * Return the current contents of the selected SR1 Register.
	 * @return SR1 output
	 */
	public Connection[] getSR1Outputs() {
		return mySR1Outputs;
	}
	
	/**
	 * Return the current contents of the selected SR2 Register.
	 * @return SR2 output
	 */
	public Connection[] getSR2Outputs() {
		return mySR2Outputs;
	}
	
	/**
	 * Return the input junctions of the Register File.
	 * @return the input junctions
	 */
	public Junction[] getRegisterInputs() {
		return myRegisterInputs;
	}
	
	
	/**
	 * TEST METHOD
	 */
	public Connection[] getOutputConnections() {
		return myRegisters[2].getOutputConnections();
	}
}
