package circuit.combinational;

import gate.AndGate;
import gate.NotGate;
import gate.OrGate;
import transistor.Connection;
import transistor.Junction;

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
			Junction aJunction = new Junction(theSummandA);
			Junction bJunction = new Junction(theSummandB);
			Junction carryJunction = new Junction(theCarryIn);
			mySummandA = aJunction.getOutput();
			mySummandB = bJunction.getOutput();
			myCarryIn = carryJunction.getOutput();
			
			//Eight AND Gates for the eight unique combinations of A, B, and Carry-In
			myAndGates = new AndGate[8];
			//Three NOT Gates for inverting A, B, and Carry-In
			myNotGates = new NotGate[3];
			
			setupInverts();
			setupGates();
			setupOutputs();
		}
		
		/*
		 * A, B, and Carry-In all have inverted variants, which are connected
		 * to an AND Gate if the normal variant is not.
		 */
		private void setupInverts() {
			//Setup NOT Gates
			myNotGates[0] = new NotGate(mySummandA);
			myNotGates[1] = new NotGate(mySummandB);
			myNotGates[2] = new NotGate(myCarryIn);
			//Setup Inverted Lines
			mySummandAInverted = myNotGates[0].getOutput();
			mySummandBInverted = myNotGates[1].getOutput();
			myCarryInInverted = myNotGates[2].getOutput();
		}
		
		/*
		 * Set up each of the eight AND Gates.
		 * Each Gate contains either the inverted or normal path of all three inputs.
		 * Each combination of inputs is unique.
		 */
		private void setupGates() {
			Connection[][] connectionGroups = new Connection[8][3];
			
			for(int i = 0; i < 8; i++) {
				//The first half of gates receive the inverted A.
				if(i < 4) {
					connectionGroups[i][0] = mySummandAInverted;
				} else {
					connectionGroups[i][0] = mySummandA;
				}
				
				//Every two gates receive the inverted B.
				if(i == 0 | i == 1 | i == 4 | i == 5) {
					connectionGroups[i][1] = mySummandBInverted;
				} else {
					connectionGroups[i][1] = mySummandB;
				}
				
				//Every other gate receives the inverted carry-in.
				if(i % 2 == 0) {
					connectionGroups[i][2] = myCarryInInverted;
				} else {
					connectionGroups[i][2] = myCarryIn;
				}
				
				myAndGates[i] = new AndGate(connectionGroups[i]);
			}
			
			//These gates generate a carry-out.
			Connection[] carryOutGroup = {myAndGates[3].getOutput(), myAndGates[5].getOutput(),
					myAndGates[6].getOutput(), myAndGates[7].getOutput()};
			
			//These gates generate a sum.
			Connection[] sumOutGroup = {myAndGates[1].getOutput(), myAndGates[2].getOutput(), 
					myAndGates[4].getOutput(), myAndGates[7].getOutput()};
			
			//Setup output gates.
			myCarryGate = new OrGate(carryOutGroup);
			mySumGate = new OrGate(sumOutGroup);
			
		}
		
		//Setup output connections to gates.
		private void setupOutputs() {
			myCarryOut = myCarryGate.getOutput();
			mySumOut = mySumGate.getOutput();
			myZeroOut = myAndGates[0].getOutput();
		}
		

		/**
		 * Return the carry-out connection.
		 * @return the carry-out
		 */
		public Connection getCarryOut() {
			return myCarryOut;
		}

		/**
		 * Return the sum output connection.
		 * @return the sum
		 */
		public Connection getSumOut() {
			return mySumOut;
		}
		
		/**
		 * Return the zero output connection.
		 * @return the zero output
		 */
		public Connection getZeroOut() {
			return myZeroOut;
		}
}
