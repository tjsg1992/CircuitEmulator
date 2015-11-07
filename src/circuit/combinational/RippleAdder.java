package circuit.combinational;

import transistor.Connection;

/**
 * A RippleAdder is a series of full-adders that enables the easy adding
 * of two sets of data (often from Registers) with the same number
 * of bits.
 * 
 * @author Taylor Gorman
 * @version 0.3, 11/07/15
 */
public class RippleAdder {
	private Connection[] mySummandsA;
	private Connection[] mySummandsB;
	private FullAdder[] myFullAdders;
	
	private Connection[] myOutputSums;
	private Connection myCarryOut;
	
	/**
	 * Construct a new RippleAdder.
	 * @param theSummandsA the first set of summands
	 * @param theSummandsB the second set of summands
	 * @throws IllegalArgumentException if the two sets are not of the same
	 * length, or if a set is of size zero.
	 */
	public RippleAdder(Connection[] theSummandsA, Connection[] theSummandsB) {
		
		//The two sets are not of the same length.
		if(theSummandsA.length != theSummandsB.length) {
			throw new IllegalArgumentException();
		}
		
		//A set has less than one output.
		if(theSummandsA.length < 1) {
			throw new IllegalArgumentException();
		}
		
		this.mySummandsA = theSummandsA;
		this.mySummandsB = theSummandsB;
		
		myFullAdders = new FullAdder[mySummandsA.length];
		myOutputSums = new Connection[mySummandsA.length];
		
		setupGates();
	}
	
	/*
	 * Instantiate the array of FullAdders and link them together.
	 */
	private void setupGates() {
		//Zero is used as the carry-in for the first FullAdder.
		Connection zeroConnection = new Connection();
		zeroConnection.powerOff();
		
		//Create the first FullAdder, which uses zero as a carry-in.
		FullAdder firstAdder = new FullAdder(mySummandsA[0], mySummandsB[0], zeroConnection);
		myFullAdders[0] = firstAdder;
		myOutputSums[0] = firstAdder.getSumOut();
		
		//Create the next seven FullAdders, setting each carry-in as the carry-out of the last one.
		for(int i = 1; i < mySummandsA.length; i++) {
			myFullAdders[i] = new FullAdder(mySummandsA[i], mySummandsB[i], myFullAdders[i - 1].getCarryOut());
			myOutputSums[i] = myFullAdders[i].getSumOut();
		}
		
		//Set the RippleAdder's carryout as the carryout of the last FulLAdder.
		myCarryOut = myFullAdders[mySummandsA.length - 1].getCarryOut();		
	}

	/**
	 * Return the set of output sums.
	 * @return the output sums
	 */
	public Connection[] getOutputSums() {
		return myOutputSums;
	}

	/**
	 * Return the carry-out, which indicates an overflow.
	 * @return the carry-out
	 */
	public Connection getCarryOut() {
		return myCarryOut;
	}
}
