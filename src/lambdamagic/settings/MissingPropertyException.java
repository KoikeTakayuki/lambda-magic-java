package lambdamagic.settings;

public class MissingPropertyException extends Exception {

	private static final long serialVersionUID = -6940515300867954078L;

	public MissingPropertyException(String propertyName) {
		super(String.format("Missing required property \"%s\".", propertyName));
	}
}
