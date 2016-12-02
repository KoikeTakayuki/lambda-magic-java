package lambdamagic.parsing;

import lambdamagic.text.TextPosition;

public class ParseResult<T> {

	private T result;
	private TextPosition position;

	public T getResult() {
		return result;
	}

	public TextPosition getPosition() {
		return position;
	}

	public ParseResult(T result, TextPosition position) {
		this.result = result;
		this.position = position;
	}

	@Override
	public String toString() {
		return "Success " + position + " <" + result.getClass().getSimpleName() + ">" + result.toString();
	}
}