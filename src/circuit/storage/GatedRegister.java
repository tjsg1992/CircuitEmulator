package circuit.storage;

import transistor.Connection;

/**
 * A Register whose values only change on the rising edge of its WE Line.
 * @author Taylor Gorman
 *
 */
public class GatedRegister {
	private TFlipFlop[] myLatches;
	private Connection[] myOutputConnections;
	private Connection myClock;
	
	public GatedRegister(Connection[] theInputConnections, Connection theClock) {
		myClock = theClock;
		myLatches = new TFlipFlop[theInputConnections.length];
		myOutputConnections = new Connection[theInputConnections.length];
		
		for(int i = 0; i < theInputConnections.length; i++) {
			myLatches[i] = new TFlipFlop(myClock, theInputConnections[i]);
			myOutputConnections[i] = myLatches[i].getOutputA();			
		}
	}
	
	public Connection[] getOutputConnections() {
		return myOutputConnections;
	}
}