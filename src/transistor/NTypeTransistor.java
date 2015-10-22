package transistor;

/**
 * An n-type transistor is one of two types of transistors.
 * <p>
 * An n-type transistor essentially acts as an extender:<br>
 * If its input is on, it's output is on.<br>
 * If it's input is off, it's output is off.
 * @author Taylor Gorman
 * @version 0.1, 10/22/15
 *
 */
public class NTypeTransistor extends Transistor {
	
	/**
	 * 
	 */
	public NTypeTransistor(Connection theInputConnection) {
		super(theInputConnection);
	}
	
	@Override
	public void update() {
		if(myInputConnection.hasPower()) {
			myOutputConnection.powerOn();
		} else {
			myOutputConnection.powerOff();
		}
	}
	
}
