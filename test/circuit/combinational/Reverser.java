package circuit.combinational;

import transistor.Connection;

public class Reverser {
	private Connection[] myInputs;
	private Connection[] myOutputs;
	
	public Reverser(Connection[] theInputs) {
		this.myInputs = theInputs;
		this.myOutputs = new Connection[myInputs.length];
		for (int i = 0; i < this.myInputs.length; i++) {
			this.myOutputs[this.myInputs.length - (i + 1)] = this.myInputs[i];
		}
	}
	
	public Connection[] getOutputs() {
		return this.myOutputs;
	}
}
