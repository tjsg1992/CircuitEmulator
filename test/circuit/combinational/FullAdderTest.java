package circuit.combinational;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import transistor.Connection;

public class FullAdderTest {
	private Connection summandA, summandB, carryIn;
	private FullAdder myFullAdder;

	@Before
	public void setUp() throws Exception {
		summandA = new Connection();
		summandB = new Connection();
		carryIn = new Connection();
		
		myFullAdder = new FullAdder(summandA, summandB, carryIn);
	}

	@Test
	public void allOffTest() {
		assertTrue(myFullAdder.getZeroOut().hasPower());
		assertFalse(myFullAdder.getCarryOut().hasPower());
		assertFalse(myFullAdder.getSumOut().hasPower());
	}
	
	@Test
	public void allOnTest() {
		summandA.powerOn();
		summandB.powerOn();
		carryIn.powerOn();
		
		assertFalse(myFullAdder.getZeroOut().hasPower());
		assertTrue(myFullAdder.getCarryOut().hasPower());
		assertTrue(myFullAdder.getSumOut().hasPower());
	}
	
	@Test
	public void oneOnTest() {
		summandA.powerOn();
		
		assertFalse(myFullAdder.getZeroOut().hasPower());
		assertFalse(myFullAdder.getCarryOut().hasPower());
		assertTrue(myFullAdder.getSumOut().hasPower());
		
		summandA.powerOff();
		summandB.powerOn();
		
		assertFalse(myFullAdder.getZeroOut().hasPower());
		assertFalse(myFullAdder.getCarryOut().hasPower());
		assertTrue(myFullAdder.getSumOut().hasPower());
		
		summandB.powerOff();
		carryIn.powerOn();
		
		assertFalse(myFullAdder.getZeroOut().hasPower());
		assertFalse(myFullAdder.getCarryOut().hasPower());
		assertTrue(myFullAdder.getSumOut().hasPower());
	}
	
	@Test
	public void twoOnTest() {
		summandA.powerOn();
		summandB.powerOn();
		
		assertFalse(myFullAdder.getZeroOut().hasPower());
		assertTrue(myFullAdder.getCarryOut().hasPower());
		assertFalse(myFullAdder.getSumOut().hasPower());
		
		summandB.powerOff();
		carryIn.powerOn();
		
		assertFalse(myFullAdder.getZeroOut().hasPower());
		assertTrue(myFullAdder.getCarryOut().hasPower());
		assertFalse(myFullAdder.getSumOut().hasPower());
		
		summandA.powerOff();
		summandB.powerOn();
		
		assertFalse(myFullAdder.getZeroOut().hasPower());
		assertTrue(myFullAdder.getCarryOut().hasPower());
		assertFalse(myFullAdder.getSumOut().hasPower());
	}

}
