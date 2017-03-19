package jp.lambdamagic.sql.query;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;

import jp.lambdamagic.NullArgumentException;
import jp.lambdamagic.sql.query.SQLDeleteQuery;
import jp.lambdamagic.sql.query.condition.SQLCondition;
import jp.lambdamagic.sql.query.condition.SQLJoinClause;
import jp.lambdamagic.sql.query.condition.UnaryOperatorExpression.IsNullExpression;

public class SQLDeleteQueryTest {

  @Test(expected=NullArgumentException.class)
  public void SQLDeleteQuery_mustThrowNullArgumentExceptionWhenNullTableNameIsGiven() {
    new SQLDeleteQuery(null, new ArrayList<>(), SQLCondition.isNull("name"));
  }
  
  @Test
  public void SQLDeleteQuery_acceptNullJoinClauses() {
    new SQLDeleteQuery("test", null, SQLCondition.isNull("name"));
  }
  
  @Test
  public void SQLDeleteQuery_acceptNullCondition() {
    new SQLDeleteQuery("test", new ArrayList<>(), null);
  }
  
  @Test
  public void getTableName_returnGivenTableName() {
    SQLDeleteQuery query = new SQLDeleteQuery("test", new ArrayList<>(), SQLCondition.isNull("name"));
    String tableName = query.getTableName();
    
    assertThat(tableName, is("test"));
  }
  
  @Test
  public void getJoinClauses_returnJoinClausesIfExists() {
    SQLDeleteQuery query = new SQLDeleteQuery("test", new ArrayList<>(), SQLCondition.isNull("name"));
    Optional<List<SQLJoinClause>> joinClauses = query.getJoinClauses();
    
    assertThat(joinClauses.isPresent(), is(true));
    assertThat(joinClauses.get(), is(new ArrayList<>()));
  }
  
  @Test
  public void getJoinClauses_returnEmptyIfNotExists() {
    SQLDeleteQuery query = new SQLDeleteQuery("test", null, SQLCondition.isNull("name"));
    Optional<List<SQLJoinClause>> joinClauses = query.getJoinClauses();
    
    assertThat(joinClauses.isPresent(), is(false));
  }
  
  @Test
  public void getCondition_returnConditionIfExists() {
    SQLDeleteQuery query = new SQLDeleteQuery("test", new ArrayList<>(), SQLCondition.isNull("name"));
    Optional<SQLCondition> condition = query.getCondition();
    
    assertThat(condition.isPresent(), is(true));
    assertThat(condition.get(), is(instanceOf(IsNullExpression.class)));
  }
  
  @Test
  public void getCondition_returnEmptyIfNotExists() {
    SQLDeleteQuery query = new SQLDeleteQuery("test", new ArrayList<>(), null);
    Optional<SQLCondition> condition = query.getCondition();
    
    assertThat(condition.isPresent(), is(false));
  }

}
