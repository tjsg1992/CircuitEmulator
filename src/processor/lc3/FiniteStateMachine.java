package processor.lc3;

import gate.AndGate;
import gate.NandGate;
import gate.NotGate;
import gate.OrGate;
import transistor.Connection;
import circuit.clock.Clock;
import circuit.clock.RippleCounter;
import circuit.combinational.Decoder;
import circuit.storage.Register;

public class FiniteStateMachine {
	private static final int PC_LOAD = 0;
	private static final int MAR_LOAD = 1;
	private static final int MDR_LOAD = 2;
	private static final int IR_LOAD = 3;
	private static final int ALU_LOAD = 4;
	private Clock myClock;
	private Connection[] myOutputs;
	
	private Connection[] mySR1Selects;
	private Connection[] mySR2Selects;
	private Connection[] myDRSelects;
	
	private Connection myAddConnection;
	private Connection myRegLoadConnection;
	private Connection[] aluk;
	
	public FiniteStateMachine() {
		setClock(new Clock());
	}
	
	public void setClock(Clock theClock) {
		this.myClock = theClock;		
		RippleCounter myCounter = new RippleCounter(myClock.getOutput(), 6);
		Decoder myDecoder = new Decoder(myCounter.getOutputConnections());
		
		AndGate[] decoderBuffers = new AndGate[16];
		Connection[] bufferGroup = new Connection[16];
		NotGate clockInverter = new NotGate(myClock.getOutput());
		
		for(int i = 0; i < 6; i++) {
			Connection[] andGateGroup = {clockInverter.getOutput(), myDecoder.getOutputConnections()[i]};
			decoderBuffers[i] = new AndGate(andGateGroup);
			bufferGroup[i] = decoderBuffers[i].getOutput();
		}
		
		myOutputs = bufferGroup;
	}
	
	public void setupInstructionHandler(Register instructionRegister) {
		Connection[] instructionOutputs = instructionRegister.getOutputConnections();
		
		Connection[] decoderInputs = new Connection[4];
		for(int i = 0; i < 4; i++) {
			decoderInputs[i] = instructionOutputs[i];
		}
		Decoder instructionDecoder = new Decoder(decoderInputs);
		myAddConnection = instructionDecoder.getOutputConnections()[1];
		Connection myAndConnection = instructionDecoder.getOutputConnections()[5];
		Connection myNotConnection = instructionDecoder.getOutputConnections()[9];
		
		Connection[] aluOuts = {myAddConnection, myAndConnection, myNotConnection};
		OrGate aluInstrGate = new OrGate(aluOuts);
		AndGate regLoadGate = new AndGate(aluInstrGate.getOutput(), myOutputs[5]);
		myRegLoadConnection = regLoadGate.getOutput();
		
		
		Connection placeholder = new Connection();
		myNotConnection.powerOff();
		myAndConnection.powerOff();
		placeholder.powerOff();
		
		//TODO: Below is a rudimentary binary encoder. Consider making a class to represent this.
		OrGate[] alukGates = new OrGate[2];
		Connection[] inputs0 = {myAndConnection, placeholder};
		alukGates[0] = new OrGate(inputs0);
		Connection[] inputs1 = {myNotConnection, placeholder};
		alukGates[1] = new OrGate(inputs1);
		
		this.aluk = new Connection[2];
		this.aluk[0] = alukGates[0].getOutput();
		this.aluk[1] = alukGates[1].getOutput();

		AndGate[] sr1Gates, sr2Gates, drGates;
		sr1Gates = new AndGate[3];
		sr2Gates = new AndGate[3];
		drGates = new AndGate[3];
		
		mySR1Selects = new Connection[3];
		mySR2Selects = new Connection[3];
		myDRSelects = new Connection[3];
		
		for(int i = 4, j = 0; i < 7; i++, j++) {
			drGates[j] = new AndGate(instructionOutputs[i], aluInstrGate.getOutput());
			myDRSelects[2 - j] = drGates[j].getOutput();
		}
		for(int i = 7, j = 0; i < 10; i++, j++) {
			sr1Gates[j] = new AndGate(instructionOutputs[i], aluInstrGate.getOutput());
			mySR1Selects[j] = sr1Gates[j].getOutput();
		}
		for(int i = 13, j = 0; i < 16; i++, j++) {
			sr2Gates[j] = new AndGate(instructionOutputs[i], aluInstrGate.getOutput());
			mySR2Selects[j] = sr2Gates[j].getOutput();
		}
		
		

	}
	
	public Connection getPCLoad() {
		return myOutputs[PC_LOAD];
	}
	
	public Connection getMARLoad() {
		return myOutputs[MAR_LOAD];
	}
	
	public Connection getMDRLoad() {
		return myOutputs[MDR_LOAD];
	}
	
	public Connection getIRLoad() {
		return myOutputs[IR_LOAD];
	}
	
	public Connection getALULoad() {
		return myOutputs[ALU_LOAD];
	}
	
	public Connection[] getALUK() {
		return aluk;
	}
	
	public Connection getREGLoad() {
		return myRegLoadConnection;
	}
	
	public void start() {
		myClock.start();
	}
	
	public Connection[] getOutputs() {
		return myOutputs;
	}
	
	public Connection[] getSR1Selects() {
		return mySR1Selects;
	}
	
	public Connection[] getSR2Selects() {
		return mySR2Selects;
	}
	
	public Connection[] getDRSelects() {
		return myDRSelects;
	}
	
	
}
