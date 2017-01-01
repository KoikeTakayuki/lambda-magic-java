package lambdamagic.sql;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import lambdamagic.sql.mysql.MySQLCommandBuilder;

@RunWith(Suite.class)
@SuiteClasses(
	{
		SQLTypeTest.class,
		SQLDatabaseTest.class,
		MySQLCommandBuilder.class
	})
public class SQLPackageTestSuite {}
