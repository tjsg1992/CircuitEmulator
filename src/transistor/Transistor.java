package transistor;

public abstract class Transistor {
	
	protected Connection myInputConnection;	
	protected Connection myOutputConnection;
	
	public Transistor(Connection theInputConnection) {
		this.myInputConnection = theInputConnection;
		this.myOutputConnection = new Connection();
		update();
	}
	
	public Connection getOutput() {
		return myOutputConnection;		
	}
	
	public abstract void update();
	
}
