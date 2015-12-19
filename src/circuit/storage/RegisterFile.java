package circuit.storage;

import gate.AndGate;
import circuit.combinational.Decoder;
import circuit.combinational.Multiplexer;
import transistor.Connection;
import transistor.Junction;

public class RegisterFile {
	private Connection[] myDRSelects, mySR1Selects, mySR2Selects;
	private Connection myREGLoad;
	private int myMemoryWidth;
	
	private int myNumRegisters;
	private Register[] myRegisters;
	
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
		
		this.myDRSelects = theDRSelects;
		this.mySR1Selects = theSR1Selects;
		this.mySR2Selects = theSR2Selects;
		this.myREGLoad = theREGLoad;
		this.myMemoryWidth = theMemoryWidth;
		
		
		myNumRegisters = (int) Math.pow(2, myDRSelects.length);
		
		myRegisters = new Register[myNumRegisters];
		myRegisterInputs = new Junction[myMemoryWidth];
		Connection[] inputConnections = new Connection[myMemoryWidth];
		Connection[] regWrites = new Connection[myNumRegisters];
		
		Decoder destDecoder = new Decoder(myDRSelects);
		
		for(int i = 0; i < myMemoryWidth; i++) {
			myRegisterInputs[i] = new Junction(new Connection());
			inputConnections[i] = myRegisterInputs[i].getOutput();
		}
		
		for(int i = 0; i < myNumRegisters; i++) {
			AndGate weGate = new AndGate(destDecoder.getOutputConnections()[i], myREGLoad);
			regWrites[i] = weGate.getOutput();
			myRegisters[i] = new Register(inputConnections, regWrites[i]);
		}

		Connection[][] sr1Groups = new Connection[myMemoryWidth][myNumRegisters];
		Connection[][] sr2Groups = new Connection[myMemoryWidth][myNumRegisters];
		Connection[][] drGroups = new Connection[myMemoryWidth][myNumRegisters];
		
		for(int i = 0; i < myMemoryWidth; i++) {
			for(int j = 0; j < myNumRegisters; j++) {
				sr1Groups[i][j] = myRegisters[j].getOutputConnections()[i];
				sr2Groups[i][j] = myRegisters[j].getOutputConnections()[i];
				drGroups[i][j] = myRegisters[j].getOutputConnections()[i];
			}
		}
		
		Multiplexer[] sr1Muxes = new Multiplexer[myMemoryWidth];
		Multiplexer[] sr2Muxes = new Multiplexer[myMemoryWidth];
		
		mySR1Outputs = new Connection[myMemoryWidth];
		mySR2Outputs = new Connection[myMemoryWidth];
		
		for(int i = 0; i < myMemoryWidth; i++) {
			sr1Muxes[i] = new Multiplexer(sr1Groups[i], mySR1Selects);
			sr2Muxes[i] = new Multiplexer(sr2Groups[i], mySR2Selects);
			
			mySR1Outputs[i] = sr1Muxes[i].getOutput();
			mySR2Outputs[i] = sr2Muxes[i].getOutput();
		}
		
		inputConnections[0].powerOn();
		regWrites[0].powerOn();
		regWrites[0].powerOff();	
		inputConnections[0].powerOff();
	}
	
	public Connection[] getSR1Outputs() {
		return mySR1Outputs;
	}
	
	public Connection[] getSR2Outputs() {
		return mySR2Outputs;
	}
	
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
