package gate;

import transistor.Connection;
import transistor.PTypeTransistor;
import transistor.Source;
import transistor.Transistor;

public class NorGate extends Gate {
	
	private Transistor myTransistorA;
	private Transistor myTransistorB;

	public NorGate(Connection[] theInputConnections) {
		super(theInputConnections);
		setupGate();
		update();
	}
	
	private void setupGate() {
		myTransistorA = new PTypeTransistor(new Source(), myInputConnections[0]);
		myTransistorB = new PTypeTransistor(myTransistorA.getOutput(), myInputConnections[1]);
		
		super.myOutputConnection = myTransistorB.getOutput();
	}

	@Override
	public void update() {
		myTransistorA.update();
		myTransistorB.update();
	}

}
