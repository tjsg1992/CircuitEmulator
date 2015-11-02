package circuit.clock;

import circuit.storage.TFlipFlop;
import transistor.Connection;

public class RippleCounter {
	private Connection myClock;
	private Connection myToggle;
	
	private TFlipFlop myLatch1;
	private TFlipFlop myLatch2;
	
	private Connection[] myOutputConnections;
	
	public RippleCounter(Connection theClock) throws InterruptedException {
		myClock = theClock;
		myToggle = new Connection();
		myToggle.powerOff();
		myOutputConnections = new Connection[2];
		
		myLatch1 = new TFlipFlop(myClock, myToggle);
		myOutputConnections[0] = myLatch1.getOutputA();
		
		myLatch2 = new TFlipFlop(myLatch1.getOutputB(), myToggle);
		myOutputConnections[1] = myLatch2.getOutputA();
		
		Thread.sleep(0, 5);		
		myToggle.powerOn();		
		Thread.sleep(0, 5);		
		myLatch1.getOutputB().powerOn();		
		Thread.sleep(0, 5);		
		myLatch1.getOutputB().powerOff();		
		Thread.sleep(0, 5);
		myLatch1.getOutputB().powerOn();
	}
	
	public Connection[] getOutputConnections() {
		return myOutputConnections;
	}
	
	public void printStatus() {
		System.out.println("Latch 1 -- " + "Toggle: " + myToggle.hasPower() + ", Clock: " + myClock.hasPower() + ", -Q: " + myLatch1.getOutputB().hasPower());
		System.out.println("Latch 2 -- " + "Toggle: " + myToggle.hasPower() + ", Clock: " + myLatch1.getOutputB().hasPower() + ", -Q: " + myLatch2.getOutputB().hasPower());
	}
}
