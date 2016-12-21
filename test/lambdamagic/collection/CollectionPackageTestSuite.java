package lambdamagic.collection;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import lambdamagic.collection.iterator.ArrayIteratorTest;
import lambdamagic.collection.iterator.IterablesTest;

@RunWith(Suite.class)
@SuiteClasses(
	{
		ArrayIteratorTest.class,
		IterablesTest.class
	})
public class CollectionPackageTestSuite {}
