package circuit.combinational;

import gate.AndGate;
import gate.NotGate;
import transistor.Connection;

/**
 * A Decoder interprets a bit pattern.<br>
 * Given a sequence of bits, a decoder will assert a unique output
 * based on what that sequence is.
 * 
 * @author Taylor Gorman
 * @version 0.2, 10/24/15
 */
public class Decoder {
	
	private Connection[] myOutputConnections;	
	private int numInputsSquared;
	
	/**
	 * Construct a Decoder from one to many input connections.<br>
	 * The Decoder will have a number of outputs equal to the square of
	 * the number of inputs.
	 * @param theInputConnections the input connections
	 * @exception if an input is not passed
	 */
	public Decoder(Connection[] theInputConnections) {
		if(theInputConnections.length < 1) {
			throw new IllegalArgumentException();
		}
		numInputsSquared = (int) Math.pow(2, theInputConnections.length);
		
		//Each input has its own inverter, of which half of its paths go through.
		//The inverted half of paths are collected here.
		Connection[] inputInverts = getInvertedConnections(theInputConnections);
		
		//Normal and inverted paths are then put into various groups, such that
		//each group has one of either for each input, and whose assortment is unique.
		Connection[][] connectionGroups = groupConnections(theInputConnections, inputInverts);
		
		//The groups of connections are then connected to AND gates.
		myOutputConnections = setupGates(connectionGroups);
	}
	
	/*
	 * For each connection, create a NOT gate, and connect it to it.
	 * Then, store the NOT gate's output to an array of inverted connections.
	 * Return the array of inverted connections.
	 */
	private Connection[] getInvertedConnections(Connection[] theConnections) {
		Connection[] invertedConnections = new Connection[theConnections.length];
		
		for(int i = 0; i < theConnections.length; i++) {
			NotGate inverter = new NotGate(theConnections[i]);
			invertedConnections[i] = inverter.getOutput();
		}
		
		return invertedConnections;
	}
	
	/*
	 * Create groups of connections whose number equals the total number of connections squared.
	 * Each group will contain either a normal or inverted path of each connection.
	 * Each group's collection of normal/inverted paths will lead to a unique assortment.
	 */
	private Connection[][] groupConnections(Connection[] theInputConnections, Connection[] theInputInverts) {
		int switchCount = numInputsSquared;
		int switchCountdown;
		boolean invertFlag;
		
		//We have input squared connection groups, each with input-number connections.
		Connection[][] connectionGroups = new Connection[numInputsSquared][theInputConnections.length];
		
		/*
		 * The first input will contribute its inverse to the first half of groups.
		 * It will then contribute its normal path to the second half of groups.
		 * 
		 * The second input will contribute its inverse to half of the first half of groups.
		 * It will then contribute its normal path to the second half of the first half of groups.
		 * And so on, with each successive input halving the number of groups before it switches.
		 */
		for(int i = 0; i < theInputConnections.length; i++) {
			//For each connection, we will connect it to a squared number of groups.
			switchCount /= 2;
			switchCountdown = switchCount;
			invertFlag = true;
			
			for(int j = 0; j < numInputsSquared; j++) {
				if(switchCountdown == 0) {
					//Switch from inverted to normal, or normal to inverted
					invertFlag = !invertFlag;
					switchCountdown = switchCount;
				}
				
				if(invertFlag) {
					connectionGroups[j][i] = theInputInverts[i]; //Connect inverted path
				} else {
					connectionGroups[j][i] = theInputConnections[i]; //Connect normal path
				}
				
				switchCountdown--;
			}
		}
		
		return connectionGroups;
		
	}
	
	/*
	 * Connect each of the connection groups to an AND Gate,
	 * and return the group of AND Gate outputs.
	 */
	private Connection[] setupGates(Connection[][] theConnectionGroups) {
		Connection[] gateOutputs = new Connection[theConnectionGroups.length];
		
		for(int i = 0; i < theConnectionGroups.length; i++) {
			AndGate andGate = new AndGate(theConnectionGroups[i]);
			gateOutputs[i] = andGate.getOutput();
		}		
		
		return gateOutputs;
	}
	
	/**
	 * Return the output connections for the Decoder.
	 * @return the output connections
	 */
	public Connection[] getOutputConnections() {
		return myOutputConnections;
	}
}
