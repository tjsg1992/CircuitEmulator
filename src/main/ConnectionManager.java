package main;

import java.util.LinkedList;
import transistor.Connectable;

/**
 * Manages connection update requests in order to allow synchronization and
 * simultaneousness without having to utilize threads.<br>
 * Utilizes an enum-type singleton implementation.
 *
 * @author Taylor Gorman
 * @version 1.0, Dec 19, 2015
 */
public enum ConnectionManager {
	INSTANCE;
	
	/*
	 * Uses two separate queues; one for the current requests and one for the next set.
	 * New requests are added to nextQueue, and nextQueue is set as currentQueue once
	 * currentQueue has been emptied.
	 */	
	private static LinkedList<Connectable> currentQueue = new LinkedList<Connectable>();
	private static LinkedList<Connectable> nextQueue = new LinkedList<Connectable>();
	private static boolean readingFlag = false;
	
	/**
	 * Add a connection update request to the Connection Manager.
	 * @param theConnection the next connection to be updated.
	 */
	public void addRequest(Connectable theConnection) {
		nextQueue.add(theConnection);
	}
	
	/**
	 * Request the Connection Manager to read through the current set of requests.<br>
	 * If the Connection Manager is already reading, it will return.
	 */
	public void readRequests() {
		if(readingFlag) return;		
		readingFlag = true;		
		
		//Update each connection in the queue.
		while(currentQueue.size() > 0) {
			Connectable nextConnection = currentQueue.pop();			
			nextConnection.update();		
		}
		
		//Reset the queues and finish reading.
		currentQueue = nextQueue;
		nextQueue = new LinkedList<Connectable>();
		readingFlag = false;
		
		//Updating connections will likely have led to more update requests.
		if(currentQueue.size() > 0) {
			readRequests();
		}
	}
}
