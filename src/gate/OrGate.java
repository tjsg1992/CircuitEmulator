package gate;

import transistor.Connection;

public class OrGate extends Gate {
	
	private NorGate myNorGate;
	private NotGate myNotGate;

	public OrGate(Connection[] theInputConnections) {
		super(theInputConnections);
		setupGate();
		update();
	}
	
	private void setupGate() {
		myNorGate = new NorGate(super.getInputConnections());
		myNotGate = new NotGate(myNorGate.getOutput());
		super.setOutputConnection(myNotGate.getOutput());
	}

	@Override
	public void update() {
		myNorGate.update();		
	}

}
