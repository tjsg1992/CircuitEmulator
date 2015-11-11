package main;

import java.util.LinkedList;
import java.util.Queue;

import transistor.Connectable;
import transistor.Connection;
import transistor.ConnectionRequest;

public enum ConnectionManager {
	INSTANCE;

	private static LinkedList<ConnectionRequest> connectionQueue = new LinkedList<ConnectionRequest>();
	private static boolean readingFlag = false;
	private static int writingPriority = 0;
	private static int readingPriority = 0;
	
	public void addRequest(Connectable theConnection) {
		ConnectionRequest newRequest = new ConnectionRequest(theConnection, writingPriority);
		connectionQueue.add(newRequest);
	}
	
	public void readRequests() {
		
//		if(connectionQueue.size() > 0) {
//			printStatus();
//		}
		
		writingPriority++;
		readingFlag = true;
		while(connectionQueue.size() > 0 && connectionQueue.peek().getPriority() <= readingPriority) {
			Connectable nextConnection = connectionQueue.pop().getConnection();
			
			if(connectionQueue.size() <= 0 || connectionQueue.peek().getPriority() > readingPriority) {
				readingFlag = false;
			}
			
			nextConnection.update();		
		}
		readingFlag = false;
		readingPriority++;
	}
	
	public void increaseReadingPriority() {
		readingPriority++;
	}
	
	public boolean isReading() {
		return readingFlag;
	}
	
	private void printStatus() {
		System.out.println("\nManager Status:");
		for(ConnectionRequest c : connectionQueue) {
			System.out.println(c.getConnection() + " : " + c.getPriority());
		}
	}
	
}
