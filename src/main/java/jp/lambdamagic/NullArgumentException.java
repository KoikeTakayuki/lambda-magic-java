package jp.lambdamagic;

public final class NullArgumentException extends InvalidArgumentException {

	private static final long serialVersionUID = 4018606724641781062L;

	public NullArgumentException(String parameterName) {
		super(parameterName, "parameter cannot be null");
	}
	
}
