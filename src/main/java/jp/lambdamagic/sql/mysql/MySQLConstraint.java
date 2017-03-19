package jp.lambdamagic.sql.mysql;

import jp.lambdamagic.sql.SQLTable.Column.Constraint;

public final class MySQLConstraint {
    
    public static final Constraint PRIMARY_KEY = new Constraint("PRIMARY KEY");
    public static final Constraint AUTO_INCREMENT = new Constraint("AUTO_INCREMENT");
    public static final Constraint INDEX = new Constraint("INDEX");
    public static final Constraint UNIQUE = new Constraint("UNIQUE");
    public static final Constraint NOT_NULL = new Constraint("NOT NULL");
    
    private MySQLConstraint() {}
    
}
