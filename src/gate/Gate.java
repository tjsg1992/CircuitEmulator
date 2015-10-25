package gate;
import transistor.Connection;

/**
 * A Gate is a basic circuit made from transistors.<br>
 * Gates take either one, or two to many input connections, and use transistors
 * to do some work on the given logic. The result of the logic is then
 * output by either powering its output connection on or off.
 * 
 * @author Taylor Gorman
 * @version 0.2, 10/23/15
 */
public abstract class Gate {
	
	//A Gate can have many input connections, and will build itself to accommodate.
	private Connection[] myInputConnections;
	private Connection myOutputConnection;
	
	/**
	 * Construct a Gate with only a single input, such as a NOT Gate.
	 * @param theInputConnection the sole input connection
	 */
	protected Gate(Connection theInputConnection) {
		myInputConnections = new Connection[1];
		myInputConnections[0] = theInputConnection;
	}

	/**
	 * Construct a Gate with multiple inputs, which includes most Gates.
	 * @param theInputConnections the group of input connections
	 * @exception IllegalArgumentException if less than two inputs are passed
	 */
	protected Gate(Connection[] theInputConnections) {
		if(theInputConnections.length < 2) {
			throw new IllegalArgumentException();
		}
		this.myInputConnections = theInputConnections;
	}
	
	/**
	 * Refresh the Gate, which may update its output.
	 */
	abstract public void update();
	
	/**
	 * Return the Gate's output Connection.
	 * @return the output connection
	 */
	public Connection getOutput() {
		return myOutputConnection;
	}
	
	/**
	 * Return the Gate's input Connections.
	 * @return the input connections
	 */
	protected Connection[] getInputConnections() {
		return myInputConnections;
	}
	
	/**
	 * Set the Gate's output Connection
	 * @param theOutputConnection the output connection to be set
	 */
	public void setOutputConnection(Connection theOutputConnection) {
		this.myOutputConnection = theOutputConnection;
	}
	
	/**
	 * Return the number of input Connections the Gate is connected to
	 * @return the number of connected inputs
	 */
	protected int numOfInputs() {
		return myInputConnections.length;
	}
	
}