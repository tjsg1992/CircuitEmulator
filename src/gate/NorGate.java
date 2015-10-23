package gate;

import transistor.Connection;
import transistor.PTypeTransistor;
import transistor.Source;
import transistor.Transistor;

public class NorGate extends Gate {
	
	private Transistor[] myTransistors;

	public NorGate(Connection[] theInputConnections) {
		super(theInputConnections);
		setupGate();
		update();
	}
	
	private void setupGate() {
		myTransistors = new Transistor[myInputConnections.length];
		myTransistors[0] = new PTypeTransistor(new Source(), myInputConnections[0]);
		
		for(int i = 1; i < myTransistors.length; i++) {
			myTransistors[i] = new PTypeTransistor(myTransistors[i - 1].getOutput(), myInputConnections[i]);
		}

		super.myOutputConnection = myTransistors[myTransistors.length - 1].getOutput();
	}

	@Override
	public void update() {
		for(Transistor t : myTransistors) {
			t.update();
		}
	}

}
