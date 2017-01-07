import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.lambdamagic.pipeline.Pipeline;
import jp.lambdamagic.sql.SQLDatabase;
import jp.lambdamagic.sql.SQLResultSet;
import jp.lambdamagic.sql.SQLTable;
import jp.lambdamagic.sql.SQLTable.Column;
import jp.lambdamagic.sql.mysql.MySQLConnection;
import jp.lambdamagic.sql.mysql.MySQLConstraint;
import jp.lambdamagic.sql.mysql.MySQLType;
import jp.lambdamagic.sql.query.SQLSelectQueryBuilder;
import jp.lambdamagic.sql.query.condition.SQLCondition;

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
					.map(r -> r.get("id").get())
					.print()
					.run();
		}
		
	}
	
}