package processor.lc3;

import gate.OrGate;

import java.util.ArrayList;
import java.util.List;

import main.CircuitBuilder;
import circuit.combinational.TristateBuffer;
import transistor.Connection;

/**
 * A simple communications system for transferring data between components.
 *
 * @author Taylor Gorman
 * @version 1.0, Dec 19, 2015
 *
 */
public class ProcessorBus {
	private Connection[] myOutputs;
	private int mySize;
	private List<Connection[]> myInputSets;
	
	public ProcessorBus(int theSize) {
		this.mySize = theSize;
		this.myInputSets = new ArrayList<Connection[]>();
		
		this.myOutputs = new Connection[this.mySize];
		for (int i = 0; i < this.mySize; i++) {
			this.myOutputs[i] = new Connection();
		}	
	}
	
	public void addInput(Connection[] theInput, Connection selectLine) {
		if (theInput.length != this.mySize) {
			throw new IllegalArgumentException("Input of width " + theInput.length + " added to processor bus. "
					+ "Width of " + this.mySize + " expected.");
		}
		
		TristateBuffer inputBuffer = new TristateBuffer(theInput, selectLine);
		this.myInputSets.add(inputBuffer.getOutputs());
		
		OrGate[] columnGates = new OrGate[this.mySize];
		
		if (this.myInputSets.size() == 1) {
			this.myOutputs = this.myInputSets.get(0);
		} else {
			for (int i = 0; i < this.mySize; i++) {
				Connection[] gateInputs = new Connection[this.myInputSets.size()];
				for (int j = 0; j < this.myInputSets.size(); j++) {
					gateInputs[j] = this.myInputSets.get(j)[i];			
				}				
				columnGates[i] = new OrGate(gateInputs);
				this.myOutputs[i] = columnGates[i].getOutput();
			}
		}
		
		
	}
	
	public Connection[] getOutputs() {
		return this.myOutputs;
	}
	
	public List<Connection[]> testGetInputs() {
		return this.myInputSets;
	}
}
