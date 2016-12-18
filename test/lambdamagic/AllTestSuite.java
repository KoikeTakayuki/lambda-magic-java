package lambdamagic;

import org.junit.runner.JUnitCore;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import lambdamagic.sql.SQLPackageTestSuite;
import lambdamagic.web.http.service.rest.WebHttpRestServicePackageTestSuite;

@RunWith(Suite.class)
@SuiteClasses(
	{
		SQLPackageTestSuite.class,
		WebHttpRestServicePackageTestSuite.class
	})
public class AllTestSuite {
	
    public static void main(String[] args) {
        JUnitCore.main(SQLPackageTestSuite.class.getName());
    }
}
