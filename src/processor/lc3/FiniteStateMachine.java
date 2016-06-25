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
	//Opcode Index
	private static final int OPCODE_BR = 0;
	private static final int OPCODE_ADD = 1;
	private static final int OPCODE_LD = 2;
	private static final int OPCODE_ST = 3;
	private static final int OPCODE_JSR = 4;
	private static final int OPCODE_AND = 5;
	private static final int OPCODE_LDR = 6;
	private static final int OPCODE_STR = 7;
	private static final int OPCODE_RTI = 8;
	private static final int OPCODE_NOT = 9;
	private static final int OPCODE_LDI = 10;
	private static final int OPCODE_STI = 11;
	private static final int OPCODE_JMP = 12;
										//13 reserved
	private static final int OPCODE_LEA = 14;
	private static final int OPCODE_TRAP = 15;
	
	private static final int PC_LOAD = 0;
	private static final int MAR_LOAD = 1;
	private static final int MDR_LOAD = 2;
	private static final int IR_LOAD = 3;
	private static final int ALU_LOAD = 4;
	private Clock myClock;
	private Connection[] myOutputs;
	
	private Connection[] myDRSelects;
	private Connection[] myConditionSelects;
	private Connection[] mySR1Selects;
	private Connection myImmediateSelect;
	private Connection[] mySR2Selects;
	private Connection[] myImm5Selects;

	private Connection myRegLoadConnection;
	private Connection[] aluk;
	
	private Decoder instructionDecoder;
	
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
		
		instructionDecoder = new Decoder(decoderInputs);		
		Connection[] aluOuts = {getInstrSelect(OPCODE_ADD), getInstrSelect(OPCODE_AND), getInstrSelect(OPCODE_NOT)};
		OrGate aluInstrGate = new OrGate(aluOuts);
		AndGate regLoadGate = new AndGate(aluInstrGate.getOutput(), myOutputs[5]);
		myRegLoadConnection = regLoadGate.getOutput();
		
		
		Connection placeholder = new Connection();
		getInstrSelect(OPCODE_NOT).powerOff();
		getInstrSelect(OPCODE_AND).powerOff();
		placeholder.powerOff();
		
		//TODO: Below is a rudimentary binary encoder. Consider making a class to represent this.
		OrGate[] alukGates = new OrGate[2];
		Connection[] inputs0 = {getInstrSelect(OPCODE_AND), placeholder};
		alukGates[0] = new OrGate(inputs0);
		Connection[] inputs1 = {getInstrSelect(OPCODE_NOT), placeholder};
		alukGates[1] = new OrGate(inputs1);
		
		this.aluk = new Connection[2];
		this.aluk[0] = alukGates[0].getOutput();
		this.aluk[1] = alukGates[1].getOutput();		
		
		
		
		
		//Create select lines.
		myConditionSelects = new Connection[3];
		
		//DR 11-9 / 4-7
		//ADD, AND, LD, LDI, LDR, LEA, NOT
		AndGate[] drGates = new AndGate[3];
		Connection[] drEnableLines = {getInstrSelect(OPCODE_ADD), getInstrSelect(OPCODE_AND), getInstrSelect(OPCODE_LD),
				getInstrSelect(OPCODE_LDI), getInstrSelect(OPCODE_LDR), getInstrSelect(OPCODE_LEA), getInstrSelect(OPCODE_NOT)};
		OrGate drEnableGate = new OrGate(drEnableLines);
		myDRSelects = new Connection[3];
		for(int i = 4, j = 0; i < 7; i++, j++) {
			drGates[j] = new AndGate(instructionOutputs[i], drEnableGate.getOutput());
			myDRSelects[2 - j] = drGates[j].getOutput();
		}
		//Condition Codes
		//NZP 11-9 / 4-7
		//BR
		AndGate[] conditionGates = new AndGate[3];
		Connection[] conEnableLines = {getInstrSelect(OPCODE_BR), new Connection()};
		OrGate conEnableGate = new OrGate(conEnableLines);
		myConditionSelects = new Connection[3];
		for(int i = 4, j = 0; i < 7; i++, j++) {
			conditionGates[j] = new AndGate(instructionOutputs[i], conEnableGate.getOutput());
			myConditionSelects[j] = conditionGates[j].getOutput();
		}
		//Source Registers for Operate Instructions
		//SR1 8-6 / 7-10
		//ADD, AND, NOT
		AndGate[] sr1Gates = new AndGate[3];
		Connection[] sr1EnableLines = {getInstrSelect(OPCODE_ADD), getInstrSelect(OPCODE_AND), getInstrSelect(OPCODE_NOT)};
		OrGate sr1EnableGate = new OrGate(sr1EnableLines);
		mySR1Selects = new Connection[3];
		for(int i = 7, j = 0; i < 10; i++, j++) {
			sr1Gates[j] = new AndGate(instructionOutputs[i], sr1EnableGate.getOutput());
			mySR1Selects[j] = sr1Gates[j].getOutput();
		}
		//Immediate Flag
		//5 / 10
		//ADD, AND
		Connection[] immEnableLines = {getInstrSelect(OPCODE_ADD), getInstrSelect(OPCODE_AND)};
		OrGate immEnableGate = new OrGate(immEnableLines);
		AndGate immGate = new AndGate(instructionOutputs[10], immEnableGate.getOutput());
		myImmediateSelect = immGate.getOutput();
		
		//SR2 2-0 / 13-16
		//ADD, AND
		AndGate[] sr2Gates = new AndGate[3];
		Connection[] sr2EnableLines = {getInstrSelect(OPCODE_ADD), getInstrSelect(OPCODE_AND)};
		OrGate sr2EnableGate = new OrGate(sr2EnableLines);
		mySR2Selects = new Connection[3];		
		for(int i = 13, j = 0; i < 16; i++, j++) {
			sr2Gates[j] = new AndGate(instructionOutputs[i], sr2EnableGate.getOutput());
			mySR2Selects[j] = sr2Gates[j].getOutput();
		}
		
		//imm5 4-0 / 11-16
		//ADD, AND
		AndGate[] imm5Gates = new AndGate[5];
		Connection[] imm5EnableLines = {getInstrSelect(OPCODE_ADD), getInstrSelect(OPCODE_AND)};
		OrGate imm5EnableGate = new OrGate(imm5EnableLines);
		myImm5Selects = new Connection[5];
		for (int i = 11, j = 0; i < 16; i++, j++) {
			imm5Gates[j] = new AndGate(instructionOutputs[i], imm5EnableGate.getOutput());
			myImm5Selects[j] = imm5Gates[j].getOutput();
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
	
	public Connection getImmediateSelect() {
		return myImmediateSelect;
	}
	
	public Connection[] getImm5Lines() {
		return myImm5Selects;
	}
	
	
	private Connection getInstrSelect(int opcode) {
		return instructionDecoder.getOutputConnections()[opcode];
	}
	
}
