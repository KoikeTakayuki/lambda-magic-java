package jp.lambdamagic.sql;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jp.lambdamagic.NullArgumentException;


public final class SQLTable {

  public final static class Column {

    public static final class Constraint {
      
      private String name;
      private Object value;
      
      public Constraint(String name, Object value) {
        if (name == null) {
          throw new NullArgumentException("name");
        }
        
        this.name = name;
        this.value = value;
      }
      
      public Constraint(String name) {
        this(name, null);
      }

      public static Constraint DEFAULT(Object value) {
        return new Constraint("DEFAULT", value);
      }
      
      public String getName() {
        return name;
      }
    
      public Object getValue() {
        return value;
      }

    }
    
    private String name;
    private SQLType type;
    private List<Constraint> constraints;
    
    public Column(String name, SQLType type, Constraint... constraints) {
      this(name, type, Arrays.asList(constraints));
    }
    
    public Column(String name, SQLType type, List<Constraint> constraints) {
      if (name == null) {
        throw new NullArgumentException("name");
      }
      
      if (type == null) {
        throw new NullArgumentException("type");
      }
      
      if (constraints == null) {
        throw new NullArgumentException("constraints");
      }
      
      this.name = name;
      this.type = type;
      this.constraints = constraints;
    }
    
    public String getName() {
      return name;
    }
    
    public SQLType getType() {
      return type;
    }
    
    public List<Constraint> getConstraints() {
      return constraints;
    }

  }
  
  private String name;
  private List<Column> columns;
  private List<String> customDeclarations;
  
  public SQLTable(String name, List<Column> columns) {
    if (name == null) {
      throw new NullArgumentException("name");
    }
    
    if (columns == null) {
      throw new NullArgumentException("columns");
    }
    
    if (columns.size() < 1) {
      throw new IllegalStateException("SQLTable should have one column definition at least");
    }
    
    this.name = name;
    this.columns = columns;
    this.customDeclarations = new ArrayList<String>();
  }
  
  public String getName() {
    return name;
  }
  
  public Iterable<Column> getColumns() {
    return columns;
  }
  
  public boolean addColumn(Column column) {
    if (column == null) {
      throw new NullArgumentException("column");
    }
    
    return columns.add(column);
  }
  
  public Iterable<String> getCustomDeclarations() {
    return customDeclarations;
  }
  
  public void addCustomDeclaration(String customDeclaration) {
    if (customDeclaration == null) {
      throw new NullArgumentException("customDeclaration");
    }
    
    customDeclarations.add(customDeclaration);
  }
  
  @Override
  public int hashCode() {
    return name.hashCode();
  }
  
  @Override
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }
    
    if (!(obj instanceof SQLTable)) {
      return false;
    }
    
    SQLTable other = (SQLTable) obj;
    return getName().equals(other.getName());
  }
  
}
