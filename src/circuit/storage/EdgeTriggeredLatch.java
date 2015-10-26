package circuit.storage;

import gate.NandGate;
import gate.NotGate;
import transistor.Connection;

public class EdgeTriggeredLatch {
	private Connection myDataLine;
	private Connection myWELine;
	private GatedDLatch myGatedD;
	private RSLatch myLatch;
	private NandGate gateA, gateB;
	
	private NotGate myWEInverter;
	
	public EdgeTriggeredLatch(Connection theDataLine, Connection theWELine) {
		myDataLine = theDataLine;
		myWELine = theWELine;
		myWEInverter = new NotGate(theWELine);
		
		myGatedD = new GatedDLatch(myDataLine, myWELine);
		
		Connection[] gateAGroup = {myGatedD.getOutputA(), myWEInverter.getOutput()};
		Connection[] gateBGroup = {myGatedD.getOutputB(), myWEInverter.getOutput()};
		gateA = new NandGate(gateAGroup);
		gateB = new NandGate(gateBGroup);
		
		myLatch = new RSLatch(gateA.getOutput(), gateB.getOutput());
	}
	
	public Connection getOutputA() {
		return myLatch.getOutputA();
	}
	
	public Connection getOutputB() {
		return myLatch.getOutputB();
	}
	
	public void printStatus() {
		System.out.println("\nData Line: " + myDataLine.hasPower() + ", WE Line: " + myWELine.hasPower());
		System.out.println("Master Latch A: " + myGatedD.getOutputA().hasPower() + ", Master Latch B " + myGatedD.getOutputB().hasPower());
		System.out.println("Inverted WE Line: " + myWEInverter.getOutput().hasPower());
		System.out.println("NAND Gate A: " + gateA.getOutput().hasPower() + ", NAND Gate B: " + gateB.getOutput().hasPower());
		System.out.println("Slave Latch A: " + myLatch.getOutputA().hasPower() + ", Slave Latch B " + myLatch.getOutputB().hasPower());
	}
	
}
