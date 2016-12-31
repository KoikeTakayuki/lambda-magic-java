package lambdamagic.sql.query.builder;

import lambdamagic.sql.query.SQLQuery;

public interface SQLQueryBuilder<T extends SQLQuery> {

	T build();
}
