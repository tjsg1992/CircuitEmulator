package gate;

import transistor.Connection;

/**
 * An OR Gate has an output of one unless all of its inputs are zero.
 * 
 * @author Taylor Gorman
 * @version 0.2, 10/24/15
 */
public class OrGate extends Gate {
	
	private NorGate myNorGate;
	private NotGate myNotGate;

	/**
	 * Construct the OR Gate with two to many outputs.
	 * @param theInputConnections the input connections
	 */
	public OrGate(Connection[] theInputConnections) {
		super(theInputConnections);
		setupGate();
		update();
	}
	
	/*
	 * An OR Gate functions by inverting the output of a NOR Gate
	 * with a NOT Gate.
	 */
	private void setupGate() {
		myNorGate = new NorGate(super.getInputConnections());
		myNotGate = new NotGate(myNorGate.getOutput());
		super.setOutputConnection(myNotGate.getOutput());
	}

	@Override
	public void update() {
		myNorGate.update();		
	}

}
