package jp.lambdamagic.sql.mapping;

import java.util.Collection;
import java.util.stream.Collectors;

import jp.lambdamagic.NullArgumentException;


public final class SQLAliasedTableMapping<T extends SQLTableMapping> implements SQLTableMapping {

	private static final String ALIAS_DECLARATION_SEPARATOR = " AS ";
	private static final String ALIAS_SEPARATOR = ".";
	
	private static int uidCounter;
	
	private String alias;
	private T baseObject;
	
	public SQLAliasedTableMapping(String alias, T baseObject) {
		if (baseObject == null) {
			throw new NullArgumentException("baseObject");
		}

		setAlias(alias);
		this.baseObject = baseObject;
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
	
	public T getBaseObject() {
		return baseObject;
	}

	@Override
	public String getDeclarationTableName() {
		return baseObject.getTableName() + ALIAS_DECLARATION_SEPARATOR + getAlias();
	}
	
	@Override
	public String getTableName() {
		return getAlias() + ALIAS_SEPARATOR + baseObject.getTableName();
	}

	@Override
	public String getColumnName(String id) {
		return getAlias() + ALIAS_SEPARATOR + baseObject.getColumnName(id);
	}
	
	@Override
	public Collection<String> getColumnNames() {
		return baseObject.getColumnNames().stream().map(columnName -> {
			return getAlias() + ALIAS_SEPARATOR + columnName;
		}).collect(Collectors.toList());
	}
	
}

