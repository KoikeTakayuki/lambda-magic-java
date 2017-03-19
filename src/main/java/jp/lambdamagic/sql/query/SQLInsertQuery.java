package jp.lambdamagic.sql.query;

import java.util.Map;

import jp.lambdamagic.NullArgumentException;

public class SQLInsertQuery implements SQLQuery {
  
  private String tableName;
  private Map<String, Object> insertValues;
  
  SQLInsertQuery(String tableName, Map<String, Object> insertValues) {
    if (tableName == null) {
      throw new NullArgumentException("tableName");
    }
    
    if (insertValues == null) {
      throw new NullArgumentException("insertValues");
    }
    
    this.tableName = tableName;
    this.insertValues = insertValues;
  }

  @Override
  public String getTableName() {
    return tableName;
  }
  
  public Map<String, Object> getInsertValues() {
    return insertValues;
  }
  
}
