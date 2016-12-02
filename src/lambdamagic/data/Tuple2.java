package lambdamagic.data;

import java.io.Serializable;

public final class Tuple2<T1, T2> implements Serializable {

	private static final long serialVersionUID = 692990897984802517L;

	private T1 firstValue;
	private T2 secondValue;

	public T1 getFirstValue() {
		return firstValue;
	}

	public T2 getSecondValue() {
		return secondValue;
	}

	public Tuple2(T1 firstValue, T2 secondValue) {
		this.firstValue = firstValue;
		this.secondValue = secondValue;
	}

	@Override
	public String toString() {
		return "Tuple2(" + firstValue + ", "+ secondValue + ")";
	}
}