package transistor;

public class ConnectionRequest {
	private Connection myConnection;
	private boolean isPoweringOn;
	
	public ConnectionRequest(Connection theConnection, boolean poweringOn) {
		myConnection = theConnection;
		isPoweringOn = poweringOn;
	}
	
	public Connection getConnection() {
		return myConnection;
	}
	
	public boolean isPoweringOn() {
		return isPoweringOn;
	}
}
