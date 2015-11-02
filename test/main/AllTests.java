package main;

import gate.AllGateTests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import transistor.AllTransistorTests;
import circuit.clock.AllCircuitClockTests;
import circuit.combinational.AllCircuitCombinationalTests;
import circuit.combinational.DecoderTest;
import circuit.storage.AllCircuitStorageTests;

@RunWith(Suite.class)
@SuiteClasses({
	AllGateTests.class,
	AllTransistorTests.class,
	AllCircuitStorageTests.class,
	AllCircuitCombinationalTests.class,
	//AllCircuitClockTests.class
})
public class AllTests {

}
