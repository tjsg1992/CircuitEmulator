package transistor;

/**
 * A Junction is a link between multiple Connections.<br>
 * A Junction has one to many connection inputs,
 * and a single connection output.<br>
 * The connection output is powered if any of the connection
 * inputs are powered.
 * 
 * @author Taylor Gorman
 * @version 0.2, 10/23/15
 */
public class Junction {
	private Connection[] inputConnections;
	private Connection outputConnection;
	
	/**
	 * Construct a Junction that links a number of Connection inputs.<br>
	 * Update its output connection upon construction.
	 * @param theInputConnections An array of Connections.
	 */
	public Junction(Connection[] theInputConnections) {
		this.inputConnections = theInputConnections;
		outputConnection = new Connection();
		update();
	}
	
	/**
	 * Power on the output Connection if any of the input
	 * Connections have power.
	 */
	public void update() {
		boolean connectionActive = false;
		
		//Check if any of the inputs are powered.
		for(Connection c : inputConnections) {
			if(c.hasPower()) connectionActive = true;
		}
		
		//Power the output if an input is powered.
		if(connectionActive) {
			outputConnection.powerOn();
		} else {
			outputConnection.powerOff();
		}
	}
	
	/**
	 * Return the output Connection of the Junction.
	 * @return the output Connection
	 */
	public Connection getOutput() {
		return outputConnection;
	}
	
	public void setInputs(Connection[] theInputs) {
		this.inputConnections = theInputs;
		update();
	}
}
