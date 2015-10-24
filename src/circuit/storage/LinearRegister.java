package circuit.storage;

import transistor.Connection;

public class LinearRegister {
	private Connection[] myInputConnections;
	private Connection[] myOutputConnections;
	private Connection myWE;
	
	private GatedDLatch[] myLatches;
	
	public LinearRegister(Connection[] theInputConnections, Connection theWE) {
		this.myInputConnections = theInputConnections;
		myLatches = new GatedDLatch[myInputConnections.length];
		this.myOutputConnections = new Connection[myInputConnections.length];
		myWE = theWE;
		
		for(int i = 0; i < myInputConnections.length; i++) {
			myLatches[i] = new GatedDLatch(myInputConnections[i], myWE);
			myOutputConnections[i] = myLatches[i].getOutputA();
		}
	}
	
	public Connection[] getOutputConnections() {
		return myOutputConnections;
	}
}
