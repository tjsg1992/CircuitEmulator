package transistor;

public class ConnectionRequest {
	private Connectable myConnection;
	private int myPriority;
	
	public ConnectionRequest(Connectable theConnection, int priority) {
		myConnection = theConnection;
		myPriority = priority;
	}
	
	public Connectable getConnection() {
		return myConnection;
	}
	
	public int getPriority() {
		return myPriority;
	}
}
