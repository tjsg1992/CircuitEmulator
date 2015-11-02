package circuit.storage;

import gate.NandGate;
import transistor.Connection;
import transistor.Junction;

public class ETDFlipFlop {
	private Connection myClock;
	private Connection myData;
	private Connection myOutputA;
	private Connection myOutputB;
	private NandGate[] myNandGates;
	private Junction[] myNandGateOutputs;
	
	public ETDFlipFlop(Connection theData, Connection theClock) {
		myData = theData;
		myClock = theClock;
		
		myData.initializeThread(0);
		myClock.initializeThread(0);
		
		setupGates();
	}
	
	private void setupGates() {
		myNandGates = new NandGate[6];
		myNandGateOutputs = new Junction[6];
		Connection[] dummyGroup = {new Connection()};
		Connection[][] nandGroups = new Connection[6][];
		
		for(int i = 0; i < 6; i++) {
			myNandGateOutputs[i] = new Junction(dummyGroup);
		}
		
		Connection[] nandGroup0 = {myNandGateOutputs[3].getOutput(), myNandGateOutputs[1].getOutput()};
		nandGroups[0] = nandGroup0;
		
		Connection[] nandGroup1 = {myNandGateOutputs[0].getOutput(), myClock};
		nandGroups[1] = nandGroup1;
		
		Connection[] nandGroup2 = {myNandGateOutputs[1].getOutput(), myNandGateOutputs[3].getOutput(), myClock};
		nandGroups[2] = nandGroup2;
		
		Connection[] nandGroup3 = {myNandGateOutputs[2].getOutput(), myData};
		nandGroups[3] = nandGroup3;
		
		Connection[] nandGroup4 = {myNandGateOutputs[1].getOutput(), myNandGateOutputs[5].getOutput()};
		nandGroups[4] = nandGroup4;
		
		Connection[] nandGroup5 = {myNandGateOutputs[4].getOutput(), myNandGateOutputs[2].getOutput()};
		nandGroups[5] = nandGroup5;
		
		for(int i = 0; i < 6; i++) {
			myNandGates[i] = new NandGate(nandGroups[i]);
			Connection[] junctionInput = {myNandGates[i].getOutput()};
			myNandGateOutputs[i].setInputs(junctionInput);
			myNandGates[i].getOutput().addJunction(myNandGateOutputs[i]);
		}
		
		myNandGates[0].getOutput().powerOff();
		myNandGates[1].getOutput().powerOn();
		myNandGates[2].getOutput().powerOn();
		myNandGates[3].getOutput().powerOn();
		myNandGates[4].getOutput().powerOff();
		myNandGates[5].getOutput().powerOn();
		
		myOutputA = myNandGates[4].getOutput();
		myOutputB = myNandGates[5].getOutput();
		
		myNandGates[1].getOutput().initializeThread(0);
		myNandGates[2].getOutput().initializeThread(0);
		
	}
	
	public Connection getOutputA() {
		return myOutputA;
	}
	
	public Connection getOutputB() {
		return myOutputB;
	}
	
	public void printStatus() {
		System.out.println("Data Line: " + myData.hasPower() + ", Clock Line: " + myClock.hasPower());
		for(int i = 0; i < 6; i++) { 
			System.out.println("Nand Gate " + (i) + ": " + myNandGates[i].getOutput().hasPower());
		}
		System.out.println("Q: " + myOutputA.hasPower() + ", -Q: " + myOutputB.hasPower() + "\n");
	}
	
}
