package lambdamagic.dsl;

import lambdamagic.text.TextLocation;

public class Syntax<T> {
	
	private TextLocation location;
	private T expression;
	
	public TextLocation getLocation() {
		return location;
	}
	
	public T getExpression() {
		return expression;
	}
	
	public Syntax(TextLocation location, T expression) {
		this.location = location;
		this.expression = expression;
	}
}
