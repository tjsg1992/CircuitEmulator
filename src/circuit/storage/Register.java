package circuit.storage;

import transistor.Connection;

/**
 * 
 *
 * @author Taylor Gorman
 * @version 1.0, Dec 19, 2015
 *
 */
public class Register {
	private Connection[] myInputConnections;
	private Connection[] myOutputConnections;
	private Connection myWE;
	
	private GatedDLatch[] myLatches;
	
	public Register(Connection[] theInputConnections, Connection theWE) {
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
