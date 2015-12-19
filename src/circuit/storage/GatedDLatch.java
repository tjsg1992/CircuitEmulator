package circuit.storage;

import gate.NandGate;
import gate.NotGate;
import transistor.Connection;
import transistor.Junction;

/**
 * A wrapper for the RSLatch that also utilizes a write-enable line
 * and only one input line.
 *
 * @author Taylor Gorman
 * @version 1.0, Dec 19, 2015
 */
public class GatedDLatch {
	private Connection myConnectionD;
	private Connection myConnectionWE;
	private RSLatch myRSLatch;
	
	/**
	 * Construct a new Gated-D Latch.
	 * @param D the input line
	 * @param WE the write-enable line
	 */
	public GatedDLatch(Connection D, Connection WE) {
		this.myConnectionD = D;
		this.myConnectionWE = WE;
		setupGates();
	}
	
	/*
	 * Set up the Gated-D Latch, which involves connecting the D and WE lines
	 * to the RS-Latch.
	 */
	private void setupGates() {
		NotGate dInverter = new NotGate(myConnectionD);
		Junction dExtender = new Junction(myConnectionD); //Synchronization
		Connection[] sConnections = {dExtender.getOutput(), myConnectionWE};
		Connection[] rConnections = {dInverter.getOutput(), myConnectionWE};
		
		NandGate nandGateS = new NandGate(sConnections);
		NandGate nandGateR = new NandGate(rConnections);
		
		myRSLatch = new RSLatch(nandGateS.getOutput(), nandGateR.getOutput());
	}
	
	/**
	 * Get Output A of the latch.
	 * @return output A
	 */
	public Connection getOutputA() {
		return myRSLatch.getOutputA();
	}
	
	/**
	 * Get Output B of the latch.
	 * @return output B
	 */
	public Connection getOutputB() {
		return myRSLatch.getOutputB();
	}
}
