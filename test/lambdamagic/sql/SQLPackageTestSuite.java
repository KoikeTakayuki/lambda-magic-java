package lambdamagic.sql;

import org.junit.runner.JUnitCore;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses(
	{
		SQLTypeTest.class,
		SQLDatabaseTest.class
	})
public class SQLPackageTestSuite {
	
    public static void main(String[] args) {
        JUnitCore.main(SQLPackageTestSuite.class.getName());
    }
}