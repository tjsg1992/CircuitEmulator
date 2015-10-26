package circuit.storage;

import transistor.Connection;
import transistor.Junction;
import transistor.ThreadConnection;

public class ToggleFlipFlop {
	private Connection myClockLine;
	private EdgeLatch myLatch;
	private Junction myJunction;
	
	public ToggleFlipFlop(Connection theClockLine) {
		myClockLine = theClockLine;
		ThreadConnection clockThread = new ThreadConnection(myClockLine);
		System.out.println("Clock: " + clockThread.hashCode());
		clockThread.start();
		
		Connection[] dummyGroup = {new Connection()};
		myJunction = new Junction(dummyGroup);
		
		myLatch = new EdgeLatch(myJunction.getOutput(), clockThread.getOutput(), false);
		ThreadConnection outputThread = new ThreadConnection(myLatch.getOutputB());
		System.out.println("Output/Data: " + outputThread.hashCode());

		outputThread.start();
		Connection[] junctionGroup = {outputThread.getOutput()};
		myJunction.setInputs(junctionGroup);
		myLatch.getOutputB().addJunction(myJunction);
	}
	
	public Connection getOutputA() {
		return myLatch.getOutputA();
	}
	
	public Connection getOutputB() {
		return myLatch.getOutputB();
	}
	
	public void printStatus() {
		System.out.println("Clock Line: " + myClockLine.hasPower());
		System.out.println("Data Line: " + myJunction.getOutput().hasPower());
		myLatch.printStatus();
		System.out.println("Q: " + myLatch.getOutputA().hasPower() + ", -Q: " + myLatch.getOutputB().hasPower() + "\n");
	}
}
