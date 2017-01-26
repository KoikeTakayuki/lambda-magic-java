package jp.lambdamagic.sql.mapping;

import java.util.List;
import java.util.stream.Collectors;

import jp.lambdamagic.NullArgumentException;

public final class SQLAliasedTableMapping<T extends SQLTableMapping> implements SQLTableMapping {

	private static final String ALIAS_DECLARATION_SEPARATOR = " AS ";
	private static final String ALIAS_SEPARATOR = ".";
	
	private static int uidCounter;
	
	private String alias;
	private T wrapped;
	
	public SQLAliasedTableMapping(String alias, T wrapped) {
		if (wrapped == null) {
			throw new NullArgumentException("baseObject");
		}

		setAlias(alias);
		this.wrapped = wrapped;
	}
	
	public String getAlias() {
		return alias;
	}
	
	private void setAlias(String alias) {
		if (alias == null) {
			throw new NullArgumentException("alias");
		}
		
		this.alias = alias + (uidCounter = (uidCounter + 1) % 1000);
	}

	@Override
	public String getDeclarationTableName() {
		return wrapped.getTableName() + ALIAS_DECLARATION_SEPARATOR + getAlias();
	}
	
	@Override
	public String getTableName() {
		return getAlias() + ALIAS_SEPARATOR + wrapped.getTableName();
	}

	@Override
	public String getColumnName(String id) {
		return getAlias() + ALIAS_SEPARATOR + wrapped.getColumnName(id);
	}
	
	@Override
	public List<String> getColumnNames() {
		return wrapped.getColumnNames().stream().map(columnName -> {
			return getAlias() + ALIAS_SEPARATOR + columnName;
		}).collect(Collectors.toList());
	}
	
}

