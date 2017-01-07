package jp.lambdamagic.json;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses(
	{
		JSONDataSourceTest.class,
		JSONParserTest.class,
		JSONWriterTest.class
	})
public class JSONPackageTestSuite {}
