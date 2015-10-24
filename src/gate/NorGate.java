package gate;

import transistor.Connection;
import transistor.PTypeTransistor;
import transistor.Source;
import transistor.Transistor;

/**
 * A NOR Gate has an output of zero unless all of its inputs are also zero.
 * 
 * @author Taylor Gorman
 * @version 0.2, 10/23/15
 */
public class NorGate extends Gate {
	
	private Transistor[] myTransistors;
	private Source mySource;

	/**
	 * Construct a NOR Gate with two to many inputs.
	 * @param theInputConnections the input connections
	 */
	public NorGate(Connection[] theInputConnections) {
		super(theInputConnections);
		mySource = new Source();
		setupGate();
		update();
	}
	
	/*
	 * Setup the contents of the gate:
	 * The gate will have a variable amount of p-type transistors, equal to the total number of inputs.
	 * Each transistor's input is connected to one of the input connections.
	 * 
	 * Then, each transistor's output is connected to the power of the next transistor, with the
	 * first transistor connected to a Source.
	 * 
	 * The output of the final transistor in the series is the output of the gate.
	 */
	private void setupGate() {
		myTransistors = new Transistor[super.numOfInputs()];

		myTransistors[0] = new PTypeTransistor(mySource, super.getInputConnections()[0]);
		
		for(int i = 1; i < myTransistors.length; i++) {
			//Create a new p-type transistor, whose power is the output of the previous transistor.
			myTransistors[i] = new PTypeTransistor(myTransistors[i - 1].getOutput(), super.getInputConnections()[i]);
		}

		super.setOutputConnection(myTransistors[myTransistors.length - 1].getOutput());
	}

	@Override
	public void update() {
		for(Transistor t : myTransistors) {
			t.update();
		}
	}

}
