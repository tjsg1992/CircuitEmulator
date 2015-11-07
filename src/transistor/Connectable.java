package transistor;

public interface Connectable {
	public void update();
	public void connectOutputTo(Connectable theOtherConnectable);
}
