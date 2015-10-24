package transistor;

/**
 * A Transistor is a very simple circuit.<br>
 * A Transistor needs a power connection and an input connection.<br>
 * If the power connection is on, then depending on the type of transistor,
 * the output connection will be either on or off.<br>
 * However, if the transistor is not powered, then its output will always
 * be off.
 * 
 * @author Taylor Gorman
 * @version 0.2, 10/23/15
 */
public abstract class Transistor {
	
	private Connection myPowerConnection;
	private Connection myInputConnection;	
	private Connection myOutputConnection;
	
	/**
	 * Construct a Transistor that is linked to power, input, and
	 * output Connections.
	 * @param thePowerConnection The connection that provides power
	 * @param theInputConnection The input connection that the Transistor works on.
	 */
	public Transistor(Connection thePowerConnection, Connection theInputConnection) {
		this.myPowerConnection = thePowerConnection;
		this.myInputConnection = theInputConnection;
		this.myOutputConnection = new Connection();
		update();
	}
	
	/**
	 * Return the Transistor's output Connection.
	 * @return the output Connection
	 */
	public Connection getOutput() {
		return myOutputConnection;		
	}
	
	/**
	 * Return whether or not the Transistor is powered.
	 * @return true if the Transistor is powered; false otherwise
	 */
	public boolean hasPower() {
		return myPowerConnection.hasPower();
	}
	
	/**
	 * Update the transistor, which updates its outputs.
	 */
	public abstract void update();

	protected Connection getPowerConnection() {
		return myPowerConnection;
	}

	protected Connection getInputConnection() {
		return myInputConnection;
	}

	protected Connection getOutputConnection() {
		return myOutputConnection;
	}
	
}
