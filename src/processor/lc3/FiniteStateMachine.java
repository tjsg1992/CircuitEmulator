package processor.lc3;

import transistor.Connection;
import circuit.clock.Clock;
import circuit.clock.RippleCounter;
import circuit.combinational.Decoder;

public class FiniteStateMachine {
	private Clock myClock;
	private Connection[] myOutputs;
	
	public FiniteStateMachine() {
		myClock = new Clock();
		myClock.start();
		
		RippleCounter myCounter = new RippleCounter(myClock.getOutput(), 2);
		Decoder myDecoder = new Decoder(myCounter.getOutputConnections());
		myOutputs = myDecoder.getOutputConnections();
	}
	
	public Connection getPCLoad() {
		return myOutputs[0];
	}
	
	public Connection getMARLoad() {
		return myOutputs[1];
	}
	
	
}
