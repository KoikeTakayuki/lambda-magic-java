package jp.lambdamagic.sql;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import jp.lambdamagic.sql.mysql.MySQLCommandBuilderTest;
import jp.lambdamagic.sql.query.QueryPackageTestSuite;

@RunWith(Suite.class)
@SuiteClasses(
    {
        SQLTypeTest.class,
        SQLDatabaseTest.class,
        MySQLCommandBuilderTest.class,
        QueryPackageTestSuite.class
    })
public class SQLPackageTestSuite {}
