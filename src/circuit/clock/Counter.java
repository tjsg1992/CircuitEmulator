package circuit.clock;

import gate.NotGate;
import circuit.storage.EdgeLatch;
import transistor.Connection;
import transistor.Junction;

public class Counter {
	private int numCounters;
	private Connection myClockLine;
	private NotGate[] myInverters;
	private Junction[] myJunctions;
	private EdgeLatch[] myLatches;
	private Connection[] myOutputConnections;
	
	
	
//	private int numCounters;
//	private Connection myClockLine;
//	private EdgeLatch[] myEdgeLatches;
//	
//	private NotGate myClockInverter;
//	private Connection[] myOutputConnections;
//	private Junction[] myJunctions;
	
	public Counter(Connection theClockLine, int theNumCounters) {
		numCounters = theNumCounters;
		myClockLine = theClockLine;
		myJunctions = new Junction[numCounters];
		myLatches = new EdgeLatch[numCounters];
		myOutputConnections = new Connection[numCounters];
		
		Connection[] dummyGroup = {new Connection()};
		
		for(int i = 0; i < numCounters; i++) {
			myJunctions[i] = new Junction(dummyGroup);
			
			if(i == 0) {
				myLatches[i] = new EdgeLatch(myJunctions[i].getOutput(), myClockLine, true);
			} else {
				myLatches[i] = new EdgeLatch(myJunctions[i].getOutput(), myLatches[i - 1].getOutputA(), true);
			}
			Connection[] junctionConnection = {myLatches[i].getOutputB()};
			myJunctions[i].setInputs(junctionConnection);
			
			myOutputConnections[i] = myJunctions[i].getOutput();
			
		}
	
	
	
	
	
	
	
	
//	public Counter(Connection theClockLine, int theNumCounters) {
//		numCounters = theNumCounters;
//		myClockLine = theClockLine;
//		
//		myEdgeLatches = new EdgeLatch[numCounters - 1];
//		myOutputConnections = new Connection[numCounters];
//		myJunctions = new Junction[numCounters - 1];
//		
//		myClockInverter = new NotGate(myClockLine);		
//		myOutputConnections[0] = myClockInverter.getOutput();
//		setupCounters();
//	}
//	
//	private void setupCounters() {
//		Connection[] dummyGroup = {new Connection()};
//		
//		myJunctions[0] = new Junction(dummyGroup);
//		myEdgeLatches[0] = new EdgeLatch(myJunctions[0].getOutput(), myClockLine, true);
//		
//		Connection[] junctionInput = {myEdgeLatches[0].getOutputB()};
//		myJunctions[0].setInputs(junctionInput);
//		myEdgeLatches[0].getOutputB().addJunction(myJunctions[0]);
//		
//		myOutputConnections[1] = myEdgeLatches[0].getOutputA();
//		
//		for(int i = 1; i < numCounters - 1; i++) {			
//			myJunctions[i] = new Junction(dummyGroup);			
//			myEdgeLatches[i] = new EdgeLatch(myJunctions[i].getOutput(), myEdgeLatches[i - 1].getOutputB(), true);
//			
//			junctionInput[0] = myEdgeLatches[i].getOutputB();
//			myJunctions[i].setInputs(junctionInput);
//			myEdgeLatches[i].getOutputB().addJunction(myJunctions[i]);
//			
//			myOutputConnections[i + 1] = myEdgeLatches[i].getOutputA();
//		}
	}
	
	public Connection[] getOutputConnections() {
		return myOutputConnections;
	}
}
