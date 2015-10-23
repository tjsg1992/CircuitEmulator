package gate;
import transistor.Connection;

public abstract class Gate {
	
	protected Connection[] myInputConnections;
	protected Connection myOutputConnection;
	
	public Gate(Connection theInputConnection) {
		myInputConnections = new Connection[1];
		myInputConnections[0] = theInputConnection;
	}

	public Gate(Connection[] theInputConnections) {
		this.myInputConnections = theInputConnections;
	}
	
	abstract public void update();
	
	public Connection getOutput() {
		return myOutputConnection;
	}
	
}