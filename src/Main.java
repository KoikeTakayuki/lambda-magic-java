import java.io.IOException;
import java.util.List;

import lambdamagic.collection.iterator.Iterables;
import lambdamagic.csv.CSVDataSource;
import lambdamagic.csv.CSVWriter;
import lambdamagic.pipeline.DataSource;
import lambdamagic.pipeline.Pipeline;

public class Main {
	
	public static void main(String[] args) throws IOException {
		DataSource<List<String>> dataSource = new CSVDataSource("/Users/koiketakayuki/Desktop/KEN_ALL_ROME.CSV", "Shift_JIS");
		Pipeline.from(dataSource)
				.print()
				.execute();

		dataSource.close();
	}
}