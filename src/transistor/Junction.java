package transistor;

public class Junction {
	private Connection[] inputConnections;
	private Connection outputConnection;
	
	public Junction(Connection[] theInputConnections) {
		this.inputConnections = theInputConnections;
		outputConnection = new Connection();
		update();
	}
	
	public void update() {
		boolean connectionActive = false;
		
		for(Connection c : inputConnections) {
			if(c.hasPower()) connectionActive = true;
		}
		
		if(connectionActive) {
			outputConnection.powerOn();
		} else {
			outputConnection.powerOff();
		}
	}
	
	public Connection getOutput() {
		return outputConnection;
	}
}
