package gate;

import transistor.Connection;
import transistor.Junction;
import transistor.PTypeTransistor;
import transistor.Source;
import transistor.Transistor;

/**
 * A NAND Gate has an output of one unless all of its inputs are one.
 * 
 * @author Taylor Gorman
 * @version 0.2, 10/23/15
 */
public class NandGate extends Gate {
	
	private Transistor[] transistors;
	private Junction gateJunction;
	private Source mySource;

	/**
	 * Construct a NAND Gate with two to many inputs.
	 * @param theInputConnections the input connections
	 */
	public NandGate(Connection[] theInputConnections) {
		super(theInputConnections);
		mySource = new Source();
		setupGate();
		update();
	}
	
	/*
	 * Setup the contents of the Gate:
	 * The gate will have a variable amount of p-type transistors, equal to the total number of inputs.
	 * Each transistor's input is connected to one of the input connections.
	 * 
	 * All of the transistors are powered by a Source, and all of their outputs are combined into
	 * the same Junction.
	 * 
	 * The output of the Junction is the output of the gate.
	 */
	private void setupGate() {
		
		//Create a group of transistors.
		transistors = new Transistor[super.numOfInputs()];
		
		for(int i = 0; i < super.numOfInputs(); i++) {
			transistors[i] = new PTypeTransistor(mySource, super.getInputConnections()[i]);
		}
		
		
		//Connect the transistors to a junction.
		Connection[] transistorOutputs = new Connection[transistors.length];
		
		for(int i = 0; i < transistors.length; i++) {
			transistorOutputs[i] = transistors[i].getOutput(); //Collects outputs
		}

		gateJunction = new Junction(transistorOutputs); //Creates junction with all outputs
		
		
		for(int i = 0; i < transistors.length; i ++) {
			transistors[i].getOutput().addJunction(gateJunction); //Connects junction to each output
		}
		
		
		//Set the junction's output to be the gate's output
		super.setOutputConnection(gateJunction.getOutput());
		}
	
	
	public void update() {
		for(Transistor t : transistors) {
			t.update();
		}
	}

}
