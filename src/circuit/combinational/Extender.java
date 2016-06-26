package circuit.combinational;

import transistor.Connection;

/**
 * Increase the width of a set of connections. <br>
 * May either extend the sign or extend zeroes.
 * 
 * @author Taylor Gorman
 * @version 1.1, Jun 25, 2016
 */
public class Extender {
	private Connection[] myInputs;
	private Connection[] myOutputs;
	private int myOutputWidth;
	
	/**
	 * 
	 * @param theInputLines
	 * @param theNewWidth
	 * @param zeroExtension False for sign extension; true for zero extension
	 */
	public Extender(Connection[] theInputLines, int theNewWidth, boolean zeroExtension) {
		if (theInputLines.length > theNewWidth) {
			throw new IllegalArgumentException("Output width of extender must exceed or equal input width.");
		}
		this.myInputs = theInputLines;
		this.myOutputWidth = theNewWidth;
		
		this.myOutputs = new Connection[this.myOutputWidth];
		for (int i = this.myOutputs.length - this.myInputs.length, j = 0; i < this.myOutputs.length; i++, j++) {
			this.myOutputs[i] = this.myInputs[j];
		}
		
		for (int i = 0; i < this.myOutputs.length - this.myInputs.length; i++) {
			this.myOutputs[i] = new Connection();
			if (!zeroExtension) {
				//Sign extended.
				if (this.myInputs[0].hasPower()) {
					this.myOutputs[i].powerOn();
				} else {
					this.myOutputs[i].powerOff();
				}
			} else {
				//Zero extended.
				this.myOutputs[i].powerOff();
			}
		}
	}
	
	public Connection[] getOutputs() {
		return this.myOutputs;
	}

}
