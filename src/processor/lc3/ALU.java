package processor.lc3;

import gate.AndGate;
import gate.NotGate;
import gate.OrGate;
import circuit.combinational.Decoder;
import circuit.combinational.RippleAdder;
import transistor.Connection;

public class ALU {
	/*
	 * The LC3 ALU supports ADD, NOT, and AND. Therefore, three select lines are expected.
	 */
	private static final int NUM_SELECTS_EXPECTED = 2;
	private static final int ADD_SELECT_LINE = 0;
	private static final int NOT_SELECT_LINE = 1;
	private static final int AND_SELECT_LINE = 2;
	private Connection[] myInputA;
	private Connection[] myInputB;
	private Connection[] myOutput;
	private Connection[] myOperationSelects;
	
	/**
	 * Expected Select Lines: <br>
	 * 00 : ADD <br>
	 * 01 : NOT <br>
	 * 10 : AND <br>
	 * @param theInputA
	 * @param theInputB
	 * @param theOperationSelects
	 */
	public ALU(Connection[] theInputA, Connection[] theInputB, Connection[] theOperationSelects) {
		if (theInputA.length != theInputB.length) {
			throw new IllegalArgumentException("ALU inputs must be of the same length. Input A was " + 
					theInputA.length + " , Input B was " + theInputB.length);
		}
		if (theOperationSelects.length != NUM_SELECTS_EXPECTED) {
			throw new IllegalArgumentException("Exactly " + NUM_SELECTS_EXPECTED + " select lines are required.");
		}
		
		this.myInputA = theInputA;
		this.myInputB = theInputB;
		this.myOperationSelects = theOperationSelects;
		this.myOutput = new Connection[this.myInputA.length];
		
		setupALU();
	}
	
	private void setupALU() {
		//DECODER
		//Convert two select lines to four, three of which represent ADD, NOT, and AND
		Decoder instDecoder = new Decoder(this.myOperationSelects);		
		
		//ADD
		RippleAdder add = new RippleAdder(this.myInputA, this.myInputB);
		AndGate[] addOuts = new AndGate[this.myInputA.length];
		for (int i = 0; i < this.myInputA.length; i++) {
			Connection[] inputs = {add.getOutputSums()[i], instDecoder.getOutputConnections()[ADD_SELECT_LINE]};
			addOuts[i] = new AndGate(inputs);
		}
		
		//NOT
		NotGate[] not = new NotGate[this.myInputA.length];
		for (int i = 0; i < this.myInputA.length; i++) {
			not[i] = new NotGate(this.myInputA[i]);
		}
		AndGate[] notOuts = new AndGate[this.myInputA.length];
		for (int i = 0; i < this.myInputA.length; i++) {
			Connection[] inputs = {not[i].getOutput(), instDecoder.getOutputConnections()[NOT_SELECT_LINE]};
			notOuts[i] = new AndGate(inputs);
		}
		
		//AND
		AndGate[] and = new AndGate[this.myInputA.length];
		for (int i = 0; i < this.myInputA.length; i++) {
			Connection[] gateInputs = {this.myInputA[i], this.myInputB[i]};
			and[i] = new AndGate(gateInputs);
		}
		AndGate[] andOuts = new AndGate[this.myInputA.length];
		for (int i = 0; i < this.myInputA.length; i++) {
			Connection[] inputs = {and[i].getOutput(), instDecoder.getOutputConnections()[AND_SELECT_LINE]};
			andOuts[i] = new AndGate(inputs);
		}
		
		OrGate[] outputGates = new OrGate[this.myInputA.length];
		for (int i = 0; i < this.myInputA.length; i++) {
			Connection[] inputs = {addOuts[i].getOutput(), notOuts[i].getOutput(), andOuts[i].getOutput()};
			outputGates[i] = new OrGate(inputs);
			this.myOutput[i] = outputGates[i].getOutput();
		}
	}
	
	public Connection[] getOutputs() {
		return this.myOutput;
	}
}
