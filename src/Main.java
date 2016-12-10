import java.io.IOException;
import java.util.List;

import lambdamagic.csv.CSVDataSource;
import lambdamagic.csv.CSVWriter;
import lambdamagic.json.JSONWriter;
import lambdamagic.pipeline.DataSource;
import lambdamagic.pipeline.Pipeline;

public class Main {
	
	public static void main(String[] args) throws IOException {
		DataSource<List<String>> dataSource = new CSVDataSource("/Users/koiketakayuki/Desktop/testdata.csv");
		Pipeline.from(dataSource)
				.print()
				.trim(30)
				.to(new CSVWriter("/Users/koiketakayuki/Desktop/test.csv"))
				.execute();

		dataSource.close();
	}
}