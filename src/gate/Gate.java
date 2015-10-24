package gate;
import transistor.Connection;

public abstract class Gate {
	
	private Connection[] myInputConnections;
	private Connection myOutputConnection;
	
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
	
	protected Connection[] getInputConnections() {
		return myInputConnections;
	}
	
	protected Connection getOutputConnection() {
		return myOutputConnection;
	}
	
	protected void setInputConnections(Connection[] theInputConnections) {
		this.myInputConnections = theInputConnections;
	}
	
	protected void setOutputConnection(Connection theOutputConnection) {
		this.myOutputConnection = theOutputConnection;
	}
	
	protected int numOfInputs() {
		return myInputConnections.length;
	}
	
}