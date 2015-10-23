package transistor;

public class Source extends Connection {

	public Source() {
		super();
	}
	
	@Override
	public void powerOn() {
		//A Source is always powered on.
	}
	
	@Override
	public void powerOff() {
		//A Source is always powered on.
	}
	
	@Override
	public boolean hasPower() {
		return true;
	}
}