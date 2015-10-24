package transistor;

/**
 * An n-type transistor is one of two types of transistors.
 * <p>
 * An n-type transistor essentially acts as an inverter if it is powered:<br>
 * If its input is on, its output is off.<br>
 * If it's input is off, its output is on.<br>
 * If the transistor is not powered, its output is always off.
 * @author Taylor Gorman
 * @version 0.2, 10/23/15
 *
 */
public class PTypeTransistor extends Transistor {
	
	public PTypeTransistor(Connection thePowerConnection, Connection theInputConnection) {
		super(thePowerConnection, theInputConnection);
		super.connectSelfToInput();
		super.connectSelfToPower();
	}
	
	@Override
	public void update() {
		if(!hasPower()) {
			super.powerOffOutput();
			return;
		}

		if(!super.inputHasPower()) {
			super.powerOnOutput();
		} else {
			super.powerOffOutput();
		}
		
	}

}
