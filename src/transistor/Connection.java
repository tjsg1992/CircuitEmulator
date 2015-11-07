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
public class Connection implements Connectable {
	
	//Class Fields
	
	//True if the connection has power
	private boolean power;
	
	//A Connection may have multiple transistors and junctions that it connects to.
	private ArrayList<Connectable> myOutputConnectables;
	private ThreadConnection myThread;
	private int myThreadDelay;
	
	/**
	 * Construct a Connection that is powered off and with not connected
	 * to any outputs.
	 */
	public Connection() {
		power = false;
		myOutputConnectables = new ArrayList<Connectable>();
		myThread = null;
	}
	
	public void connectOutputTo(Connectable theOtherConnectable) {
		myOutputConnectables.add(theOtherConnectable);
		update();
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
			update();
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
			update();
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
	public void update() {
		if(myOutputConnectables.size() != 0) {
			for(Connectable c : myOutputConnectables) {
				c.update();
			}
		}
		
	}
}
