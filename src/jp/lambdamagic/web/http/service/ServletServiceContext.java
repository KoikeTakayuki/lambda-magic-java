package jp.lambdamagic.web.http.service;

import java.io.InputStream;
import java.util.Date;

import javax.servlet.ServletContext;

import jp.lambdamagic.NullArgumentException;
import jp.lambdamagic.event.log.LoggedEventType;


public class ServletServiceContext implements ServiceContext {

	private ServletContext wrapped;
		
	public ServletServiceContext(ServletContext wrapped) {
		if (wrapped == null) {
			throw new NullArgumentException("wrapped");
		}
		
		this.wrapped = wrapped;
	}

	@Override
	public void log(LoggedEventType type, String format, Object... args) {
		String message = String.format("%tc [%s] %s%n", new Date(), type, String.format(format, args));
		System.err.println(message);
		wrapped.log(message);
	}

	@Override
	public String getResourceAbsolutePath(String path) {
		return wrapped.getRealPath(path);
	}

	@Override
	public InputStream getResourceAsStream(String path) {
		InputStream stream = wrapped.getResourceAsStream(path);
		return stream;
	}
	
}
