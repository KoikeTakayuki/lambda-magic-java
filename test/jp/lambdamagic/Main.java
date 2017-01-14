package jp.lambdamagic;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

import jp.lambdamagic.text.Encodings;
import jp.lambdamagic.web.http.AsyncHttpClient;
import jp.lambdamagic.web.http.HttpResponse;

public class Main {

	public static void main(String[] args) {
		AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
		CompletableFuture<HttpResponse> res = asyncHttpClient.getAsync("http://ekiten.jp");
		res.thenAccept(r -> {
			try {
				System.out.println(r.getText(Encodings.EUC_JP));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
	}
}
