package lambdamagic.data;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import lambdamagic.data.functional.EitherTest;

@RunWith(Suite.class)
@SuiteClasses(
	{
		Tuple2Test.class,
		EitherTest.class
	})
public class DataPackageTestSuite {}
