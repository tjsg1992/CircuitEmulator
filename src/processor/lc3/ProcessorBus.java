package processor.lc3;

import transistor.Connection;

public class ProcessorBus {
	
	private Connection[] myGatePCOutputs;
	private Connection[] myGateMDROutputs;
	
	public ProcessorBus() {
		//Empty constructor. Mostly just a collection of setter and getter methods.
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
}
