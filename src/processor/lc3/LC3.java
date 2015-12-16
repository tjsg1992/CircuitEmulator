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

public class LC3 {
	static final int MEMORY_SIZE = 10;
	static final int WORD_SIZE = 16;
	private FiniteStateMachine myStateMachine;
	private Connection[] myOutputConnections;
	private Register[] myRegisters;
	
	private Junction[] myRegisterInputs;
	private Connection[] mySR1Outputs;
	private Connection[] mySR2Outputs;
	
	private RippleAdder myRegisterAdder;
	
	Register myInstructionRegister;
	
	public LC3() {
		myStateMachine = new FiniteStateMachine();
		initialize();		
		setupRegisters();
		setupRegisterAdder();
		myOutputConnections = myRegisters[2].getOutputConnections();
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
	}
	
	private void setupRegisters() {
		myRegisters = new Register[8];
		myRegisterInputs = new Junction[16];
		Connection[] inputConnections = new Connection[16];
		Connection[] regWrites = new Connection[8];
		
		Decoder destDecoder = new Decoder(myStateMachine.getDRSelects());
		
		for(int i = 0; i < 16; i++) {
			myRegisterInputs[i] = new Junction(new Connection());
			inputConnections[i] = myRegisterInputs[i].getOutput();
		}
		
		for(int i = 0; i < 8; i++) {
			AndGate weGate = new AndGate(destDecoder.getOutputConnections()[i], myStateMachine.getREGLoad());
			regWrites[i] = weGate.getOutput();
			myRegisters[i] = new Register(inputConnections, regWrites[i]);
		}

		Connection[][] sr1Groups = new Connection[16][8];
		Connection[][] sr2Groups = new Connection[16][8];
		Connection[][] drGroups = new Connection[16][8];
		
		for(int i = 0; i < 16; i++) {
			for(int j = 0; j < 8; j++) {
				sr1Groups[i][j] = myRegisters[j].getOutputConnections()[i];
				sr2Groups[i][j] = myRegisters[j].getOutputConnections()[i];
				drGroups[i][j] = myRegisters[j].getOutputConnections()[i];
			}
		}
		
		Multiplexer[] sr1Muxes = new Multiplexer[16];
		Multiplexer[] sr2Muxes = new Multiplexer[16];
		
		mySR1Outputs = new Connection[16];
		mySR2Outputs = new Connection[16];
		
		for(int i = 0; i < 16; i++) {
			sr1Muxes[i] = new Multiplexer(sr1Groups[i], myStateMachine.getSR1Selects());
			sr2Muxes[i] = new Multiplexer(sr2Groups[i], myStateMachine.getSR2Selects());
			
			mySR1Outputs[i] = sr1Muxes[i].getOutput();
			mySR2Outputs[i] = sr2Muxes[i].getOutput();
		}
		
		//Testng
		inputConnections[0].powerOn();
		regWrites[0].powerOn();
		regWrites[0].powerOff();	
		inputConnections[0].powerOff();
	}
	
	private void setupRegisterAdder() {
		myRegisterAdder = new RippleAdder(mySR1Outputs, mySR2Outputs);
		GatedRegister gateALU = new GatedRegister(myRegisterAdder.getOutputSums(), myStateMachine.getALULoad());
		
		for(int i = 0; i < 16; i++) {
			myRegisterInputs[i].setInput(gateALU.getOutputConnections()[i]);
			gateALU.getOutputConnections()[i].connectOutputTo(myRegisterInputs[i]);
		}
	}
	
	public Connection[] getCurrentOutput() {
		return myOutputConnections;
	}
	
	protected Register getInstructionRegsiter() {
		return myInstructionRegister;
	}

}
