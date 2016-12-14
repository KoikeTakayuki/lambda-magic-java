package lambdamagic.sql.mysql;

import java.sql.SQLException;

import javax.sql.DataSource;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import lambdamagic.sql.SQLConnection;


public final class MySQLConnection extends SQLConnection<MySQLCommandBuilder> {

	private static final MySQLCommandBuilder COMMAND_BUILDER = new MySQLCommandBuilder();
	
	private static DataSource createDataSource(String url, String userName, String password) {
		MysqlDataSource dataSource = new MysqlDataSource();
		
		dataSource.setUrl(url);
		dataSource.setUser(userName);
		dataSource.setPassword(password);
		return dataSource;
	}

	
	public MySQLConnection(DataSource dataSource) throws SQLException {
		super(dataSource, COMMAND_BUILDER);
	}
	
	public MySQLConnection(String url, String userName, String password) throws SQLException {
		this(createDataSource(url, userName, password));
	}
}
