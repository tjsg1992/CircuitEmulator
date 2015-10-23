package gate;

import transistor.Connection;
import transistor.PTypeTransistor;
import transistor.Source;
import transistor.Transistor;

public class NorGate extends Gate {
	
	private Transistor[] myTransistors;
	private Source mySource;

	public NorGate(Connection[] theInputConnections) {
		super(theInputConnections);
		mySource = new Source();
		setupGate();
		update();
	}
	
	private void setupGate() {
		myTransistors = new Transistor[myInputConnections.length];
		myTransistors[0] = new PTypeTransistor(mySource, myInputConnections[0]);
		
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
