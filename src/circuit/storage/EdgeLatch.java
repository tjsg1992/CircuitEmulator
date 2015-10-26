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
	
	/*
	 * TODO RISING EDGE SETUP BUGGY
	 */
	
	public EdgeLatch(Connection theDataLine, Connection theWELine, boolean risingEdge) {
		if(risingEdge) {
			System.err.println("Rising Edge buggy, switching to Falling Edge");
			risingEdge = false;
		}
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
	
	public void printStatus() {
		System.out.println("---Edge Latch:");
		System.out.println("---Data Line: " + myDataLine.hasPower());
		System.out.println("---Clock Line: " + myWELine.hasPower());
		System.out.println("---Q: " + getOutputA().hasPower() + ", -Q: " + getOutputB().hasPower());
	}
}
