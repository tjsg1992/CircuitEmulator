package circuit.clock;

import gate.AndGate;
import gate.NotGate;
import transistor.Connection;
import transistor.Junction;

public class Clock extends Thread {
	public static final int CLOCK_SPEED = 2;
	private Connection myClock;
	private long nextTime;
	
	private Junction myHaltJunction;
	private NotGate myHaltInverter;
	private AndGate myHaltGate;
	
	public Clock() {
		Connection[] junctionInputs = {new Connection()};
		myHaltJunction = new Junction(junctionInputs);
		myHaltInverter = new NotGate(myHaltJunction.getOutput());
		
		myClock = new Connection();
		Connection[] haltGateGroup = {myHaltInverter.getOutput(), myClock};
		myHaltGate = new AndGate(haltGateGroup);
		nextTime = System.currentTimeMillis() - System.currentTimeMillis() % CLOCK_SPEED + CLOCK_SPEED;
	}
	
	@SuppressWarnings("static-access")
	public void run() {
		while(true) {
			long remainingTime = nextTime - System.currentTimeMillis();
			
			if(remainingTime < 0) {
				remainingTime = 0;
			}
			try {
				this.sleep(remainingTime);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			nextTime = System.currentTimeMillis() - System.currentTimeMillis() % CLOCK_SPEED + CLOCK_SPEED;
			if(myClock.hasPower()) {
				myClock.powerOff();
			} else {
				myClock.powerOn();
			}
			
		}
	}
	
	public Connection getOutput() {
		return myHaltGate.getOutput();
	}
	
	public void setHaltLine(Connection theHaltLine) {
		Connection[] haltLineGroup = {theHaltLine};
		theHaltLine.connectOutputTo(myHaltJunction);
		myHaltJunction.setInputs(haltLineGroup);
	}
	

}
