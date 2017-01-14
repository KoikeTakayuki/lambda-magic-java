package jp.lambdamagic.sql.query;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses(
	{
		SQLInsertQueryTest.class,
		SQLSelectQueryTest.class,
		SQLUpdateQueryTest.class,
		SQLDeleteQueryTest.class,
		SQLInsertQueryBuilderTest.class,
		SQLSelectQueryBuilderTest.class,
		SQLUpdateQueryBuilderTest.class,
		SQLDeleteQueryBuilderTest.class,
	})
public class QueryPackageTestSuite {}
