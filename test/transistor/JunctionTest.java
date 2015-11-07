package transistor;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class JunctionTest {
	private Connection connectionA;
	private Connection connectionB;
	private Connection connectionC;
	private Junction junction;
	
	@Before
	public void setup() {
		connectionA = new Connection();
		connectionB = new Connection();
		connectionC = new Connection();
		
		Connection[] junctionGroup = {connectionA, connectionB, connectionC};
		junction = new Junction(junctionGroup);
		
		connectionA.addJunction(junction);
		connectionB.addJunction(junction);
		connectionC.addJunction(junction);
	}

	@Test
	public void allOffTest() {
		assertFalse(junction.getOutput().hasPower());
	}
	
	@Test
	public void allOnTest() {
		connectionA.powerOn();
		connectionB.powerOn();
		connectionC.powerOn();
		
		assertTrue(junction.getOutput().hasPower());
	}
	
	@Test
	public void oneOnTest() {
		connectionA.powerOn();
		assertTrue(junction.getOutput().hasPower());
		
		connectionA.powerOff();
		assertFalse(junction.getOutput().hasPower());
		
		connectionB.powerOn();
		assertTrue(junction.getOutput().hasPower());
	}

}
