package lambdamagic.sql.query;

import java.util.List;

import lambdamagic.sql.query.condition.SQLCondition;
import lambdamagic.sql.query.condition.SQLJoinClause;

public interface SQLConditionalQuery extends SQLQuery {

	List<SQLJoinClause> getJoinClauses();
	SQLCondition getCondition();
}
