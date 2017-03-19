package jp.lambdamagic.sql.query;

import java.util.ArrayList;
import java.util.List;

import jp.lambdamagic.NullArgumentException;
import jp.lambdamagic.sql.query.condition.SQLCondition;
import jp.lambdamagic.sql.query.condition.SQLJoinClause;
import jp.lambdamagic.sql.query.condition.SQLJoinClause.JoinType;

public abstract class SQLConditionalQueryBuilder<T extends SQLQuery, S extends SQLConditionalQueryBuilder<T, ?>> implements SQLQueryBuilder<T> {

    protected String tableName;
    protected List<SQLJoinClause> joinClauses;
    protected SQLCondition condition;
    
    protected SQLConditionalQueryBuilder(String tableName) {
        if (tableName == null) {
            throw new NullArgumentException("tableName");
        }

        this.tableName = tableName;
    }
    
    @SuppressWarnings("unchecked")
    public S where(SQLCondition condition) {
        if (condition == null) {
            throw new NullArgumentException("condition");
        }

        this.condition = condition;
        return (S) this;
    }
    
    @SuppressWarnings("unchecked")
    public S innerJoin(String tableName, SQLCondition joinCondition) {
        addJoinClause(SQLJoinClause.JoinType.INNER, tableName, joinCondition);
        return (S)this;
    }
    
    @SuppressWarnings("unchecked")
    public S leftJoin(String tableName, SQLCondition joinCondition) {
        addJoinClause(SQLJoinClause.JoinType.LEFT, tableName, joinCondition);
        return (S)this;
    }
    
    @SuppressWarnings("unchecked")
    public S rightJoin(String tableName, SQLCondition joinCondition) {
        addJoinClause(SQLJoinClause.JoinType.RIGHT, tableName, joinCondition);
        return (S)this;
    }
    
    protected void addJoinClause(JoinType joinType, String tableName, SQLCondition joinCondition) {
        if (joinType == null) {
            throw new NullArgumentException("joinType");
        }
        
        if (tableName == null) {
            throw new NullArgumentException("tableName");
        }
        
        if (joinCondition == null) {
            throw new NullArgumentException("joinCondition");
        }
        
        if (joinClauses == null) {
            joinClauses = new ArrayList<>();
        }

        SQLJoinClause joinClause = new SQLJoinClause(joinType, tableName, joinCondition);
        joinClauses.add(joinClause);
    }
    
}
