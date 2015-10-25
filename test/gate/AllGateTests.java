package gate;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ AndGateTest.class, NandGateTest.class, NorGateTest.class,
		NotGateTest.class, OrGateTest.class })
public class AllGateTests {

}
