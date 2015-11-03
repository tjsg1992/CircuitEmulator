package circuit.clock;

import transistor.Connection;
import circuit.storage.TFlipFlop;

public class ClockGate {
	private TFlipFlop[] myLatches;
	private Connection[] myOutputConnections;
	private Connection myClock;
	
	public ClockGate(Connection[] theInputConnections, Connection theClock) {
		myClock = theClock;
		
		for(int i = 0; i < theInputConnections.length; i++) {
			myLatches[i] = new TFlipFlop(theInputConnections[i], myClock);
			myOutputConnections[i] = myLatches[i].getOutputA();			
		}
	}
}