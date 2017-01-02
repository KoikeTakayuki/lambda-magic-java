package lambdamagic;

public class InvalidArgumentException extends IllegalArgumentException {

	private static final long serialVersionUID = 3288398416175201103L;

	public InvalidArgumentException(String parameterName, String message) {
		super(parameterName + " - " + message);
	}
	
}
