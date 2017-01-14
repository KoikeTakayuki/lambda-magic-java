package jp.lambdamagic.sql.query;

import static org.junit.Assert.*;

import org.junit.Test;

import jp.lambdamagic.sql.mysql.MySQLCommandBuilder;
import jp.lambdamagic.sql.query.condition.SQLCondition;

public class SQLDeleteQueryBuilderTest {

	@Test
	public void test() {
		SQLDeleteQuery query = SQLDeleteQueryBuilder.deleteFrom("test")
													.where(SQLCondition.contain("name", "test"))
													.innerJoin("test2", SQLCondition.equalTo("name", "name"))
													.innerJoin("test3", SQLCondition.equalTo("name", "name"))
													.build();
		
		MySQLCommandBuilder cb = new MySQLCommandBuilder();
		System.out.println(cb.buildDeleteCommand(query));
	}

}
