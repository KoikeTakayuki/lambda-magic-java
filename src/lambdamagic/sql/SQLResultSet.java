package lambdamagic.sql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import lambdamagic.NullArgumentException;
import lambdamagic.pipeline.DataSource;

public final class SQLResultSet implements DataSource<SQLResult> {
	
	@FunctionalInterface
	public static interface SQLResultSetConstructor {

		ResultSet create() throws SQLException;
	}
	
	private SQLResultSetConstructor constructor;
	private ResultSet resultSet;

	SQLResultSet(SQLResultSetConstructor constructor) {
		if (constructor == null) {
			throw new NullArgumentException("constructor");
		}

		this.constructor = constructor;
	}
	
	private ResultSet getResultSet() throws SQLException {
		if (resultSet == null) {
			resultSet = constructor.create();
		}

		return resultSet;
	}

	@Override
	public Optional<SQLResult> readData() {
		try {

			if (!getResultSet().isLast() && ((getResultSet().getRow() != 0) || getResultSet().isBeforeFirst())) {
				getResultSet().next();
				return Optional.of(new SQLResult(getResultSet()));
			}
			
			return Optional.empty();

		} catch (SQLException e) {
			return Optional.empty();
		}
	}
	
	@Override
	public void close() throws SQLException {
		resultSet.getStatement().close();
	}
	
}
