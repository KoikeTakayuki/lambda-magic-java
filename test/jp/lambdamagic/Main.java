package jp.lambdamagic;

import java.util.Optional;

public class Main {

	public static void main(String[] args) {
		Optional<Object> empty = Optional.empty();
		empty.get();
	}
}
