package gate;

import transistor.Connection;
import transistor.Junction;
import transistor.PTypeTransistor;
import transistor.Source;
import transistor.Transistor;

public class NandGate extends Gate {
	
	private Transistor[] transistors;
	private Junction gateJunction;

	public NandGate(Connection[] theInputConnections) {
		super(theInputConnections);
		setupGate();
		update();
	}
	
	private void setupGate() {
		transistors = new Transistor[myInputConnections.length];
		
		for(int i = 0; i < myInputConnections.length; i++) {
			transistors[i] = new PTypeTransistor(new Source(), myInputConnections[i]);
		}
		
		Connection[] junctions = new Connection[transistors.length];
		
		for(int i = 0; i < transistors.length; i++) {
			junctions[i] = transistors[i].getOutput();
		}

		gateJunction = new Junction(junctions);
		
		for(int i = 0; i < transistors.length; i ++) {
			transistors[i].getOutput().addJunction(gateJunction);
		}
		
		super.myOutputConnection = gateJunction.getOutput();
	}
	
	public void update() {
		for(Transistor t : transistors) {
			t.update();
		}
	}

}
