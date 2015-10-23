package gate;

import transistor.Connection;
import transistor.Junction;
import transistor.PTypeTransistor;
import transistor.Source;
import transistor.Transistor;

public class NandGate extends Gate {
	
	private Transistor myTransistorA;
	private Transistor myTransistorB;
	private Junction junctionAandB;

	public NandGate(Connection[] theInputConnections) {
		super(theInputConnections);
		setupGate();
		update();
	}
	
	private void setupGate() {
		myTransistorA = new PTypeTransistor(new Source(), myInputConnections[0]);
		myTransistorB = new PTypeTransistor(new Source(), myInputConnections[1]);
		
		Connection[] junctions = new Connection[2];
		junctions[0] = myTransistorA.getOutput();
		junctions[1] = myTransistorB.getOutput();
		junctionAandB = new Junction(junctions);
		
		myTransistorA.getOutput().addJunction(junctionAandB);
		myTransistorB.getOutput().addJunction(junctionAandB);
		
		super.myOutputConnection = junctionAandB.getOutput();
	}
	
	public void update() {
		myTransistorA.update();
		myTransistorB.update();
	}

}
