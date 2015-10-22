package transistor;

import java.util.ArrayList;

/**
 * A Connection is a wire that is either on or off.
 * A single Connection is most often used by two circuit structures.
 * By using Connections, we can keep circuit structures completely modular.
 * 
 * @author Taylor Gorman
 * @version 0.1, 10/22/15
 */
public class Connection {
	private boolean power;
	private ArrayList<Transistor> myOutputTransistors;
	
	public Connection() {
		power = false;
		myOutputTransistors = new ArrayList<Transistor>();
	}
	
	public void addOutputTransistor(Transistor theOutputTransistor) {
		this.myOutputTransistors.add(theOutputTransistor);
		updateOutputs();
	}
	
	public void powerOn() {
		if(power) return;
		
		power = true;
		updateOutputs();
	}
	
	public void powerOff() {
		if(!power) return;
		
		power = false;
		updateOutputs();
	}
	
	public boolean hasPower() {
		return power;
	}
	
	private void updateOutputs() {
		if(myOutputTransistors.size() == 0) return;
		
		for(Transistor t : myOutputTransistors) {
			t.update();
		}
	}
}
