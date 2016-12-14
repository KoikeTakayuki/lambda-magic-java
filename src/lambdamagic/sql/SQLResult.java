package lambdamagic.sql;

import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import lambdamagic.data.Queryable;


public class SQLResult implements Queryable<String, Object> {

	private ResultSet resultSet;

	SQLResult(ResultSet resultSet) {
		this.resultSet = resultSet;
	}

	public Optional<Object> get(int columnIndex) {
		try {
			return Optional.of(resultSet.getObject(columnIndex + 1));
		} catch (SQLException e) {
			return Optional.empty();
		}
	}

	@Override
	public Optional<Object> get(String columnName) {
		try {
			return Optional.of(resultSet.getObject(columnName));
		} catch (SQLException e) {
			return Optional.empty();
		}
	}

	public InputStream getInputStream(String columnName) throws SQLException {
		return resultSet.getBinaryStream(columnName);
	}
}
