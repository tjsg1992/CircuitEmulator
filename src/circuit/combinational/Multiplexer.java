package circuit.combinational;

import gate.AndGate;
import gate.NotGate;
import gate.OrGate;
import transistor.Connection;

/**
 * A Multiplexer has n "select lines" and n-squared inputs.<br>
 * By setting the select lines, one may select one of the inputs
 * to be output.
 * 
 * @author Taylor Gorman
 * @version 0.2, 10/24/15
 */
public class Multiplexer {
	private Connection[] myInputConnections;
	private Connection[] mySelectionLines;
	
	private AndGate[] myAndGates;
	private OrGate myOutputGate;
	
	private NotGate[] myInverters;
	private Connection[] myInvertedSelections;
	
	private Connection[][] myConnectionGroups;
	
	/**
	 * Construct a Multiplexer
	 * @param theInputs the input lines for the mux to handle
	 * @param theSelectionLines the selection lines to choose which input to connect to the output
	 * @exception IllegalArgumentException if the number of inputs is not equal to the square
	 * of the number of selection lines.
	 */
	public Multiplexer(Connection[] theInputs, Connection[] theSelectionLines) {
		
		//Check that we have the right amount of inputs and selections.
		if(Math.pow(2, theSelectionLines.length) != theInputs.length) {
			throw new IllegalArgumentException();
		}
		
		myInputConnections = theInputs;
		mySelectionLines = theSelectionLines;
		
		//Create an AND gate for each input line.
		//Create a NOT gate for each selection line.
		myAndGates = new AndGate[numInputs()];
		myInverters = new NotGate[numSelects()];
		
		//Each selection line will have an inverse and normal path.
		myInvertedSelections = new Connection[numSelects()];
		
		createInverters();
		setupConnectionGroups();
		setupGates();
	}
	
	
	/*
	 * For each selection line, create an inverted path.
	 */
	private void createInverters() {
		for(int i = 0; i < numSelects(); i++) {
			myInverters[i] = new NotGate(mySelectionLines[i]);
			myInvertedSelections[i] = myInverters[i].getOutput();
		}
	}
	
	/*
	 * For each AND Gate, create a connection group.
	 * Each Connection group will have either the normal or inverted path
	 * of every selection line, and a unique input line.
	 */
	private void setupConnectionGroups() {
		int switchCount = numInputs();
		int switchCountdown;
		boolean invertFlag;
		
		//Create Connection Groups
		myConnectionGroups = 
				new Connection[numInputs()][numSelects() + 1];
		
		//Assign each connection group its unique input line
		for(int k = numInputs() - 1; k >= 0; k--) {
			myConnectionGroups[k][0] = myInputConnections[k];
		}
		
		for(int i = numSelects() - 1; i >= 0; i--) {
			//For each selection line, we will be assigning either its normal
			//or inverted path to every connection group.
			switchCount /= 2;
			switchCountdown = switchCount;
			invertFlag = true;
			
			for(int j = 0; j < numInputs(); j++) {
				/*
				 * The first selection line will use its inverted path for the first half
				 * of groups, and then its normal path for the second half.
				 * 
				 * The second selection line will use its inverted path for half of the first half,
				 * and then its normal path for the second half of the first half, and so on.
				 */
				if(switchCountdown == 0) {
					//Switch to inverse/normal paths
					invertFlag = !invertFlag;
					switchCountdown = switchCount;
				}
				
				if(invertFlag) {
					//Assign the inverted path to the group. We use i + 1, because
					//the input line is assigned to the 0th index.
					myConnectionGroups[j][i + 1] = myInvertedSelections[i];
				} else {
					//Assign the normal path to the group.
					myConnectionGroups[j][i + 1] = mySelectionLines[i];
				}
				
				switchCountdown--;
			}
		}
	}
	
	
	/*
	 * For each of the generated connection groups, attach them
	 * to a new AND Gate. Then, take all of the AND gate outputs
	 * and attach them to our output gate, which is an OR Gate.
	 */
	private void setupGates() {
		
		Connection[] andGateOutputs = new Connection[numInputs()];
		
		for(int i = 0; i < numInputs(); i++) {
			myAndGates[i] = new AndGate(myConnectionGroups[i]);
			andGateOutputs[i] = myAndGates[i].getOutput();
		}
		
		myOutputGate = new OrGate(andGateOutputs);
	}
	
	/**
	 * Get the output of the multiplexer
	 * @return the output connection
	 */
	public Connection getOutput() {
		return myOutputGate.getOutput();
	}
	
	
	//Helper Methods
	
	/*
	 * Return the number of input connections.
	 */
	private int numInputs() {
		return myInputConnections.length;
	}
	
	/*
	 * Return the number of selection lines.
	 */
	private int numSelects() {
		return mySelectionLines.length;
	}
}
