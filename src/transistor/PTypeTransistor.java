package transistor;

/**
 * An n-type transistor is one of two types of transistors.
 * <p>
 * An n-type transistor essentially acts as an inverter:<br>
 * If its input is on, it's output is off.<br>
 * If it's input is off, it's output is on.
 * @author Taylor Gorman
 * @version 0.1, 10/22/15
 *
 */
public class PTypeTransistor extends Transistor {
	
	public PTypeTransistor(Connection theInputConnection) {
		super(theInputConnection);
	}
	
	@Override
	public void update() {
		if(!myInputConnection.hasPower()) {
			myOutputConnection.powerOn();
		} else {
			myOutputConnection.powerOff();
		}
		
	}

}
