package transistor;

public class ThreadConnection extends Thread {
	private Connection myInputConnection;
	private Connection myOutputConnection;
	private boolean isDelayed;
	private ThreadConnection partner;
	
	public ThreadConnection(Connection theInputConnection) {
		myInputConnection = theInputConnection;
		myOutputConnection = new Connection();
		theInputConnection.addThreadConnection(this);
		isDelayed = false;
	}
	
	public Connection getOutputConnection() {
		return myOutputConnection;
	}
	
	public void update() {
		run();
	}
	
	public void run() {
		System.out.println(this.hashCode() + " Processing");
		if(myInputConnection.hasPower() && !myOutputConnection.hasPower()) {
			myOutputConnection.powerOn();
		} else if (!myInputConnection.hasPower() && myOutputConnection.hasPower()) {
			myOutputConnection.powerOff();
		}
		System.out.println(this.hashCode() + " Resolved");
		
		

	}
	
	public Connection getOutput() {
		return myOutputConnection;
	}
	
	public void setDelay(boolean theDelay) {
		isDelayed = theDelay;
	}
}
