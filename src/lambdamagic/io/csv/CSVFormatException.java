package lambdamagic.io.csv;

import lambdamagic.io.DataFormatException;

public class CSVFormatException extends DataFormatException {

	private static final long serialVersionUID = 634559429018164809L;

	public CSVFormatException(int line, int offset) {
		super(String.format("Invalid CSV format at line %d, offset %d.", line, offset));
	}

	protected CSVFormatException(String message) {
		super(message);
	}
}
