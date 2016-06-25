package circuit.combinational;

import transistor.Connection;

public class MultiplexerArray {
	private Connection[][] myInputSets;
	private Connection[] mySelectLines;
	private Connection[] myOutputs;
	
	public MultiplexerArray(Connection[][] theInputSets, Connection[] theSelectLines) {
		this.myInputSets = theInputSets;
		this.mySelectLines = theSelectLines;
		this.myOutputs = new Connection[this.myInputSets[0].length];
		
		Multiplexer[] muxArray = new Multiplexer[this.myInputSets[0].length];
		for (int i = 0; i < muxArray.length; i++) {
			Connection[] muxInputs = new Connection[this.myInputSets.length];
			for (int j = 0; j < muxInputs.length; j++) {
				muxInputs[j] = this.myInputSets[j][i];
			}
			muxArray[i] = new Multiplexer(muxInputs, this.mySelectLines);
			this.myOutputs[i] = muxArray[i].getOutput();
		}
	}
	
	public MultiplexerArray(Connection[] inputSetA, Connection[] inputSetB, Connection theSelectLine) {
		this(packInputs(inputSetA, inputSetB), packSelects(theSelectLine));
	}
	
	public Connection[] getOutputs() {
		return this.myOutputs;
	}
	
	private static Connection[][] packInputs(Connection[] inputSetA, Connection[] inputSetB) {
		Connection[][] inputSets = {inputSetA, inputSetB};
		return inputSets;
	}	
	
	private static Connection[] packSelects(Connection theSelectLine) {
		Connection[] selects = {theSelectLine};
		return selects;
	}
	
	
}
