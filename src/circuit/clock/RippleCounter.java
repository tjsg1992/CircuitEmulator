package circuit.clock;

import circuit.storage.TFlipFlop;
import transistor.Connection;

public class RippleCounter {
	private Connection myClock;
	private Connection myToggle;
	
	private TFlipFlop[] myLatches;
	
	private Connection[] myOutputConnections;
	
	private int mySize;
	
	public RippleCounter(Connection theClock, int theSize) {
		myClock = theClock;
		mySize = theSize;
		myToggle = new Connection();
		myToggle.powerOff();
		
		myOutputConnections = new Connection[mySize];
		myLatches = new TFlipFlop[mySize];
		
		myLatches[0] = new TFlipFlop(myClock, myToggle);
		myOutputConnections[0] = myLatches[0].getOutputA();
		
		for(int i = 1; i < mySize; i++) {
			myLatches[i] = new TFlipFlop(myLatches[i - 1].getOutputB(), myToggle);
			myOutputConnections[i] = myLatches[i].getOutputA();
		}
		
		myToggle.powerOn();	
		
	}
	
	public Connection[] getOutputConnections() {
		return myOutputConnections;
	}
}