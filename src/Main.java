import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;

import lambdamagic.csv.CSVDataSource;
import lambdamagic.json.JSONDataSource;
import lambdamagic.pipeline.DataSource;
import lambdamagic.pipeline.Pipeline;
import lambdamagic.web.http.service.rest.HttpRestService;

public class Main {
	
	public static void main(String[] args) throws IOException, ServletException {
		HttpRestService service = new RestAPISample();
		
		service.init();
	}
}