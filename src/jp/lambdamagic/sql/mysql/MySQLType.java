package jp.lambdamagic.sql.mysql;

import jp.lambdamagic.sql.SQLType;

public final class MySQLType {
	
	public static final SQLType SYMBOL = new SQLType("CHAR", 64);
	public static final SQLType BOOLEAN = new SQLType("BIT");
	public static final SQLType INTEGER = new SQLType("INT");
	public static final SQLType REAL = new SQLType("DOUBLE");
	public static final SQLType SHORT_TEXT = new SQLType("VARCHAR", 64);
	public static final SQLType TEXT = new SQLType("VARCHAR", 255);
	public static final SQLType LONG_TEXT = new SQLType("TEXT");
	public static final SQLType PASSWORD = new SQLType("CHAR", 32);
	public static final SQLType DATE = new SQLType("DATE");
	public static final SQLType TIME = new SQLType("TIME");
	public static final SQLType DATE_TIME = new SQLType("DATETIME");
	public static final SQLType BINARY_OBJECT = new SQLType("LONGBLOB");
	
	private MySQLType() {}
	
}
