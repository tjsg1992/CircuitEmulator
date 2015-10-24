package transistor;

/**
 * An n-type transistor is one of two types of transistors.
 * <p>
 * An n-type transistor essentially acts as an extender, so long as it's powered:<br>
 * If its input is on, its output is on.<br>
 * If it's input is off, its output is off.<br>
 * If the transistor is not powered, its output is always off.
 * @author Taylor Gorman
 * @version 0.2, 10/23/15
 *
 */
public class NTypeTransistor extends Transistor {
	
	/**
	 * Construct an n-type transistor.
	 */
	public NTypeTransistor(Connection thePowerConnection, Connection theInputConnection) {
		super(thePowerConnection, theInputConnection);
		super.connectSelfToInput();
		super.connectSelfToPower();
	}
	
	@Override
	public void update() {
		//No matter the input, the output is off if the Transistor is not powered.
		if(!hasPower()) super.powerOffOutput();
		
		if(super.inputHasPower()) {
			super.powerOnOutput();
		} else {
			super.powerOffOutput();
		}
	}
	
}
