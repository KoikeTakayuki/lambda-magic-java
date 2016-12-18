package lambdamagic.web.http.service.rest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses(
	{
		HttpRestOperationTest.class,
		HttpRestParameterTest.class
	})
public class WebHttpRestServicePackageTestSuite {}
