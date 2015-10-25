package circuit.storage;

import gate.NandGate;
import gate.NotGate;
import transistor.Connection;

public class GatedDLatch {
	private Connection myConnectionD;
	private Connection myConnectionWE;
	private RSLatch myRSLatch;
	
	public GatedDLatch(Connection D, Connection WE) {
		this.myConnectionD = D;
		this.myConnectionWE = WE;
		
		setupGates();
	}
	
	private void setupGates() {
		NotGate dInverter = new NotGate(myConnectionD);
		Connection[] sConnections = {myConnectionD, myConnectionWE};
		Connection[] rConnections = {dInverter.getOutput(), myConnectionWE};
		
		NandGate nandGateS = new NandGate(sConnections);
		NandGate nandGateR = new NandGate(rConnections);
		
		myRSLatch = new RSLatch(nandGateS.getOutput(), nandGateR.getOutput());
	}
	
	public Connection getOutputA() {
		return myRSLatch.getOutputA();
	}
	
	public Connection getOutputB() {
		return myRSLatch.getOutputB();
	}
}