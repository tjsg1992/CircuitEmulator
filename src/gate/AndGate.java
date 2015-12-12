package gate;

import transistor.Connection;

/**
 * An AND Gate has an output of zero unless all of its inputs are one.
 * 
 * @author Taylor Gorman
 * @version 0.2, 10/23/15
 */
public class AndGate extends Gate {
	
	private NandGate myNandGate;
	private NotGate myNotGate;

	/**
	 * Construct the AND Gate with two to many outputs.
	 * @param theInputConnections the input connections
	 */
	public AndGate(Connection[] theInputConnections) {
		super(theInputConnections);
		setupGate();
		update();
	}

	public AndGate(Connection input1, Connection input2) {
		super(input1, input2);
		setupGate();
		update();
	}

	/*
	 * An AND Gate functions by inverting the output of a NAND Gate
	 * with a NOT Gate.
	 */
	private void setupGate() {
		myNandGate = new NandGate(super.getInputConnections());
		myNotGate = new NotGate(myNandGate.getOutput());
		super.setOutputConnection(myNotGate.getOutput());
	}

	@Override
	public void update() {
		myNandGate.update();		
	}

}
