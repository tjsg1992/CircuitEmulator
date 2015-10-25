package circuit.storage;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ GatedDLatchTest.class, LinearRegisterTest.class,
		RSLatchTest.class })
public class AllCircuitStorageTests {

}
