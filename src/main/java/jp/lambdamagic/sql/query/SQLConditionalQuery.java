package jp.lambdamagic.sql.query;

import java.util.List;
import java.util.Optional;

import jp.lambdamagic.sql.query.condition.SQLCondition;
import jp.lambdamagic.sql.query.condition.SQLJoinClause;

public interface SQLConditionalQuery extends SQLQuery {
  Optional<List<SQLJoinClause>> getJoinClauses();
  Optional<SQLCondition> getCondition();
}