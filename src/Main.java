import java.io.IOException;
import java.util.List;

import lambdamagic.csv.CSVDataSource;
import lambdamagic.json.JSONDataSource;
import lambdamagic.pipeline.DataSource;
import lambdamagic.pipeline.Pipeline;

public class Main {
	
	public static void main(String[] args) throws IOException {
		DataSource<Object> dataSource = JSONDataSource.fromString("{\"test\":\"test\", \"ok\":true, \"a\":{}}");
		Pipeline.from(dataSource)
				.print()
				.trim(30)
				.execute();

		dataSource.close();
	}
}