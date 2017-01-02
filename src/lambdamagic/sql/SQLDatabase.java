package lambdamagic.sql;

import java.util.Optional;

import lambdamagic.NullArgumentException;

public final class SQLDatabase {

	private String name;
	private String collation;
	
	public SQLDatabase(String name, String collation) {
		if (name == null) {
			throw new NullArgumentException("name");
		}
		
		this.name = name;
		this.collation = collation;
	}
	
	public SQLDatabase(String name) {
		this(name, null);
	}
	
	public String getName() {
		return name;
	}

	public Optional<String> getCollation() {
		if (collation == null) {
			return Optional.empty();
		}
		
		return Optional.of(collation);
	}
	
}