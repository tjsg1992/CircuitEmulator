package processor.lc3;

import main.MemoryLoader;
import transistor.Connection;
import transistor.Junction;
import circuit.combinational.RippleAdder;
import circuit.storage.GatedRegister;
import circuit.storage.MemoryArray;
import circuit.storage.Register;

public class LC3 {
	static final int MEMORY_SIZE = 8;
	static final int WORD_SIZE = 16;
	private FiniteStateMachine myStateMachine;
	private Connection[] myOutputConnections;
	private Register[] myRegisters;
	
	Register myInstructionRegister;
	
	public LC3() {
		myStateMachine = new FiniteStateMachine();
		initialize();		
		setupRegisters();
	}
	
	/*
	 * Step 1: Load the MAR with the contents of the PC, and increment the PC by 1.
	 */
	
	private void initialize() {
		Connection[] counterConnections = new Connection[MEMORY_SIZE];
		Connection[] adderConnections = new Connection[MEMORY_SIZE];
		Junction[] inputJunctions = new Junction[MEMORY_SIZE];
		Connection[] junctionOutputs = new Connection[MEMORY_SIZE];
		
		for(int i = 0; i < MEMORY_SIZE; i++) {
			counterConnections[i] = new Connection();
			adderConnections[i] = new Connection();
			inputJunctions[i] = new Junction(new Connection());
			junctionOutputs[i] = inputJunctions[i].getOutput();
		}
		
		
		GatedRegister programCounter = new GatedRegister(junctionOutputs, myStateMachine.getPCLoad());
		RippleAdder counterIncrement = new RippleAdder(programCounter.getOutputConnections(), adderConnections);

		for(int i = 0; i < MEMORY_SIZE; i++) {
			inputJunctions[i].setInput(counterIncrement.getOutputSums()[i]);
			counterIncrement.getOutputSums()[i].connectOutputTo(inputJunctions[i]);
		}

		GatedRegister gatePC = new GatedRegister(programCounter.getOutputConnections(), myStateMachine.getPCLoad());
		Register memoryAddressRegister = new Register(gatePC.getOutputConnections(), myStateMachine.getMARLoad());

		Connection[] memoryInputs = new Connection[WORD_SIZE];
		for(int i = 0; i < WORD_SIZE; i++) {
			memoryInputs[i] = new Connection();
		}
		Connection memoryWE = new Connection();
		
		MemoryArray memory = new MemoryArray(memoryInputs,
				memoryAddressRegister.getOutputConnections(), memoryWE);
		
		MemoryLoader loader = new MemoryLoader(memoryInputs,
				memoryAddressRegister.getOutputConnections(), memoryWE,
				"memory-load.txt");
		
		loader.loadMemory();
		
		GatedRegister memoryDataRegister = 
				new GatedRegister(memory.getOutputConnections(), myStateMachine.getMDRLoad());
		
		myInstructionRegister =
				new Register(memoryDataRegister.getOutputConnections(), myStateMachine.getIRLoad());
		
		myStateMachine.setupInstructionHandler(myInstructionRegister);
		
		myOutputConnections = myInstructionRegister.getOutputConnections();
		adderConnections[0].powerOn();
		myStateMachine.start();
	}
	
	private void setupRegisters() {
		myRegisters = new Register[8];
		//TODO
	}
	
	public Connection[] getCurrentOutput() {
		return myOutputConnections;
	}
	
	protected Register getInstructionRegsiter() {
		return myInstructionRegister;
	}

}
