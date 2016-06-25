package circuit.combinational;

import gate.AndGate;
import transistor.Connection;

/*
 * A tristate buffer is difficult to implement due to its use of a high impendance state,
 * which is done through hardware.
 * Therefore, we have a workaround solution where we use an AND gate between each
 * input connection and the enabled line. This simulates the buffer becoming "open"
 * if the enabled line is off.
 */

/**
 * A tristate buffer can be thought of as a switch. <br>
 * If it is enabled, then the switch is closed; if it is disabled, the switch is open.
 * 
 * @author Taylor Gorman
 * @version 1.1, Jun 25, 2016
 */
public class TristateBuffer {
	private Connection[] myInputs;
	private Connection myEnabled;
	private Connection[] myOutputs;
	
	public TristateBuffer(Connection[] theInputs, Connection theEnabled) {
		this.myInputs = theInputs;
		this.myEnabled = theEnabled;
		this.myOutputs = new Connection[myInputs.length];
		
		AndGate[] andGates = new AndGate[myInputs.length];
		for (int i = 0; i < andGates.length; i++) {
			andGates[i] = new AndGate(myInputs[i], myEnabled);
			myOutputs[i] = andGates[i].getOutput();
		}
	}
	
	public Connection[] getOutputs() {
		return myOutputs;
	}
}
