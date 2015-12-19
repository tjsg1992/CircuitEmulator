package circuit.storage;

import transistor.Connection;

/**
 * A Register whose values only change on the rising edge of the WE Line.<br>
 * A GatedRegister uses ETD-Flip-Flops rather than Gated-D Latches.
 * 
 * @author Taylor Gorman
 * @version 1.0, Dec 19, 2015
 */
public class GatedRegister {
	private ETDFlipFlop[] myLatches;
	private Connection[] myOutputConnections;
	private Connection myClock;
	
	/**
	 * Construct a GatedRegister
	 * @param theInputConnections the inputs for the register
	 * @param theClock the write-enable line
	 */
	public GatedRegister(Connection[] theInputConnections, Connection theClock) {
		myClock = theClock;
		myLatches = new ETDFlipFlop[theInputConnections.length];
		myOutputConnections = new Connection[theInputConnections.length];
		
		for(int i = 0; i < theInputConnections.length; i++) {
			myLatches[i] = new ETDFlipFlop(theInputConnections[i], myClock);
			myOutputConnections[i] = myLatches[i].getOutputA();			
		}
	}
	
	/**
	 * Return the outputs of the Gated Register.
	 * @return the register outputs
	 */
	public Connection[] getOutputConnections() {
		return myOutputConnections;
	}
}