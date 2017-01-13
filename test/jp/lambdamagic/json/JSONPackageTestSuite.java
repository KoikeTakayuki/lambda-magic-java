package jp.lambdamagic.json;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import jp.lambdamagic.json.data.JSONBooleanTest;
import jp.lambdamagic.json.data.JSONNumberTest;
import jp.lambdamagic.json.data.JSONObjectTest;
import jp.lambdamagic.json.data.JSONStringTest;

@RunWith(Suite.class)
@SuiteClasses(
	{
		JSONDataSourceTest.class,
		JSONParserTest.class,
		JSONWriterTest.class,
		JSONObjectTest.class,
		JSONStringTest.class,
		JSONNumberTest.class,
		JSONBooleanTest.class
	})
public class JSONPackageTestSuite {}
