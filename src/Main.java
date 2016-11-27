import java.util.Optional;

import lambdamagic.pipeline.Pipeline;

public class Main {

	public static void main(String[] args) {
		
		Pipeline<Integer, String> p = Pipeline.from(() -> {
			return Optional.of(100);
		}).to(i -> {
			System.out.println(i);
			return i + 100;
		}).to(i -> {
			System.out.println(i);
			return i + 100;
		}).fork(i -> {
			System.out.println(i);
		}).to(i -> {
			System.out.println(i);
			System.out.println("finish");
			return "test";
		});
		
		p.execute();
	}
}
