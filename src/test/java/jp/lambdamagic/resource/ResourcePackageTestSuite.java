package jp.lambdamagic.resource;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses(
	{
		FileResourceLoaderTest.class,
		RelativeResourceLoaderTest.class
	})
public class ResourcePackageTestSuite {}
