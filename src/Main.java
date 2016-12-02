
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;

import lambdamagic.csv.CSVRow;
import lambdamagic.csv.CSVWriter;
import lambdamagic.pipeline.Pipeline;

public class Main {

	public static void main(String[] args) throws FileNotFoundException, IOException {

		Pipeline.from(() -> Optional.of(new CSVRow("test", "test2", "\"ok\"")))
			.trim(20)
			.to(new CSVWriter("/Users/koiketakayuki/Desktop/test.csv"))
			.print()
			.execute();
			
	}
}
