package jp.lambdamagic.event;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import jp.lambdamagic.NullArgumentException;
import jp.lambdamagic.event.ProgressEvent;

public class ProgressEventTest {

	@Test(expected=NullArgumentException.class)
	public void ProgressEvent_mustThrowNullArgumentExceptionWhenNullSourceIsGiven() {
		new ProgressEvent(null, 1, 20);
	}
	
	@Test
	public void getSource_returnGivenSource() {
		ProgressEvent event = new ProgressEvent("source", 1, 20);
		assertThat(event.getSource(), is("source"));
	}

	@Test
	public void getCount_returnGivenCount() {
		ProgressEvent event = new ProgressEvent("source", 1, 20);
		assertThat(event.getCount(), is(1L));
	}
	
	@Test
	public void getTotalCount_returnGivenTotalCount() {
		ProgressEvent event = new ProgressEvent("source", 1, 20);
		assertThat(event.getTotalCount(), is(20L));
	}
	
	@Test
	public void getType_returnProgressEventType() {
		ProgressEvent event = new ProgressEvent("source", 1, 20);
		assertThat(event.getType(), is(ProgressEvent.TYPE));
	}
	
	@Test
	public void getPercentage_calculateProgressPercentage() {
		ProgressEvent event = new ProgressEvent("source", 1, 20);
		assertThat(event.getPercentage(), is(5F));
	}
	
}
