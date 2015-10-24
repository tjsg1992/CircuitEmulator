package circuit.combinational;

import transistor.Connection;

public class RippleAdder {
	private Connection[] mySummandsA;
	private Connection[] mySummandsB;
	private FullAdder[] myFullAdders;
	
	private Connection[] myOutputSums;
	private Connection myCarryOut;
	
	public RippleAdder(Connection[] theSummandsA, Connection[] theSummandsB) {
		
		if(theSummandsA.length != theSummandsB.length) {
			throw new IllegalArgumentException();
		}
		
		this.mySummandsA = theSummandsA;
		this.mySummandsB = theSummandsB;
		
		myFullAdders = new FullAdder[mySummandsA.length];
		myOutputSums = new Connection[mySummandsA.length];
		
		setupGates();
	}
	
	private void setupGates() {
		Connection zeroConnection = new Connection();
		zeroConnection.powerOff();
		
		FullAdder firstAdder = new FullAdder(mySummandsA[0], mySummandsB[0], zeroConnection);
		myFullAdders[0] = firstAdder;
		myOutputSums[0] = firstAdder.getSumOut();
		
		for(int i = 1; i < mySummandsA.length; i++) {
			myFullAdders[i] = new FullAdder(mySummandsA[i], mySummandsB[i], myFullAdders[i - 1].getCarryOut());
			myOutputSums[i] = myFullAdders[i].getSumOut();
		}
		
		myCarryOut = myFullAdders[mySummandsA.length - 1].getCarryOut();		
	}

	public Connection[] getOutputSums() {
		return myOutputSums;
	}

	public Connection getCarryOut() {
		return myCarryOut;
	}
}
