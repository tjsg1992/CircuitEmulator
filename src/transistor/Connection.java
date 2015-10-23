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
	private ArrayList<Junction> myJunctions;
	
	public Connection() {
		power = false;
		myOutputTransistors = new ArrayList<Transistor>();
		myJunctions = new ArrayList<Junction>();
	}
	
	public void addJunction(Junction theJunction) {
		this.myJunctions.add(theJunction);
		updateOutputs();
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
		
		if(myJunctions.size() != 0) {
			for(Junction j : myJunctions) {
				j.update();
			}
		}
		
		if(myOutputTransistors.size() != 0) {
			for(Transistor t : myOutputTransistors) {
				t.update();
			}
		}
	}
}
