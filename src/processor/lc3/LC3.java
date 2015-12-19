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
	private ProcessorBus myBus;	
	private Connection[] myOutputConnections; //TEST OUTPUT
	
	public LC3() {
		myBus = new ProcessorBus();
		myStateMachine = new FiniteStateMachine();
		
		setupProgramCounter();
		setupMemory();		
		Register instructionRegister =
				new Register(myBus.getGateMDROutputs(), myStateMachine.getIRLoad());
		myStateMachine.setupInstructionHandler(instructionRegister);		
		setupProcessingUnit();
		
		myStateMachine.start();
	}
	
	private void setupMemory() {
		Register memoryAddressRegister = new Register(myBus.getGatePCOutputs(), myStateMachine.getMARLoad());
		
		Connection[] memoryInputs = new Connection[WORD_SIZE];
		for(int i = 0; i < WORD_SIZE; i++) {
			memoryInputs[i] = new Connection();
		}
		Connection memoryWE = new Connection();
		
		MemoryArray memory = new MemoryArray(memoryInputs,
				memoryAddressRegister.getOutputConnections(), memoryWE);
		

		
		GatedRegister memoryDataRegister = 
				new GatedRegister(memory.getOutputConnections(), myStateMachine.getMDRLoad());
		
		myBus.setGateMDROutputs(memoryDataRegister.getOutputConnections());
		
		MemoryLoader loader = new MemoryLoader(memoryInputs,
				memoryAddressRegister.getOutputConnections(), memoryWE,
				"memory-load.txt");
		loader.loadMemory();
	}
	
	private void setupProgramCounter() {
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
		myBus.setGatePCOutputs(gatePC.getOutputConnections());
		
		adderConnections[0].powerOn();
	}
	
	private void setupProcessingUnit() {
		RegisterFile myRegisterFile = new RegisterFile(myStateMachine.getDRSelects(), myStateMachine.getSR1Selects(),
				myStateMachine.getSR2Selects(), myStateMachine.getREGLoad(), WORD_SIZE);
		
		RippleAdder myRegisterAdder = new RippleAdder(myRegisterFile.getSR1Outputs(), myRegisterFile.getSR2Outputs());
		GatedRegister gateALU = new GatedRegister(myRegisterAdder.getOutputSums(), myStateMachine.getALULoad());
		
		for(int i = 0; i < 16; i++) {
			myRegisterFile.getRegisterInputs()[i].setInput(gateALU.getOutputConnections()[i]);
			gateALU.getOutputConnections()[i].connectOutputTo(myRegisterFile.getRegisterInputs()[i]);
		}
		
		myOutputConnections = myRegisterFile.getOutputConnections(); //TEST OUTPUT
	}
	
	/**
	 * TEST OUTPUT
	 */
	public Connection[] getCurrentOutput() {
		return myOutputConnections;
	}

}
