import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lambdamagic.pipeline.Pipeline;
import lambdamagic.sql.SQLDatabase;
import lambdamagic.sql.SQLResultSet;
import lambdamagic.sql.SQLTable;
import lambdamagic.sql.SQLTable.Column;
import lambdamagic.sql.mysql.MySQLConnection;
import lambdamagic.sql.mysql.MySQLConstraint;
import lambdamagic.sql.mysql.MySQLType;
import lambdamagic.sql.query.SQLSelectQueryBuilder;
import lambdamagic.sql.query.condition.SQLCondition;

public class Main {
	
	public static void main(String[] args) throws SQLException, Exception {
		
		
		try(MySQLConnection connection = new MySQLConnection("jdbc:mysql://127.0.0.1:8889", "root", "root")) {
			connection.setDebugOutput(System.out);
			connection.dropDatabase("test");
			connection.createDatabase(new SQLDatabase("test", "utf8_general_ci"));
			connection.useDatabase("test");
			
			
			List<Column> columns = new ArrayList<>();
				
			columns.add(new Column("id", MySQLType.INTEGER, MySQLConstraint.PRIMARY_KEY, MySQLConstraint.AUTO_INCREMENT));
			columns.add(new Column("name", MySQLType.TEXT));
			columns.add(new Column("password", MySQLType.PASSWORD));
				
			connection.createTable(new SQLTable("user", columns));
			
			
			Map<String, Object> values = new HashMap<>();
			values.put("name", "test");
			values.put("password", "asc");
			connection.insertInto("user", values);
			connection.insertInto("user", values);
			connection.insertInto("user", values);
			connection.insertInto("user", values);
			connection.insertInto("user", values);
			connection.insertInto("user", values);
			connection.insertInto("user", values);
			connection.insertInto("user", values);
			connection.insertInto("user", values);
			connection.insertInto("user", values);
			
			SQLResultSet select = connection.select(SQLSelectQueryBuilder.from("user").where(SQLCondition.GREATER_THAN("id", 3)).build());
			
			Pipeline.from(select)
					.to(r -> r.get("id").get())
					.print()
					.execute();
		}
		
	}
	
}