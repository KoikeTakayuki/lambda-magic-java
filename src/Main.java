import java.util.concurrent.CompletableFuture;

import lambdamagic.sql.mysql.MySQLCommandBuilder;
import lambdamagic.sql.query.SQLDeleteQuery;
import lambdamagic.sql.query.condition.SQLCondition;

public class Main {
	
	public static void main(String[] args) {
		
		
		MySQLCommandBuilder builder = new MySQLCommandBuilder();
		
		String query = builder.buildDeleteFromCommand(SQLDeleteQuery.from("test").where(SQLCondition.AND(SQLCondition.CONTAIN("Name", "Koike"), SQLCondition.GREATER_OR_EQUAL_TO("Id", 1))));
		System.out.println(query);
	}
	
}