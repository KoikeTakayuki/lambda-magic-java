package lambdamagic.sql.mysql;

import java.sql.SQLException;

import javax.sql.DataSource;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import lambdamagic.NullArgumentException;
import lambdamagic.sql.SQLCommandBuilder;
import lambdamagic.sql.SQLConnection;


public final class MySQLConnection extends SQLConnection {

	private static final SQLCommandBuilder COMMAND_BUILDER = new MySQLCommandBuilder();
	
	private static DataSource createDataSource(String url, String userName, String password) {
		if (url == null)
			throw new NullArgumentException("url");
		
		if (userName == null)
			throw new NullArgumentException("userName");
		
		if (password == null)
			throw new NullArgumentException("password");

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
