package lambdamagic.sql.mapping;

import java.util.Collection;

public interface SQLColumnSetMapping {
	Collection<String> getColumnNames();
	String getColumnName(String id);
}
