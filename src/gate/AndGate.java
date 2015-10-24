package gate;

import transistor.Connection;

public class AndGate extends Gate {
	
	private NandGate myNandGate;
	private NotGate myNotGate;

	public AndGate(Connection[] theInputConnections) {
		super(theInputConnections);
		setupGate();
		update();
	}
	
	private void setupGate() {
		myNandGate = new NandGate(super.getInputConnections());
		myNotGate = new NotGate(myNandGate.getOutput());
		super.setOutputConnection(myNotGate.getOutput());
	}

	@Override
	public void update() {
		myNandGate.update();		
	}

}
