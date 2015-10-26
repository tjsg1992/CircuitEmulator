package circuit.storage;

import gate.NotGate;
import transistor.Connection;

public class EdgeLatch {
	private Connection myDataLine;
	private Connection myWELine;
	private boolean isRisingEdge;
	
	private NotGate myWEInverter;
	private GatedDLatch myLatch1;
	private GatedDLatch myLatch2;
	
	public EdgeLatch(Connection theDataLine, Connection theWELine, boolean risingEdge) {
		myDataLine = theDataLine;
		myWELine = theWELine;
		isRisingEdge = risingEdge;
		
		myWEInverter = new NotGate(myWELine);
		
		if(isRisingEdge) {
			myLatch1 = new GatedDLatch(myDataLine, myWELine);
			myLatch2 = new GatedDLatch(myLatch1.getOutputA(), myWELine);
		} else {
			myLatch1 = new GatedDLatch(myDataLine, myWELine);
			myLatch2 = new GatedDLatch(myLatch1.getOutputA(), myWEInverter.getOutput());
		}
		
		
	}
	
	public Connection getOutputA() {
		return myLatch2.getOutputA();
	}
	
	public Connection getOutputB() {
		return myLatch2.getOutputB();
	}
	
	public boolean isRisingEdge() {
		return isRisingEdge;
	}
	
	public boolean isFallingEdge() {
		return !isRisingEdge;
	}
}
