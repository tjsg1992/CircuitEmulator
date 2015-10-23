package transistor;

public abstract class Transistor {
	
	protected Connection myPowerConnection;
	protected Connection myInputConnection;	
	protected Connection myOutputConnection;
	
	public Transistor(Connection thePowerConnection, Connection theInputConnection) {
		this.myPowerConnection = thePowerConnection;
		
		this.myInputConnection = theInputConnection;
		
		this.myOutputConnection = new Connection();
		update();
	}
	
	public Connection getOutput() {
		return myOutputConnection;		
	}
	
	public boolean hasPower() {
		return myPowerConnection.hasPower();
	}
	
	public abstract void update();
	
}
