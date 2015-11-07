package transistor;

public class ThreadConnection extends Thread {
	private Connection myParent;
	private int myDelay;
	
	public ThreadConnection(Connection theInputConnection, int theDelay) {
		myParent = theInputConnection;
		myDelay = theDelay;
	}
	
	public void update() {
		run();
	}
	
	public void run() {
		//System.out.println("Processing Thread");
		try {
			this.sleep(0, myDelay);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		myParent.update();
	}
}
