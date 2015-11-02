package circuit.combinational;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ DecoderTest.class, FullAdderTest.class, RippleAdderTest.class, MultiplexerTest.class })
public class AllCircuitCombinationalTests {

}
