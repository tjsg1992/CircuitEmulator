package circuit.clock;

import circuit.storage.ToggleFlipFlop;
import transistor.Connection;

public class Counter {
	private int numCounters;
	private Connection myClockLine;
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
	
	public void printStatus() {
		for(int i = 0; i < numCounters; i++) {
			System.out.println(i + ": " + myLatches[i].getOutputA().hasPower() + " " + myLatches[i].getOutputB().hasPower());
		}
	}
}
