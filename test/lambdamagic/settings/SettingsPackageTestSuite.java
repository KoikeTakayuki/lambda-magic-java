package lambdamagic.settings;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses(
	{
		InMemoryPropertySetTest.class,
		PropertyStringTest.class,
		SettingsTest.class
	})
public class SettingsPackageTestSuite {}
