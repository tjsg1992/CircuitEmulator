package gate;

import transistor.Connection;
import transistor.PTypeTransistor;
import transistor.Source;
import transistor.Transistor;

public class NotGate extends Gate {
	
	private Transistor myTransistor;
	
	public NotGate(Connection theInputConnection) {
		super(theInputConnection);		
		setupGate();
		update();
	}

	private void setupGate() {
		myTransistor = new PTypeTransistor(new Source(), myInputConnections[0]);
		super.myOutputConnection = myTransistor.getOutput();
	}
	
	@Override
	public void update() {
		myTransistor.update();
	}
}