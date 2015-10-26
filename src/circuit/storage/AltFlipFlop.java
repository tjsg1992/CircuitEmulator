package circuit.storage;

import gate.AndGate;
import gate.NotGate;
import gate.OrGate;
import transistor.Connection;
import transistor.Junction;

public class AltFlipFlop {
	private Connection myToggleLine;
	private Connection myClockLine;
	private EdgeLatch myLatch;
	
	public AltFlipFlop(Connection theToggleLine, Connection theClockLine) {
		myToggleLine = theToggleLine;
		myClockLine = theClockLine;
		
		NotGate toggleInverter = new NotGate(myToggleLine);
		
		Connection[] dummyObject = {new Connection()};
		Junction junctionA = new Junction(dummyObject);
		Junction junctionB = new Junction(dummyObject);
		
		Connection[] andGroupA = {toggleInverter.getOutput(), junctionA.getOutput()};
		Connection[] andGroupB = {myToggleLine, junctionB.getOutput()};
		
		AndGate andGateA = new AndGate(andGroupA);
		AndGate andGateB = new AndGate(andGroupB);
		
		Connection[] orGroup = {andGateA.getOutput(), andGateB.getOutput()};
		
		OrGate myOrGate = new OrGate(orGroup);
		
		myLatch = new EdgeLatch(myOrGate.getOutput(), myClockLine, false);
		
		Connection[] junctionAGroup = {myLatch.getOutputA()};
		Connection[] junctionBGroup = {myLatch.getOutputB()};
		
		junctionA.setInputs(junctionAGroup);
		junctionB.setInputs(junctionBGroup);
	}
	
	public Connection getOutputA() {
		return myLatch.getOutputA();
	}
	
	public Connection getOutputB() {
		return myLatch.getOutputB();
	}

}
