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
		myTransistors = new Transistor[super.numOfInputs()];
		myTransistors[0] = new PTypeTransistor(mySource, super.getInputConnections()[0]);
		
		for(int i = 1; i < myTransistors.length; i++) {
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
