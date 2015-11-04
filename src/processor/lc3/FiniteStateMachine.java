package processor.lc3;

import gate.AndGate;
import gate.NandGate;
import transistor.Connection;
import circuit.clock.Clock;
import circuit.clock.RippleCounter;
import circuit.combinational.Decoder;

public class FiniteStateMachine {
	private Clock myClock;
	private Connection[] myOutputs;
	
	private RippleCounter haltCounter;
	private Decoder haltDecoder;
	
	public FiniteStateMachine() {
		myClock = new Clock();
		
		RippleCounter myCounter = new RippleCounter(myClock.getOutput(), 2);
		Decoder myDecoder = new Decoder(myCounter.getOutputConnections());
		myOutputs = myDecoder.getOutputConnections();
		
		haltCounter = new RippleCounter(myOutputs[3], 4);
		haltDecoder = new Decoder(haltCounter.getOutputConnections());
		
		myClock.setHaltLine(myOutputs[3]);
		//myClock.setHaltLine(haltDecoder.getOutputConnections()[15]);
	}
	
	public Connection getPCLoad() {
		return myOutputs[0];
	}
	
	public Connection getMARLoad() {
		return myOutputs[1];
	}
	
	public Connection getMDRLoad() {
		return myOutputs[2];
	}
	
	public void start() {
		myClock.start();
	}
	
	public Connection[] getOutputs() {
		return myOutputs;
	}
	
	
}
