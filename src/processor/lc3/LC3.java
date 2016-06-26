package processor.lc3;

import java.util.Arrays;

import main.CircuitBuilder;
import main.MemoryLoader;
import transistor.Connection;
import transistor.Junction;
import circuit.combinational.Extender;
import circuit.combinational.FullAdder;
import circuit.combinational.Multiplexer;
import circuit.combinational.MultiplexerArray;
import circuit.combinational.Reverser;
import circuit.combinational.RippleAdder;
import circuit.storage.GatedRegister;
import circuit.storage.MemoryArray;
import circuit.storage.Register;
import circuit.storage.RegisterFile;

/**
 * An implementation of the LC3 processor.
 *
 * @author Taylor Gorman
 * @version 1.0, Dec 19, 2015
 */
public class LC3 {
	public static final int MEMORY_SIZE = 10;
	public static final int WORD_SIZE = 16;
	
	private FiniteStateMachine myStateMachine;
	private ProcessorBus myBus;	
	private GatedRegister programCounter;
	private Connection[] myOutputConnections; //TEST OUTPUT
	
	/**
	 * Construct a LC3 processor.
	 */
	public LC3() {
		myBus = new ProcessorBus(WORD_SIZE);
		myStateMachine = new FiniteStateMachine();
		
		setupProgramCounter();
		setupMemory();		
		Register instructionRegister =
				new Register(myBus.getOutputs(), myStateMachine.getIRLoad());
		myStateMachine.setupInstructionHandler(instructionRegister);
		setupProcessingUnit();
		setupAddressAdder();
		
		myStateMachine.start();
	}
	
	
	/*
	 * Setup the Memory section of the LC3, including the Memory, MAR, and MDR.
	 * Also includes the Loader mechanism for writing .txt contents into the Memory.
	 */
	private void setupMemory() {		
		//The MAR stores the next instruction address from the bus.
		Register memoryAddressRegister = new Register(myBus.getOutputs(), myStateMachine.getMARLoad());
		
		//Create the memory inputs for our Memory along with its write line.
		Connection[] memoryInputs = new Connection[WORD_SIZE];
		for(int i = 0; i < WORD_SIZE; i++) {
			memoryInputs[i] = new Connection();
		}
		Connection memoryWE = new Connection();
		
		//Create the memory. Memory inputs will later be connected to the Memory Loader, and is indexed by the MAR.
		MemoryArray memory = new MemoryArray(memoryInputs,
				Arrays.copyOfRange(memoryAddressRegister.getOutputConnections(), 0, MEMORY_SIZE), memoryWE);
		
		//The MDR stores the instruction located at the memory address stored in the MAR. Linked to the bus.
		Register memoryDataRegister = 
				new Register(memory.getOutputConnections(), myStateMachine.getMDRLoad());		
		myBus.addInput(memoryDataRegister.getOutputConnections(), myStateMachine.getMDRGate());
		
		//Connect a MemoryLoader to the Memory that loads it with assembly code from a .txt file.
		MemoryLoader loader = new MemoryLoader(memoryInputs,
				memoryAddressRegister.getOutputConnections(), memoryWE,
				"memory-load.txt");
		loader.loadMemory();
	}
	
	/*
	 * Set up the Program Counter section of the LC3, including the PC and incrementer.
	 * The PC starts at 0, and counts upwards by one unless manually set.
	 */
	private void setupProgramCounter() {
		
		//Summand B for Ripple Adder. Set to one so that we increment.
		Connection[] adderConnections = new Connection[MEMORY_SIZE];
		
		//Cycle exists between the PC and the incrementer; use intermediary junctions accordingly.
		Junction[] inputJunctions = new Junction[MEMORY_SIZE];
		Connection[] junctionOutputs = new Connection[MEMORY_SIZE];
		
		for(int i = 0; i < MEMORY_SIZE; i++) {
			adderConnections[i] = new Connection();
			inputJunctions[i] = new Junction(new Connection());
			junctionOutputs[i] = inputJunctions[i].getOutput();
		}		
		adderConnections[0].powerOn();
		
		//Create the PC, connected to the junctions, and the incrementer, whose Summand A is the PC.
		programCounter = new GatedRegister(junctionOutputs, myStateMachine.getPCLoad());
		RippleAdder counterIncrement = new RippleAdder(programCounter.getOutputConnections(), adderConnections);

		//Connect the incrementer to the PC's intermediary junctions.
		for(int i = 0; i < MEMORY_SIZE; i++) {
			inputJunctions[i].setInput(counterIncrement.getOutputSums()[i]);
			counterIncrement.getOutputSums()[i].connectOutputTo(inputJunctions[i]);
		}

		//GatePC is loaded with the current PC value and connected to the bus.
		Reverser r1 = new Reverser(programCounter.getOutputConnections());
		Extender e = new Extender(r1.getOutputs(), WORD_SIZE, true);
		Reverser r2 = new Reverser(e.getOutputs());
		myBus.addInput(r2.getOutputs(), myStateMachine.getPCGate());
	}
	
	/*
	 * Set up the Processing section of the LC3, including the Register File and ALU.
	 */
	private void setupProcessingUnit() {
		RegisterFile myRegisterFile = new RegisterFile(myStateMachine.getDRSelects(), myStateMachine.getSR1Selects(),
				myStateMachine.getSR2Selects(), myStateMachine.getREGLoad(), WORD_SIZE);
		
		/*
		 * Setup Input B
		 */
		Extender imm5Extender = new Extender(myStateMachine.getImm5Lines(), WORD_SIZE, false);
		//Apparently at some point the registers needed to be flipped.
		Reverser imm5Reverser = new Reverser(imm5Extender.getOutputs());
		MultiplexerArray inputBMux = new MultiplexerArray(myRegisterFile.getSR2Outputs(), imm5Reverser.getOutputs(),
				myStateMachine.getImmediateSelect());
		
		ALU alu = new ALU(myRegisterFile.getSR1Outputs(), inputBMux.getOutputs(), myStateMachine.getALUK());
		GatedRegister gateALU = new GatedRegister(alu.getOutputs(), myStateMachine.getALULoad());
		
		myBus.addInput(gateALU.getOutputConnections(), myStateMachine.getALUGate());
		
		//Connect the Register File inputs to the GateALU outputs from the bus.
		for(int i = 0; i < 16; i++) {
			myRegisterFile.getRegisterInputs()[i].setInput(myBus.getOutputs()[i]);
			myBus.getOutputs()[i].connectOutputTo(myRegisterFile.getRegisterInputs()[i]);
		}
		
		//TEST OUTPUT
		myOutputConnections = myRegisterFile.getOutputConnections();
		
	}
	
	private void setupAddressAdder() {
		//ADDR1MUX
		//For now, PC only
		
		//ADDR2MUX
		//For now, PCoffset9 only
		Extender pcOffset9Extender = new Extender(myStateMachine.getPCOffset9Lines(), WORD_SIZE, false);
		Extender pcExtender = new Extender(this.programCounter.getOutputConnections(), WORD_SIZE, true);
		RippleAdder addressAdder = new RippleAdder(pcExtender.getOutputs(), pcOffset9Extender.getOutputs());
	}
	
	/**
	 * TEST OUTPUT
	 */
	public Connection[] getCurrentOutput() {
		return myOutputConnections;
	}

}
