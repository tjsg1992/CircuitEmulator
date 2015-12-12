package processor.lc3;

import gate.AndGate;
import gate.NandGate;
import gate.NotGate;
import transistor.Connection;
import circuit.clock.Clock;
import circuit.clock.RippleCounter;
import circuit.combinational.Decoder;
import circuit.storage.Register;

public class FiniteStateMachine {
	private Clock myClock;
	private Connection[] myOutputs;
	
	private Connection[] mySR1Inputs;
	private Connection[] mySR2Inputs;
	private Connection[] myDRInputs;
	
	private RippleCounter haltCounter;
	private Decoder haltDecoder;
	
	public FiniteStateMachine() {
		myClock = new Clock();
		NotGate clockInverter = new NotGate(myClock.getOutput());
		
		RippleCounter myCounter = new RippleCounter(myClock.getOutput(), 4);
		Decoder myDecoder = new Decoder(myCounter.getOutputConnections());
		
		AndGate[] decoderBuffers = new AndGate[16];
		Connection[] bufferGroup = new Connection[16];
		for(int i = 0; i < 4; i++) {
			Connection[] andGateGroup = {clockInverter.getOutput(), myDecoder.getOutputConnections()[i]};
			decoderBuffers[i] = new AndGate(andGateGroup);
			bufferGroup[i] = decoderBuffers[i].getOutput();
		}
		
		myOutputs = bufferGroup;
		
		haltCounter = new RippleCounter(myOutputs[3], 4);
		haltDecoder = new Decoder(haltCounter.getOutputConnections());
		
		//myClock.setHaltLine(myOutputs[3]);
		//myClock.setHaltLine(haltDecoder.getOutputConnections()[4]);
	}
	
	public void setupInstructionHandler(Register instructionRegister) {
		Connection[] instructionOutputs = instructionRegister.getOutputConnections();
		
		Connection[] decoderInputs = new Connection[4];
		for(int i = 0; i < 4; i++) {
			decoderInputs[i] = instructionOutputs[i];
		}
		Decoder instructionDecoder = new Decoder(decoderInputs);
		
		Connection addConnection = instructionDecoder.getOutputConnections()[1];
		
		AndGate[] sr1Gates, sr2Gates, drGates;
		sr1Gates = new AndGate[3];
		sr2Gates = new AndGate[3];
		drGates = new AndGate[3];
		
		mySR1Inputs = new Connection[3];
		mySR2Inputs = new Connection[3];
		myDRInputs = new Connection[3];
		
		for(int i = 4, j = 0; i < 7; i++, j++) {
			drGates[j] = new AndGate(instructionOutputs[i], addConnection);
			myDRInputs[j] = drGates[j].getOutput();
		}
		for(int i = 7, j = 0; i < 10; i++, j++) {
			sr1Gates[j] = new AndGate(instructionOutputs[i], addConnection);
			mySR1Inputs[j] = sr1Gates[j].getOutput();
		}
		for(int i = 13, j = 0; i < 16; i++, j++) {
			sr2Gates[j] = new AndGate(instructionOutputs[i], addConnection);
			mySR2Inputs[j] = sr2Gates[j].getOutput();
		}
		
	}
	
	public Connection getPCLoad() {
		return myOutputs[0];
	}
	
	public Connection getMARLoad() {
		return myOutputs[1];
	}
	
	public Connection getMDRLoad() {
		return myOutputs[2];
	}
	
	public Connection getIRLoad() {
		return myOutputs[3];
	}
	
	public void start() {
		myClock.start();
	}
	
	public Connection[] getOutputs() {
		return myOutputs;
	}
	
	public Connection[] getSR1Inputs() {
		return mySR1Inputs;
	}
	
	public Connection[] getSR2Inputs() {
		return mySR2Inputs;
	}
	
	public Connection[] getDRInputs() {
		return myDRInputs;
	}
	
	
}
