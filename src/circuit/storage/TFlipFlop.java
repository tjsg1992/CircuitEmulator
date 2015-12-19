package circuit.storage;

import transistor.Connection;
import transistor.Junction;
import gate.AndGate;
import gate.NotGate;
import gate.OrGate;

public class TFlipFlop {
	private Connection myToggle;
	private Connection myClock;
	
	private NotGate myToggleInverter;
	private ETDFlipFlop myLatch;
	
	private AndGate myAndGateA;
	private AndGate myAndGateB;
	private OrGate myOrGate;
	
	private Junction myJunctionA, myJunctionB;
	
	public TFlipFlop(Connection theClock, Connection theToggle) {
		myClock = theClock;
		myToggle = theToggle;
		myToggleInverter = new NotGate(myToggle);
		
		Connection[] dummyGroup = {new Connection()};
		myJunctionA = new Junction(dummyGroup);
		myJunctionB = new Junction(dummyGroup);
		
		Connection[] andGateAGroup = {myJunctionA.getOutput(), myToggleInverter.getOutput()};
		Connection[] andGateBGroup = {myJunctionB.getOutput(), myToggle};
		
		myAndGateA = new AndGate(andGateAGroup);
		myAndGateB = new AndGate(andGateBGroup);
		
		Connection[] orGroup = {myAndGateA.getOutput(), myAndGateB.getOutput()};
		myOrGate = new OrGate(orGroup);
		
		myLatch = new ETDFlipFlop(myOrGate.getOutput(), myClock);
		
		Connection[] junctionGroupA = {myLatch.getOutputA()};
		Connection[] junctionGroupB = {myLatch.getOutputB()};
		
		myJunctionA.setInputs(junctionGroupA);
		myJunctionB.setInputs(junctionGroupB);
		
		myLatch.getOutputA().connectOutputTo(myJunctionA);
		myLatch.getOutputB().connectOutputTo(myJunctionB);
	}
	
	public Connection getOutputA() {
		return myLatch.getOutputA();
	}
	
	public Connection getOutputB() {
		return myLatch.getOutputB();
	}
	
	public void printStatus() {
		System.out.println("And Gate A: " + myAndGateA.getOutput().hasPower() + ", And Gate B: " + myAndGateB.getOutput().hasPower() + ", Or Gate: " + myOrGate.getOutput().hasPower());
		myLatch.printStatus();
	}
}
