import java.util.concurrent.CompletableFuture;

public class Main {
	
	public static void main(String[] args) throws InterruptedException {
		
		CompletableFuture.runAsync(() -> {
			try {
				Thread.sleep(5000);
				System.out.println("test");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}).join();
		
	}
}