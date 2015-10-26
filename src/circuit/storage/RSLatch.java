package circuit.storage;

import gate.NandGate;
import transistor.Connection;
import transistor.Junction;

/**
 * An R-S Latch is a basic form of storage capable
 * of containing one bit.<br>
 * By clearing one of its inputs, its outputs will change, and will
 * remain the same until the other input has been cleared.
 * 
 * @author Taylor Gorman
 * @version 0.2, 10/24/15
 */
public class RSLatch {
	
	private Connection inputS, inputR;
	private Connection outputA, outputB;
	private NandGate gateS, gateR;
	
	private Junction junctionA, junctionB;
	
	/**
	 * Construct a new R-S Latch from two input connections, R and S
	 * @param S an input connection
	 * @param R an input connection
	 */
	public RSLatch(Connection S, Connection R) {
		this.inputS = S;
		this.inputR = R;
		setupLatch();
	}
	
	private void setupLatch() {
		
		//Create two junctions, points A and B
		junctionA = new Junction(new Connection[0]);
		junctionB = new Junction(new Connection[0]);
		
		//Create two gates, using R/S and a junction as inputs.
		Connection[] gateSInputs = new Connection[2];
		gateSInputs[0] = inputS;
		gateSInputs[1] = junctionB.getOutput();
		
		Connection[] gateRInputs = new Connection[2];
		gateRInputs[0] = inputR;
		gateRInputs[1] = junctionA.getOutput();
		
		gateS = new NandGate(gateSInputs);
		gateR = new NandGate(gateRInputs);
		
		//Link each Gate's output to the junction used for the other gate's input
		Connection[] junctionAInputs = {gateS.getOutput()};
		Connection[] junctionBInputs = {gateR.getOutput()};
		
		junctionA.setInputs(junctionAInputs);
		gateS.getOutput().addJunction(junctionA);
		
		junctionB.setInputs(junctionBInputs);
		gateR.getOutput().addJunction(junctionB);
		
		//Set each junction to be an output of the latch
		outputA = junctionA.getOutput();
		outputB = junctionB.getOutput();
		
		outputB.powerOn();
	}
	
	/**
	 * Return Output A of the latch
	 * @return output A
	 */
	public Connection getOutputA() {
		return outputA;
	}
	
	/**
	 * Return Output B of the latch
	 * @return output B
	 */
	public Connection getOutputB() {
		return outputB;
	}
	
	
}
