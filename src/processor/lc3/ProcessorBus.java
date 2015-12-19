package processor.lc3;

import transistor.Connection;

/**
 * A simple communications system for transferring data between components.
 *
 * @author Taylor Gorman
 * @version 1.0, Dec 19, 2015
 *
 */
public class ProcessorBus {
	
	private Connection[] myGatePCOutputs;
	private Connection[] myGateMDROutputs;
	private Connection[] myGateALUOutputs;
	
	public ProcessorBus() {
		//Empty constructor, as the class is just a collection of setter and getter methods.
	}

	public Connection[] getGatePCOutputs() {
		return myGatePCOutputs;
	}

	public void setGatePCOutputs(Connection[] theGatePCOutputs) {
		this.myGatePCOutputs = theGatePCOutputs;
	}

	public Connection[] getGateMDROutputs() {
		return myGateMDROutputs;
	}

	public void setGateMDROutputs(Connection[] theGateMDROutputs) {
		this.myGateMDROutputs = theGateMDROutputs;
	}

	public Connection[] getGateALUOutputs() {
		return myGateALUOutputs;
	}

	public void setGateALUOutputs(Connection[] theGateALUOutputs) {
		this.myGateALUOutputs = theGateALUOutputs;
	}
}
