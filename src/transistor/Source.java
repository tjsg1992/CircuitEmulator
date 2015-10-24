package transistor;

/**
 * A Source is a special type of Connection that is
 * always powered.
 * 
 * @author Taylor Gorman
 * @version 0.2, 10/23/15
 */
public class Source extends Connection {

	/**
	 * Construct a Source.
	 */
	public Source() {
		super();
	}
	
	@Override
	/**
	 * Instantly return, as the Source is always powered.
	 */
	public void powerOn() {
		//A Source is always powered on.
	}
	
	@Override
	/**
	 * Instantly return, as the Source is always powered.
	 */
	public void powerOff() {
		//A Source is always powered on.
	}
	
	@Override
	/**
	 * Return true, as the Source is always powered.
	 */
	public boolean hasPower() {
		return true;
	}
}