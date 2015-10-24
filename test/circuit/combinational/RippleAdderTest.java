package circuit.combinational;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import transistor.Connection;

public class RippleAdderTest {
	private Connection[] mySummandsA;
	private Connection[] mySummandsB;
	
	private RippleAdder myAdder;

	@Before
	public void setUp() throws Exception {
		mySummandsA = new Connection[8];
		mySummandsB = new Connection[8];
		
		for(int i = 0; i < 8; i++) {
			mySummandsA[i] = new Connection();
			mySummandsB[i] = new Connection();
		}
		
		myAdder = new RippleAdder(mySummandsA, mySummandsB);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void sameSummandLengthTest() {
		Connection[] mySummandsSeven = new Connection[7];
		for(int i = 0; i < 7; i++) {
			mySummandsSeven[i] = new Connection();
		}
		
		myAdder = new RippleAdder(mySummandsA, mySummandsSeven);
	}

	@Test
	public void addTenAndFifteen() {
		mySummandsA[1].powerOn();
		mySummandsA[3].powerOn();
		
		mySummandsB[0].powerOn();
		mySummandsB[1].powerOn();
		mySummandsB[2].powerOn();
		mySummandsB[3].powerOn();
		
		assertTrue(myAdder.getOutputSums()[0].hasPower());
		assertFalse(myAdder.getOutputSums()[1].hasPower());
		assertFalse(myAdder.getOutputSums()[2].hasPower());
		assertTrue(myAdder.getOutputSums()[3].hasPower());
		assertTrue(myAdder.getOutputSums()[4].hasPower());
		assertFalse(myAdder.getOutputSums()[5].hasPower());
		assertFalse(myAdder.getOutputSums()[6].hasPower());
		assertFalse(myAdder.getOutputSums()[7].hasPower());
		assertFalse(myAdder.getCarryOut().hasPower());		
	}
	
	@Test
	public void sumLargerThan256() {
		mySummandsA[1].powerOn();
		mySummandsA[4].powerOn();
		mySummandsA[5].powerOn();
		
		mySummandsB[1].powerOn();
		mySummandsB[3].powerOn();
		mySummandsB[4].powerOn();
		mySummandsB[5].powerOn();
		mySummandsB[6].powerOn();
		mySummandsB[7].powerOn();
		
		assertFalse(myAdder.getOutputSums()[0].hasPower());
		assertFalse(myAdder.getOutputSums()[1].hasPower());
		assertTrue(myAdder.getOutputSums()[2].hasPower());
		assertTrue(myAdder.getOutputSums()[3].hasPower());
		assertFalse(myAdder.getOutputSums()[4].hasPower());
		assertTrue(myAdder.getOutputSums()[5].hasPower());
		assertFalse(myAdder.getOutputSums()[6].hasPower());
		assertFalse(myAdder.getOutputSums()[7].hasPower());
		assertTrue(myAdder.getCarryOut().hasPower());	
	}
	
	@Test
	public void addZeroAndZero() {
		assertFalse(myAdder.getOutputSums()[0].hasPower());
		assertFalse(myAdder.getOutputSums()[1].hasPower());
		assertFalse(myAdder.getOutputSums()[2].hasPower());
		assertFalse(myAdder.getOutputSums()[3].hasPower());
		assertFalse(myAdder.getOutputSums()[4].hasPower());
		assertFalse(myAdder.getOutputSums()[5].hasPower());
		assertFalse(myAdder.getOutputSums()[6].hasPower());
		assertFalse(myAdder.getOutputSums()[7].hasPower());
		assertFalse(myAdder.getCarryOut().hasPower());	
	}
	
	@Test
	public void addZeroTo255() {
		mySummandsA[0].powerOn();
		mySummandsA[1].powerOn();
		mySummandsA[2].powerOn();
		mySummandsA[3].powerOn();
		mySummandsA[4].powerOn();
		mySummandsA[5].powerOn();
		mySummandsA[6].powerOn();
		mySummandsA[7].powerOn();
		
		assertTrue(myAdder.getOutputSums()[0].hasPower());
		assertTrue(myAdder.getOutputSums()[1].hasPower());
		assertTrue(myAdder.getOutputSums()[2].hasPower());
		assertTrue(myAdder.getOutputSums()[3].hasPower());
		assertTrue(myAdder.getOutputSums()[4].hasPower());
		assertTrue(myAdder.getOutputSums()[5].hasPower());
		assertTrue(myAdder.getOutputSums()[6].hasPower());
		assertTrue(myAdder.getOutputSums()[7].hasPower());
		assertFalse(myAdder.getCarryOut().hasPower());	
	}
	
	@Test
	public void addTo510() {
		mySummandsA[0].powerOn();
		mySummandsA[1].powerOn();
		mySummandsA[2].powerOn();
		mySummandsA[3].powerOn();
		mySummandsA[4].powerOn();
		mySummandsA[5].powerOn();
		mySummandsA[6].powerOn();
		mySummandsA[7].powerOn();
		
		mySummandsB[0].powerOn();
		mySummandsB[1].powerOn();
		mySummandsB[2].powerOn();
		mySummandsB[3].powerOn();
		mySummandsB[4].powerOn();
		mySummandsB[5].powerOn();
		mySummandsB[6].powerOn();
		mySummandsB[7].powerOn();
		
		assertFalse(myAdder.getOutputSums()[0].hasPower());
		assertTrue(myAdder.getOutputSums()[1].hasPower());
		assertTrue(myAdder.getOutputSums()[2].hasPower());
		assertTrue(myAdder.getOutputSums()[3].hasPower());
		assertTrue(myAdder.getOutputSums()[4].hasPower());
		assertTrue(myAdder.getOutputSums()[5].hasPower());
		assertTrue(myAdder.getOutputSums()[6].hasPower());
		assertTrue(myAdder.getOutputSums()[7].hasPower());
		assertTrue(myAdder.getCarryOut().hasPower());
	}

}
