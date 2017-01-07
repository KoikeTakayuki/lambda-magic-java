package jp.lambdamagic;

public final class OutOfRangeArgumentException extends InvalidArgumentException {

	private static final long serialVersionUID = -7619827208754153514L;

	public OutOfRangeArgumentException(String parameterName, String failedAssertion) {
		super(parameterName, "parameter is out of range according, failed assertion \"" + failedAssertion + "\".");
	}
	
}
