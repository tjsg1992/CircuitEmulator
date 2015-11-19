package main;

import java.util.LinkedList;
import java.util.Queue;

import transistor.Connectable;
import transistor.Connection;

public enum ConnectionManager {
	INSTANCE;

	private static LinkedList<Connectable> nextQueue = new LinkedList<Connectable>();
	private static LinkedList<Connectable> currentQueue = new LinkedList<Connectable>();
	private static boolean readingFlag = false;
	
	public void addRequest(Connectable theConnection) {
		nextQueue.add(theConnection);
	}
	
	public void readRequests() {
		readingFlag = true;
		
		while(currentQueue.size() > 0) {
			Connectable nextConnection = currentQueue.pop();			
			nextConnection.update();		
		}
		
		currentQueue = nextQueue;
		nextQueue = new LinkedList<Connectable>();
		
		if(currentQueue.size() > 0) {
			readRequests();
		}
		
		readingFlag = false;
	}
	
	public boolean isReading() {
		return readingFlag;
	}
	
	private void printStatus() {
		System.out.println("\nManager Status:");
		for(Connectable c : currentQueue) {
			System.out.println(c);
		}
	}
	
}
