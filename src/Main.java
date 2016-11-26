import lambdamagic.web.http.AsyncHttpClient;

public class Main {

	public static void main(String[] args) {
		
		
		AsyncHttpClient httpClient = new AsyncHttpClient();
		
		httpClient.getAsync("http://ekiten.jp").thenAcceptAsync((result) -> {
			System.out.println(result.getText());
			httpClient.shutdown();
		}).exceptionally(e -> {
			e.printStackTrace();
			return null;
		});
		
//		httpClient.shutdown();
	}
}
