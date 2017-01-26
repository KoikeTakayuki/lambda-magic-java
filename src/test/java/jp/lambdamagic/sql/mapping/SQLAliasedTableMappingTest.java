package jp.lambdamagic.sql.mapping;

import static org.hamcrest.CoreMatchers.endsWith;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;

import jp.lambdamagic.NullArgumentException;

public class SQLAliasedTableMappingTest {

	@Test(expected=NullArgumentException.class)
	public void SQLAliasedTableMapping_mustThrowNullArgumentExceptionWhenNullAliasIsGiven() {
		new SQLAliasedTableMapping<>(null, new SQLTableMappingStub());
	}
	
	@Test(expected=NullArgumentException.class)
	public void SQLAliasedTableMapping_mustThrowNullArgumentExceptionWhenNullTableMappingIsGiven() {
		new SQLAliasedTableMapping<>("alias", null);
	}
	
	@Test
	public void getAlias_returnAlias() {
		SQLAliasedTableMapping<SQLTableMappingStub> sqlAliasedTableMapping = new SQLAliasedTableMapping<>("alias", new SQLTableMappingStub());
		assertThat(sqlAliasedTableMapping.getAlias(), startsWith("alias"));
	}
	
	@Test
	public void getDeclarationTableName_returnDeclarationTableNameWithAlias() {
		SQLAliasedTableMapping<SQLTableMappingStub> sqlAliasedTableMapping = new SQLAliasedTableMapping<>("alias", new SQLTableMappingStub());
		assertThat(sqlAliasedTableMapping.getDeclarationTableName(), startsWith("tableName AS alias"));
	}
	
	@Test
	public void getTableName_returnAliasedTableName() {
		SQLAliasedTableMapping<SQLTableMappingStub> sqlAliasedTableMapping = new SQLAliasedTableMapping<>("alias", new SQLTableMappingStub());
		assertThat(sqlAliasedTableMapping.getTableName(), startsWith("alias"));
		assertThat(sqlAliasedTableMapping.getTableName(), endsWith(".tableName"));
	}
	
	@Test
	public void getColumnName_returnAliasedColumnName() {
		SQLAliasedTableMapping<SQLTableMappingStub> sqlAliasedTableMapping = new SQLAliasedTableMapping<>("alias", new SQLTableMappingStub());
		assertThat(sqlAliasedTableMapping.getColumnName("test"), startsWith("alias"));
		assertThat(sqlAliasedTableMapping.getColumnName("test"), endsWith(".columnName"));
	}
	
	@Test
	public void getColumnNames_returnAliasedColumnNames() {
		SQLAliasedTableMapping<SQLTableMappingStub> sqlAliasedTableMapping = new SQLAliasedTableMapping<>("alias", new SQLTableMappingStub());
		List<String> columnNames = sqlAliasedTableMapping.getColumnNames();
		assertThat(columnNames.size(), is(1));
		
		String columnName = columnNames.get(0);
		assertThat(columnName, startsWith("alias"));
		assertThat(columnName, endsWith(".columnName"));
	}

}
