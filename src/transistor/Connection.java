package transistor;

import java.util.ArrayList;

/**
 * A Connection is a wire that is either on or off.
 * A single Connection is most often used by two circuit structures.
 * By using Connections, we can keep circuit structures completely modular.
 * 
 * @author Taylor Gorman
 * @version 0.2, 10/23/15
 */
public class Connection {
	
	//Class Fields
	
	//True if the connection has power
	private boolean power;
	
	//A Connection may have multiple transistors and junctions that it connects to.
	private ArrayList<Transistor> myOutputTransistors;
	private ArrayList<Junction> myJunctions;
	private ThreadConnection myThread;
	private int myThreadDelay;
	
	/**
	 * Construct a Connection that is powered off and with not connected
	 * to any outputs.
	 */
	public Connection() {
		power = false;
		myOutputTransistors = new ArrayList<Transistor>();
		myJunctions = new ArrayList<Junction>();
		myThread = null;
	}
	


	/**
	 * Adds the specified Junction to the Connections list of
	 * output Junctions it is connected to.<br>
	 * Updates the Connection's outputs afterwards, which may or may
	 * not power on the newly connected Junction.
	 * @param theJunction The Junction to connect the Connection to
	 */
	public void addJunction(Junction theJunction) {
		this.myJunctions.add(theJunction);
		updateOutputs();
	}
	
	/**
	 * Adds the specified Transistor to the Connections list of
	 * output Transistors it is connected to.<br>
	 * The Connection's outputs will be updated afterward.
	 * @param theOutputTransistor The Transistor to connect the Connection to
	 */
	public void addOutputTransistor(Transistor theOutputTransistor) {
		this.myOutputTransistors.add(theOutputTransistor);
		updateOutputs();
	}
	
	public void initializeThread(int theDelay) {
		myThreadDelay = theDelay;
		myThread = new ThreadConnection(this, myThreadDelay);
		myThread.start();
	}


	/**
	 * Power on the Connection.
	 */
	public void powerOn() {
		if(power) return; //Already powered. Return to stop update chain.
		power = true;
		
		if(myThread != null) {
			initializeThread(myThreadDelay);
		} else {
			updateOutputs();
		}	
		
	}
	
	/**
	 * Power off the Connection.
	 */
	public void powerOff() {
		if(!power) return; //Already unpowered. Return to stop update chain.
		
		power = false;
		
		if(myThread != null) {
			initializeThread(myThreadDelay);
		} else {
			updateOutputs();
		}	
	}
	



	/**
	 * Return whether or not the Connection is powered.
	 * @return true if the Connection has power; false otherwise
	 */
	public boolean hasPower() {
		return power;
	}
	
	
	/*
	 * Update the output junctions and transistors that the Connection
	 * is connected to
	 */
	public void updateOutputs() {
		if(myJunctions.size() != 0) {
			for(Junction j : myJunctions) {
				j.update();
			}
		}
		
		if(myOutputTransistors.size() != 0) {
			for(Transistor t : myOutputTransistors) {
				t.update();
			}
		}
	}
}
