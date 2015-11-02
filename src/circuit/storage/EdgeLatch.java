package circuit.storage;

import gate.NotGate;
import transistor.Connection;
import transistor.ThreadConnection;

public class EdgeLatch {
	private Connection myDataLine;
	private Connection myWELine;
	private boolean isRisingEdge;
	
	private NotGate myWEInverter;
	private GatedDLatch myLatch1;
	private GatedDLatch myLatch2;
	
	/*
	 * TODO FALLING EDGE SETUP BUGGY
	 */
	
	public EdgeLatch(Connection theDataLine, Connection theWELine, boolean risingEdge) {
		if(!risingEdge) {
			System.err.println("Falling Edge buggy, switching to Rising Edge");
			risingEdge = false;
		}
		myDataLine = theDataLine;
		myWELine = theWELine;
		isRisingEdge = risingEdge;
		
		myWEInverter = new NotGate(myWELine);
		myWEInverter.getOutput().initializeThread(2);
		
		if(isRisingEdge) {
			myLatch1 = new GatedDLatch(myDataLine, myWELine);
			myLatch2 = new GatedDLatch(myLatch1.getOutputA(), myWEInverter.getOutput());
		} else {
			myLatch1 = new GatedDLatch(myDataLine, myWELine);
			myLatch2 = new GatedDLatch(myLatch1.getOutputA(), myWELine);
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
		System.out.println("\n---Edge Latch:");
		System.out.println("---Data Line: " + myDataLine.hasPower());
		System.out.println("---Clock Line: " + myWELine.hasPower() + ", Latch 1 Q: " + myLatch1.getOutputA().hasPower());
		System.out.println("---Sl Clock Line: " + myWEInverter.getOutput().hasPower());
		myLatch2.printStatus();
		System.out.println("---Q: " + getOutputA().hasPower() + ", -Q: " + getOutputB().hasPower());
	}
}
