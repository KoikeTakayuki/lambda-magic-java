package jp.lambdamagic.collection;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import jp.lambdamagic.collection.iterator.ArrayIteratorTest;
import jp.lambdamagic.collection.iterator.IterablesTest;

@RunWith(Suite.class)
@SuiteClasses(
	{
		ArrayIteratorTest.class,
		IterablesTest.class
	})
public class CollectionPackageTestSuite {}
