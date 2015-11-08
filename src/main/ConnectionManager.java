package main;

import java.util.LinkedList;
import java.util.Queue;

import transistor.Connection;
import transistor.ConnectionRequest;

public enum ConnectionManager {
	INSTANCE;

	private static LinkedList<ConnectionRequest> connectionQueue = new LinkedList<ConnectionRequest>();
	private static boolean isReading = false;
	private static ConnectionRequest currentRequest;
	
	public void addRequest(Connection theConnection, boolean poweringOn) {
		ConnectionRequest newRequest = new ConnectionRequest(theConnection, poweringOn);
		connectionQueue.add(newRequest);
		
		if(!isReading) {
			readRequests();
		}
	}
	
	public void readRequests() {
		isReading = true;
		
		while(connectionQueue.size() > 0) {
			currentRequest = connectionQueue.get(0);
			if(currentRequest.isPoweringOn()) {
				currentRequest.getConnection().powerOn();
			} else {
				currentRequest.getConnection().powerOff();
			}
			
			connectionQueue.remove(0);
		}
		
		isReading = false;
	}
	
	
	
}
