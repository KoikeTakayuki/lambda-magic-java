package jp.lambdamagic.sql.query;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.Test;

import jp.lambdamagic.NullArgumentException;
import jp.lambdamagic.sql.query.condition.SQLCondition;
import jp.lambdamagic.sql.query.condition.SQLJoinClause;
import jp.lambdamagic.sql.query.condition.UnaryOperatorExpression.IsNullExpression;

public class SQLUpdateQueryTest {

	@Test(expected=NullArgumentException.class)
	public void SQLDeleteQuery_mustThrowNullArgumentExceptionWhenNullTableNameIsGiven() {
		Map<String, Object> updateValues = new HashMap<>();
		new SQLUpdateQuery(null, new ArrayList<>(), SQLCondition.isNull("name"), updateValues);
	}
	
	@Test
	public void SQLDeleteQuery_acceptNullJoinClauses() {
		Map<String, Object> updateValues = new HashMap<>();
		new SQLUpdateQuery("test", null, SQLCondition.isNull("name"), updateValues);
	}
	
	@Test
	public void SQLDeleteQuery_acceptNullCondition() {
		Map<String, Object> updateValues = new HashMap<>();
		new SQLUpdateQuery("test", new ArrayList<>(), null, updateValues);
	}
	
	@Test
	public void getTableName_returnGivenTableName() {
		Map<String, Object> updateValues = new HashMap<>();
		SQLUpdateQuery query = new SQLUpdateQuery("test", new ArrayList<>(), SQLCondition.isNull("name"), updateValues);
		String tableName = query.getTableName();
		
		assertThat(tableName, is("test"));
	}
	
	@Test
	public void getJoinClauses_returnJoinClausesIfExists() {
		Map<String, Object> updateValues = new HashMap<>();
		SQLUpdateQuery query = new SQLUpdateQuery("test", new ArrayList<>(), SQLCondition.isNull("name"), updateValues);
		Optional<List<SQLJoinClause>> joinClauses = query.getJoinClauses();
		
		assertThat(joinClauses.isPresent(), is(true));
		assertThat(joinClauses.get(), is(new ArrayList<>()));
	}
	
	@Test
	public void getJoinClauses_returnEmptyIfNotExist() {
		Map<String, Object> updateValues = new HashMap<>();
		SQLUpdateQuery query = new SQLUpdateQuery("test", null, SQLCondition.isNull("name"), updateValues);
		Optional<List<SQLJoinClause>> joinClauses = query.getJoinClauses();
		
		assertThat(joinClauses.isPresent(), is(false));
	}
	
	@Test
	public void getCondition_returnConditionIfExists() {
		Map<String, Object> updateValues = new HashMap<>();
		SQLUpdateQuery query = new SQLUpdateQuery("test", new ArrayList<>(), SQLCondition.isNull("name"), updateValues);
		Optional<SQLCondition> condition = query.getCondition();
		
		assertThat(condition.isPresent(), is(true));
		assertThat(condition.get(), is(instanceOf(IsNullExpression.class)));
	}
	
	@Test
	public void getCondition_returnEmptyIfNotExists() {
		Map<String, Object> updateValues = new HashMap<>();
		SQLUpdateQuery query = new SQLUpdateQuery("test", new ArrayList<>(), null, updateValues);
		Optional<SQLCondition> condition = query.getCondition();
		
		assertThat(condition.isPresent(), is(false));
	}
	
	@Test
	public void getUpdateValues_returnGivenUpdateValues() {
		Map<String, Object> updateValues = new HashMap<>();
		SQLUpdateQuery query = new SQLUpdateQuery("test", new ArrayList<>(), null, updateValues);
		
		assertThat(query.getUpdateValues(), is(updateValues));
	}

}
