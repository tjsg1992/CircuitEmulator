package circuit.clock;

import gate.NotGate;
import circuit.storage.EdgeLatch;
import circuit.storage.ToggleFlipFlop;
import transistor.Connection;
import transistor.Junction;

public class Counter {
	private int numCounters;
	private Connection myClockLine;
	private Junction[] myJunctions;
	private ToggleFlipFlop[] myLatches;
	private Connection[] myOutputConnections;

	
	public Counter(Connection theClockLine, int theNumCounters) {
		numCounters = theNumCounters;
		myOutputConnections = new Connection[numCounters];		
		myClockLine = theClockLine;		
		myLatches = new ToggleFlipFlop[numCounters];
		
		myLatches[0] = new ToggleFlipFlop(myClockLine);
		myOutputConnections[0] = myLatches[0].getOutputA();
		
		for(int i = 1; i < numCounters; i++) {
			myLatches[i] = new ToggleFlipFlop(myLatches[i - 1].getOutputB());
			myOutputConnections[i] = myLatches[i].getOutputA();
		}
	}
	
	public Connection[] getOutputConnections() {
		return myOutputConnections;
	}
}
