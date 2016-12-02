package lambdamagic.csv;

import java.util.ArrayList;
import java.util.Arrays;

public class CSVRow extends ArrayList<String> {

	private static final long serialVersionUID = -3080016804932002363L;
	
	public CSVRow(String... strings) {
		this.addAll(Arrays.asList(strings));
	}

	@Override
	public String toString() {
		return "CSVRow" + super.toString();
	}
}
