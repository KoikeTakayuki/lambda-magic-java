package jp.lambdamagic.sql.query;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import jp.lambdamagic.NullArgumentException;
import jp.lambdamagic.sql.query.condition.SQLCondition;
import jp.lambdamagic.sql.query.condition.SQLJoinClause;

public class SQLUpdateQuery implements SQLConditionalQuery {
  
  private String tableName;
  private List<SQLJoinClause> joinClauses;
  private SQLCondition condition;
  private Map<String, Object> updateValues;
  
  SQLUpdateQuery(String tableName, List<SQLJoinClause> joinClauses,
      SQLCondition condition, Map<String, Object> updateValues) {

    if (tableName == null) {
      throw new NullArgumentException("tableName");
    }
    
    this.tableName = tableName;
    this.joinClauses = joinClauses;
    this.condition = condition;
    this.updateValues = updateValues;
  }

  @Override
  public String getTableName() {
    return tableName;
  }

  @Override
  public Optional<List<SQLJoinClause>> getJoinClauses() {
    if (joinClauses == null) {
      return Optional.empty();
    }

    return Optional.of(joinClauses);
  }

  @Override
  public Optional<SQLCondition> getCondition() {
    if (condition == null) {
      return Optional.empty();
    }

    return Optional.of(condition);
  }
  
  public Map<String, Object> getUpdateValues() {
    return updateValues;
  }

}
