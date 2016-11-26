package lambdamagic.web.http;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AsyncHttpClient {

	private HttpClient httpClient;
	private ExecutorService executor;
	
	private static int DEFAULT_THREAD_POOL_SIZE = 5;

	public AsyncHttpClient() {
		this(DEFAULT_THREAD_POOL_SIZE);
	}
	
	public AsyncHttpClient(int threadPoolSize) {
		this.httpClient = new HttpClient();
		this.executor = Executors.newFixedThreadPool(threadPoolSize);
	}

	public CompletableFuture<HttpResponse> getAsync(String urlString) {
		return getAsync(urlString, Collections.<String, String>emptyMap());
	}
	
	public CompletableFuture<HttpResponse> getAsync(String urlString, Map<String, String> requestHeaderFields) {
		return sendRequestAsync("get", urlString, requestHeaderFields);
	}
	
	public CompletableFuture<HttpResponse> postAsync(String urlString, Map<String, String> requestHeaderFields) {
		return sendRequestAsync("post", urlString, requestHeaderFields);
	}
	
	public CompletableFuture<HttpResponse> putAsync(String urlString, Map<String, String> requestHeaderFields) {
		return sendRequestAsync("put", urlString, requestHeaderFields);
	}
	
	public CompletableFuture<HttpResponse> deleteAsync(String urlString, Map<String, String> requestHeaderFields) {
		return sendRequestAsync("delete", urlString, requestHeaderFields);
	}

	private CompletableFuture<HttpResponse> sendRequestAsync(String methodName, String urlString, Map<String, String> requestHeaderFields) {
		CompletableFuture<HttpResponse> result = new CompletableFuture<HttpResponse>();

		executor.submit(() -> {
			try {
				System.out.println(methodName);
				HttpResponse response = (HttpResponse)HttpClient.class.getMethod(methodName, String.class, Map.class).invoke(httpClient, urlString, requestHeaderFields);
				result.complete(response);
			} catch (Exception ex) {
				result.completeExceptionally(ex);
			}
		});

		return result;
	}
	
	public void shutdown() {
		this.executor.shutdown();
	}
}
