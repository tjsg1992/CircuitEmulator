package gate;

import transistor.Connection;
import transistor.PTypeTransistor;
import transistor.Source;
import transistor.Transistor;

/**
 * A NOT Gate is an inverter, and essentially a wrapper for a p-type transistor.<br>
 * When a NOT Gate's input is on, it's output is off; 
 * if it's input is off, it's output is on.
 * 
 * @author Taylor Gorman
 * @version 0.2, 10/23/15
 */
public class NotGate extends Gate {
	
	private Transistor myTransistor;
	
	/**
	 * Construct a NOT Gate. Unlike other Gates, NOT Gates can
	 * only connect to one input.
	 * @param theInputConnection the input of the Gate
	 */
	public NotGate(Connection theInputConnection) {
		super(theInputConnection);
		setupGate();
		update();
	}

	/*
	 * Setup the contents of the gate:
	 * Create a p-type transistor and connect it to the input connection.
	 * The p-type's output is the output connection of the gate.
	 * In this way, the NOT Gate is essentially a wrapper for a p-type transistor.
	 */
	private void setupGate() {
		myTransistor = new PTypeTransistor(new Source(), super.getInputConnections()[0]);
		super.setOutputConnection(myTransistor.getOutput());
	}
	
	@Override
	public void update() {
		myTransistor.update();
	}
}