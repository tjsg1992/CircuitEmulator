package circuit.storage;

import transistor.Connection;
import transistor.Junction;

public class ToggleFlipFlop {
	private Connection myClockLine;
	private EdgeLatch myLatch;
	private Junction myJunction;
	
	public ToggleFlipFlop(Connection theClockLine) {
		myClockLine = theClockLine;
		
		Connection[] dummyGroup = {new Connection()};
		myJunction = new Junction(dummyGroup);
		
		myLatch = new EdgeLatch(myClockLine, myJunction.getOutput(), true);
		Connection[] junctionGroup = {myLatch.getOutputB()};
		myJunction.setInputs(junctionGroup);
	}
	
	public Connection getOutputA() {
		return myLatch.getOutputA();
	}
	
	public Connection getOutputB() {
		return myLatch.getOutputB();
	}
}
