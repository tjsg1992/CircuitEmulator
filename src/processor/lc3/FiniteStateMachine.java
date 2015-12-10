package processor.lc3;

import gate.AndGate;
import gate.NandGate;
import gate.NotGate;
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
		NotGate clockInverter = new NotGate(myClock.getOutput());
		
		RippleCounter myCounter = new RippleCounter(myClock.getOutput(), 4);
		Decoder myDecoder = new Decoder(myCounter.getOutputConnections());
		
		AndGate[] decoderBuffers = new AndGate[16];
		Connection[] bufferGroup = new Connection[16];
		for(int i = 0; i < 4; i++) {
			Connection[] andGateGroup = {clockInverter.getOutput(), myDecoder.getOutputConnections()[i]};
			decoderBuffers[i] = new AndGate(andGateGroup);
			bufferGroup[i] = decoderBuffers[i].getOutput();
		}
		
		myOutputs = bufferGroup;
		
		haltCounter = new RippleCounter(myOutputs[3], 4);
		haltDecoder = new Decoder(haltCounter.getOutputConnections());
		
		//myClock.setHaltLine(myOutputs[3]);
		//myClock.setHaltLine(haltDecoder.getOutputConnections()[4]);
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
	
	public Connection getIRLoad() {
		return myOutputs[3];
	}
	
	public void start() {
		myClock.start();
	}
	
	public Connection[] getOutputs() {
		return myOutputs;
	}
	
	
}
