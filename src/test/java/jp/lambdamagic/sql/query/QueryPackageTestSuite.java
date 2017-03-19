package jp.lambdamagic.sql.query;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import jp.lambdamagic.sql.query.condition.ComparisonExpressionTest;
import jp.lambdamagic.sql.query.condition.LogicExpressionTest;
import jp.lambdamagic.sql.query.condition.SQLJoinClauseTest;
import jp.lambdamagic.sql.query.condition.UnaryOperatorExpressionTest;

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
    UnaryOperatorExpressionTest.class,
    ComparisonExpressionTest.class,
    LogicExpressionTest.class,
    SQLJoinClauseTest.class
  })
public class QueryPackageTestSuite {}
