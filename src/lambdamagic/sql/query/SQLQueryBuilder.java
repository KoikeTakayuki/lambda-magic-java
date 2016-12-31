package lambdamagic.sql.query;

public interface SQLQueryBuilder<T extends SQLQuery> {

	T build();
}
