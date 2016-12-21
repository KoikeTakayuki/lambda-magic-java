package lambdamagic.text;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses(
	{
		CharactersTest.class,
		TextLocationTest.class,
		TextPosition.class
	})
public class TextPackageTestSuite {}
