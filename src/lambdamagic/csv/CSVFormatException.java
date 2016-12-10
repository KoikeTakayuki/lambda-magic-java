package lambdamagic.csv;

import lambdamagic.io.DataFormatException;
import lambdamagic.text.TextPosition;

public class CSVFormatException extends DataFormatException {

	private static final long serialVersionUID = 634559429018164809L;

	public CSVFormatException(TextPosition position) {
		super("Invalid CSV format at " + position);
	}
}
