package lambdamagic.pipeline;

import static lambdamagic.testing.Assertion.*;

import java.util.Optional;

import org.junit.Test;

import lambdamagic.NullArgumentException;
import lambdamagic.testing.SampleValue;

public class PipelineTest {
	
	
	
	@Test
	public void testSuccess() {
		raiseNothing(() -> {
			Pipeline.from(() -> Optional.of(SampleValue.INTEGER))
					.to(number -> number + SampleValue.INTEGER).trim(30).filter(value -> true).execute();
		});
	}

	@Test
	public void testNullArgument() {
		//raiseException(() -> Pipeline.from(null), NoDataSourceException.class);

		raiseException(() -> {
			Pipeline.from(() -> Optional.of(SampleValue.INTEGER))
					.to((DataProcessor<Integer, Integer>)null);
		}, NullArgumentException.class);		
	}
	
	@Test
	public void testForkMethod() {
		
		Pipeline.from(() -> Optional.of(SampleValue.INTEGER))
				.to(number -> {
					assertEquals(SampleValue.INTEGER, number.intValue());
				})
				.trim(30)
				.execute();
	}
}