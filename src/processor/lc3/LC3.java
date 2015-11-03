package processor.lc3;

import circuit.clock.Clock;
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
		//Register programCounter = new Register(WORD_SIZE);
		
		//Register memoryAddressRegister = new Register(WORD_SIZE);
	}

}
