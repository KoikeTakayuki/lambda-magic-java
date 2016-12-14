package lambdamagic.sql;

import lambdamagic.NullArgumentException;

public final class SQLDatabase {

	private String name;
	private String collation;
	
	public String getName() {
		return name;
	}

	public String getCollation() {
		return collation;
	}
	
	public SQLDatabase(String name, String collation) {
		if (name == null)
			throw new NullArgumentException("name");
		
		this.name = name;
		this.collation = collation;
	}
	
	public SQLDatabase(String name) {
		this(name, null);
	}
}