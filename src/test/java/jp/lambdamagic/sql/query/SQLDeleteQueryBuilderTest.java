package jp.lambdamagic.sql.query;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertThat;

import java.util.Optional;

import org.junit.Test;

import jp.lambdamagic.sql.query.condition.SQLCondition;
import jp.lambdamagic.sql.query.condition.SQLJoinClause;
import jp.lambdamagic.sql.query.condition.ComparisonExpression.EqualToExpression;

public class SQLDeleteQueryBuilderTest {

  @Test
  public void build_buildDeleteQuery() {
    SQLCondition testCondition = SQLCondition.contain("name", "test");
    SQLDeleteQuery query = SQLDeleteQueryBuilder.deleteFrom("test")
                          .where(testCondition)
                          .innerJoin("test2", SQLCondition.equalTo("name1", "name2"))
                          .leftJoin("test3", SQLCondition.equalTo("name3", "name4"))
                          .build();
    
    assertThat(query.getTableName(), is("test"));
    
    Optional<SQLCondition> condition = query.getCondition();
    assertThat(condition.isPresent(), is(true));
    assertThat(condition.get(), is(testCondition));
    
    assertThat(query.getJoinClauses().isPresent(), is(true));
    assertThat(query.getJoinClauses().get().size(), is(2));
    
    SQLJoinClause clause1 = query.getJoinClauses().get().get(0);
    SQLJoinClause clause2 = query.getJoinClauses().get().get(1);
    
    assertThat(clause1.getTableName(), is("test2"));
    assertThat(clause1.getJoinType(), is(SQLJoinClause.JoinType.INNER));
    assertThat(clause1.getCondition(), is(instanceOf(EqualToExpression.class)));
    EqualToExpression joinClauseCondition1 = (EqualToExpression)clause1.getCondition();
    assertThat(joinClauseCondition1.getFirstOperand(), is("name1"));
    assertThat(joinClauseCondition1.getSecondOperand(), is("name2"));
    
    assertThat(clause2.getTableName(), is("test3"));
    assertThat(clause2.getJoinType(), is(SQLJoinClause.JoinType.LEFT));
    assertThat(clause1.getCondition(), is(instanceOf(EqualToExpression.class)));
    EqualToExpression joinClauseCondition2 = (EqualToExpression)clause2.getCondition();
    assertThat(joinClauseCondition2.getFirstOperand(), is("name3"));
    assertThat(joinClauseCondition2.getSecondOperand(), is("name4"));
  }

}
