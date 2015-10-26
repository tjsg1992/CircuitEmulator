package circuit.storage;

import gate.NotGate;
import transistor.Connection;

public class FallingEdgeLatch {
	private Connection myDataLine;
	private Connection myWELine;
	
	private NotGate myWEInverter;
	private GatedDLatch myLatch1;
	private GatedDLatch myLatch2;
	
	public FallingEdgeLatch(Connection theDataLine, Connection theWELine) {
		myDataLine = theDataLine;
		myWELine = theWELine;
		
		myWEInverter = new NotGate(myWELine);
		
		myLatch1 = new GatedDLatch(myDataLine, myWELine);
		myLatch2 = new GatedDLatch(myLatch1.getOutputA(), myWEInverter.getOutput());
	}
	
	public Connection getOutputA() {
		return myLatch2.getOutputA();
	}
	
	public Connection getOutputB() {
		return myLatch2.getOutputB();
	}
}
