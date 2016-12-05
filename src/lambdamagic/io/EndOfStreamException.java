package lambdamagic.io;

public class EndOfStreamException extends Exception {

	private static final long serialVersionUID = 794560722649741158L;

	public EndOfStreamException() {
		super("Attempted to read past the end of the stream");
	}
}
