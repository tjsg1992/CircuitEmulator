package circuit.combinational;

import gate.AndGate;
import gate.NotGate;
import transistor.Connection;

public class Multiplexer {
	private Connection[] myOutputConnections;
	
	private int connectionsByTwo;
	
	public Multiplexer(Connection[] theInputConnections) {
		connectionsByTwo = (int) Math.pow(2, theInputConnections.length);
		
		Connection[] inputInverts = getInvertedConnections(theInputConnections);
		Connection[][] connectionGroups = groupConnections(theInputConnections, inputInverts);
		myOutputConnections = setupGates(connectionGroups);
	}
	
	
	private Connection[] getInvertedConnections(Connection[] theConnections) {
		Connection[] invertedConnections = new Connection[theConnections.length];
		
		for(int i = 0; i < theConnections.length; i++) {
			NotGate inverter = new NotGate(theConnections[i]);
			invertedConnections[i] = inverter.getOutput();
		}
		
		return invertedConnections;
	}
	
	private Connection[][] groupConnections(Connection[] theInputConnections, Connection[] theInputInverts) {
		int switchCount = connectionsByTwo;
		int switchCountdown;
		boolean invertFlag;
		
		Connection[][] connectionGroups = new Connection[connectionsByTwo][theInputConnections.length];
		
		for(int i = 0; i < theInputConnections.length; i++) {
			switchCount /= 2;
			switchCountdown = switchCount;
			invertFlag = true;
			
			for(int j = 0; j < connectionsByTwo; j++) {
				if(switchCountdown == 0) {
					invertFlag = !invertFlag;
					switchCountdown = switchCount;
				}
				
				if(invertFlag) {
					connectionGroups[j][i] = theInputInverts[i];
				} else {
					connectionGroups[j][i] = theInputConnections[i];
				}
				
				switchCountdown--;
			}
		}
		
		return connectionGroups;
		
	}
	
	private Connection[] setupGates(Connection[][] theConnectionGroups) {
		Connection[] gateOutputs = new Connection[theConnectionGroups.length];
		
		for(int i = 0; i < theConnectionGroups.length; i++) {
			AndGate andGate = new AndGate(theConnectionGroups[i]);
			gateOutputs[i] = andGate.getOutput();
		}		
		
		return gateOutputs;
	}
	
	public Connection[] getOutputConnections() {
		return myOutputConnections;
	}
}
