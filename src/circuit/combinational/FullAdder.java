package circuit.combinational;

import gate.AndGate;
import gate.NotGate;
import gate.OrGate;
import transistor.Connection;

/**
 * A Full Adder adds two bits together.<br>
 * As inputs, it takes an two summands and a carry-in.<br>
 * As outputs, it generates a sum and carry-out.
 * 
 * @author Taylor Gorman
 * @version 0.2, 10/24/15
 */
public class FullAdder {

		private Connection mySummandA, mySummandB, myCarryIn;
		private Connection mySummandAInverted, mySummandBInverted, myCarryInInverted;
		
		private Connection myCarryOut, mySumOut, myZeroOut;
		private AndGate[] myAndGates;
		private OrGate myCarryGate, mySumGate;
		
		private NotGate[] myNotGates;
		
		public FullAdder(Connection theSummandA, Connection theSummandB, Connection theCarryIn) {
			mySummandA = theSummandA;
			mySummandB = theSummandB;
			myCarryIn = theCarryIn;
			
			myAndGates = new AndGate[8];
			myNotGates = new NotGate[3];
			
			setupInverts();
			setupGates();
			setupOutputs();
		}
		
		private void setupInverts() {
			myNotGates[0] = new NotGate(mySummandA);
			myNotGates[1] = new NotGate(mySummandB);
			myNotGates[2] = new NotGate(myCarryIn);
			
			mySummandAInverted = myNotGates[0].getOutput();
			mySummandBInverted = myNotGates[1].getOutput();
			myCarryInInverted = myNotGates[2].getOutput();
		}
		
		private void setupGates() {
			Connection[][] connectionGroups = new Connection[8][3];
			
			for(int i = 0; i < 8; i++) {
				if(i < 4) {
					connectionGroups[i][0] = mySummandAInverted;
				} else {
					connectionGroups[i][0] = mySummandA;
				}
				
				if(i == 0 | i == 1 | i == 4 | i == 5) {
					connectionGroups[i][1] = mySummandBInverted;
				} else {
					connectionGroups[i][1] = mySummandB;
				}
				
				if(i % 2 == 0) {
					connectionGroups[i][2] = myCarryInInverted;
				} else {
					connectionGroups[i][2] = myCarryIn;
				}
				
				myAndGates[i] = new AndGate(connectionGroups[i]);
			}
			
			Connection[] carryOutGroup = {myAndGates[3].getOutput(), myAndGates[5].getOutput(),
					myAndGates[6].getOutput(), myAndGates[7].getOutput()};
			
			Connection[] sumOutGroup = {myAndGates[1].getOutput(), myAndGates[2].getOutput(), 
					myAndGates[4].getOutput(), myAndGates[7].getOutput()};
			
			
			myCarryGate = new OrGate(carryOutGroup);
			mySumGate = new OrGate(sumOutGroup);
			
		}
		
		private void setupOutputs() {
			myCarryOut = myCarryGate.getOutput();
			mySumOut = mySumGate.getOutput();
			myZeroOut = myAndGates[0].getOutput();
		}
		

		public Connection getCarryOut() {
			return myCarryOut;
		}

		public Connection getSumOut() {
			return mySumOut;
		}
		public Connection getZeroOut() {
			return myZeroOut;
		}
}
