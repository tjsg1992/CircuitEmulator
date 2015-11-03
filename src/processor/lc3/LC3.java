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
		
		adderConnections[0].powerOn();
		Register programCounter = new Register(junctionOutputs, myStateMachine.getPCLoad());	
		RippleAdder counterIncrement = new RippleAdder(programCounter.getOutputConnections(), adderConnections);
		
		for(int i = 0; i < WORD_SIZE; i++) {
			inputJunctions[i].setInput(counterIncrement.getOutputSums()[i]);
			counterIncrement.getOutputSums()[i].addJunction(inputJunctions[i]);
		}
		
		GatedRegister gatePC = new GatedRegister(programCounter.getOutputConnections(), myStateMachine.getPCLoad());
		Register memoryAddressRegister = new Register(gatePC.getOutputConnections(), myStateMachine.getMARLoad());
		
		while(true) {
			try {
				Thread.sleep(6);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			for(int i = 0; i < WORD_SIZE; i++) {
				if(junctionOutputs[WORD_SIZE - i - 1].hasPower()) System.out.print("1");
				else System.out.print("0");
			}
			System.out.print("\n");
		}
		
	}

}
