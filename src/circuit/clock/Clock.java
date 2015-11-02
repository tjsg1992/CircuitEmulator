package circuit.clock;

import transistor.Connection;

public class Clock extends Thread {
	private Connection myClock;
	private long nextTime;
	
	public Clock() {
		myClock = new Connection();
		myClock.initializeThread(0);
		nextTime = System.currentTimeMillis() - System.currentTimeMillis() % 5 + 5;
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
			
			nextTime = System.currentTimeMillis() - System.currentTimeMillis() % 5 + 5;
			if(myClock.hasPower()) {
				myClock.powerOff();
			} else {
				myClock.powerOn();
			}
			
		}
	}
	
	public Connection getOutput() {
		return myClock;
	}
	

}
