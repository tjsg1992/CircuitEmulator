package transistor;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ ConnectionTest.class, NTypeTransistorTest.class,
		PTypeTransistorTest.class })
public class AllTransistorTests {

}
