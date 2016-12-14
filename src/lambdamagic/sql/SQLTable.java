package lambdamagic.sql;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import lambdamagic.NullArgumentException;


public final class SQLTable {

	public final static class Column {

		public static final class Constraint {
			
			private String name;
			private Object value;

			public static Constraint DEFAULT(Object value) {
				return new Constraint("DEFAULT", value);
			}
			
			public String getName() {
				return name;
			}
		
			public Object getValue() {
				return value;
			}
			
			private Constraint(String name, Object value) {
				if (name == null)
					throw new NullArgumentException("name");

				this.name = name;
				this.value = value;
			}
		}
		
		private String name;
		private SQLType type;
		private Set<Constraint> constraints;
		
		public String getName() {
			return name;
		}

		
		public SQLType getType() {
			return type;
		}
		
		public Set<Constraint> getConstraints() {
			return constraints;
		}
		
		public Column(String name, SQLType type, Constraint... constraints) {
			this(name, type, new HashSet<Constraint>(Arrays.asList(constraints)));
		}
		
		public Column(String name, SQLType type, Set<Constraint> constraints) {
			this.name = name;
			this.type = type;
			
			if (constraints == null)
				throw new NullArgumentException("constraints");
			
			this.constraints = constraints;
		}
	}
	
	private String name;
	private Set<Column> columns;
	private List<String> customDeclarations;
	
	public String getName() {
		return name;
	}
	
	public Iterable<Column> getColumns() {
		return columns;
	}
	
	public boolean addColumn(Column column) {
		if (column == null)
			throw new NullArgumentException("column");
		
		return columns.add(column);
	}
	
	public Iterable<String> getCustomDeclarations() {
		return customDeclarations;
	}
	
	public void addCustomDeclaration(String customDeclaration) {
		if (customDeclaration == null)
			throw new NullArgumentException("customDeclaration");
	
		customDeclarations.add(customDeclaration);
	}
	
	public SQLTable(String name, Set<Column> columns) {
		if (name == null)
			throw new NullArgumentException("name");
		
		if (columns == null)
			throw new NullArgumentException("columns");
		
		this.name = name;
		this.columns = columns;
		this.customDeclarations = new ArrayList<String>();
	}
	
	public SQLTable(String name) {
		this(name, new LinkedHashSet<Column>());
	}
	
	@Override
	public int hashCode() {
		return name.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		
		if (!(obj instanceof SQLTable))
			return false;
		
		SQLTable other = (SQLTable) obj;
		return getName().equals(other.getName());
	}
}
