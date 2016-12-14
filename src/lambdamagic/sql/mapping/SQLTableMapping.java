package lambdamagic.sql.mapping;

public interface SQLTableMapping extends SQLColumnSetMapping {

	String getDeclarationTableName();
	String getTableName();
}
