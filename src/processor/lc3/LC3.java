package processor.lc3;

import transistor.Connection;
import transistor.Junction;
import circuit.clock.Clock;
import circuit.combinational.FullAdder;
import circuit.combinational.RippleAdder;
import circuit.storage.GatedRegister;
import circuit.storage.Register;

public class LC3 {
	static final int WORD_SIZE = 16;
	private FiniteStateMachine myStateMachine;
	private Connection[] myOutputConnections;
	
	public LC3() {
		myStateMachine = new FiniteStateMachine();
		loadRegisters();
		
		
		
	}
	
	/*
	 * Step 1: Load the MAR with the contents of the PC, and increment the PC by 1.
	 */
	
	private void loadRegisters() {
		Connection[] counterConnections = new Connection[WORD_SIZE];
		Connection[] adderConnections = new Connection[WORD_SIZE];
		Junction[] inputJunctions = new Junction[WORD_SIZE];
		Connection[] junctionOutputs = new Connection[WORD_SIZE];
		
		for(int i = 0; i < WORD_SIZE; i++) {
			counterConnections[i] = new Connection();
			adderConnections[i] = new Connection();
			inputJunctions[i] = new Junction(new Connection());
			junctionOutputs[i] = inputJunctions[i].getOutput();
		}
		
		Junction delayedPCJunction = new Junction(myStateMachine.getPCLoad());
		myStateMachine.getPCLoad().connectOutputTo(delayedPCJunction);
		Connection delayedPCLoad = delayedPCJunction.getOutput();
		delayedPCLoad.initializeThread(900000);
		
		

		GatedRegister programCounter = new GatedRegister(junctionOutputs, delayedPCLoad);
		RippleAdder counterIncrement = new RippleAdder(programCounter.getOutputConnections(), adderConnections);
		
		for(int i = 0; i < WORD_SIZE; i++) {
			inputJunctions[i].setInput(counterIncrement.getOutputSums()[i]);
			counterIncrement.getOutputSums()[i].connectOutputTo(inputJunctions[i]);
		}
		
		GatedRegister gatePC = new GatedRegister(programCounter.getOutputConnections(), myStateMachine.getPCLoad());
		Register memoryAddressRegister = new Register(gatePC.getOutputConnections(), myStateMachine.getMARLoad());
		myOutputConnections = memoryAddressRegister.getOutputConnections();
		adderConnections[0].powerOn();

		myStateMachine.start();
		
		
				
	}
	
	public Connection[] getCurrentOutput() {
		return myOutputConnections;
	}

}
