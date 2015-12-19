package processor.lc3;

import gate.AndGate;
import main.MemoryLoader;
import transistor.Connection;
import transistor.Junction;
import circuit.combinational.Decoder;
import circuit.combinational.FullAdder;
import circuit.combinational.Multiplexer;
import circuit.combinational.RippleAdder;
import circuit.storage.GatedRegister;
import circuit.storage.MemoryArray;
import circuit.storage.Register;
import circuit.storage.RegisterFile;

public class LC3 {
	public static final int MEMORY_SIZE = 10;
	public static final int WORD_SIZE = 16;
	
	private FiniteStateMachine myStateMachine;
	private Connection[] myOutputConnections;
	
	private RippleAdder myRegisterAdder;	
	private Register myInstructionRegister;
	private RegisterFile myRegisterFile;
	
	public LC3() {
		myStateMachine = new FiniteStateMachine();
		initialize();
		setupRegisterAdder();
		myOutputConnections = myRegisterFile.getOutputConnections();
		myStateMachine.start();
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
		
		adderConnections[0].powerOn();
		
		myRegisterFile = new RegisterFile(myStateMachine.getDRSelects(), myStateMachine.getSR1Selects(),
				myStateMachine.getSR2Selects(), myStateMachine.getREGLoad(), WORD_SIZE);
	}
	
	private void setupRegisterAdder() {
		myRegisterAdder = new RippleAdder(myRegisterFile.getSR1Outputs(), myRegisterFile.getSR2Outputs());
		GatedRegister gateALU = new GatedRegister(myRegisterAdder.getOutputSums(), myStateMachine.getALULoad());
		
		for(int i = 0; i < 16; i++) {
			myRegisterFile.getRegisterInputs()[i].setInput(gateALU.getOutputConnections()[i]);
			gateALU.getOutputConnections()[i].connectOutputTo(myRegisterFile.getRegisterInputs()[i]);
		}
	}
	
	public void initializeMemory() {
		
	}
	
	public void initializeControlUnit() {
		
	}
	
	public void initializeProcessingUnit() {
		
	}
	
	public Connection[] getCurrentOutput() {
		return myOutputConnections;
	}
	
	protected Register getInstructionRegsiter() {
		return myInstructionRegister;
	}

}
