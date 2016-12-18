package lambdamagic.web.http.service.rest;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.Reader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.zip.GZIPOutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lambdamagic.InvalidArgumentException;
import lambdamagic.NullArgumentException;
import lambdamagic.io.DataFormatException;
import lambdamagic.web.http.HttpHeaderField;
import lambdamagic.web.http.HttpHeaderFieldValue;
import lambdamagic.web.http.HttpMethod;
import lambdamagic.web.serialization.DataSerializer;
import lambdamagic.web.serialization.ObjectWriter;
import lambdamagic.web.serialization.json.JSONDataSerializer;
import lambdamagic.web.serialization.text.PlainTextDataSerializer;

public abstract class HttpRestService extends HttpServlet {

	private static final long serialVersionUID = 4155762293913898421L;

	public static final String EXCEPTION_CLASS_NAME_MESSAGE_SEPARATOR = "/";
	public static final DataSerializer DEFAULT_QUERY_PARAMETER_DATA_SERIALIZER = new PlainTextDataSerializer();
	public static final DataSerializer DEFAULT_DATA_SERIALIZER = new JSONDataSerializer();
	
	private static final Map<Class<?>, Class<?>> PRIMITIVE_TYPE_MAP = new HashMap<Class<?>, Class<?>>() {

		private static final long serialVersionUID = -5114557329231774346L;

		{
			put(boolean.class, Boolean.class);
			put(byte.class, Byte.class);
			put(short.class, Short.class);
			put(int.class, Integer.class);
			put(long.class, Long.class);
			put(char.class, Character.class);
			put(float.class, Float.class);
			put(double.class, Double.class);
		}
	};
	
	private ThreadLocal<HttpServletRequest> threadLocalRequest = new ThreadLocal<HttpServletRequest>();
	private ThreadLocal<HttpServletResponse> threadLocalResponse = new ThreadLocal<HttpServletResponse>();
	private Map<HttpMethod, Map<String, HttpRestHandler>> methodHandlerMap;

	private PrintStream debugOutput;
	
	protected DataSerializer getQueryParameterDataSerializer() {
		return DEFAULT_QUERY_PARAMETER_DATA_SERIALIZER;
	}
	
	protected DataSerializer getDataSerializer() {
		return DEFAULT_DATA_SERIALIZER;
	}
	
	public void setDebugOutput(PrintStream debugOutput) {
		this.debugOutput = debugOutput;
	}
	
	public HttpRestService() {
		this.methodHandlerMap = new HashMap<HttpMethod, Map<String, HttpRestHandler>>();
	}
	
	public void registerOperation(HttpRestOperation operation) throws NoSuchMethodException, SecurityException {
		if (operation == null)
			throw new NullArgumentException("operation");
		
		Map<String, HttpRestHandler> pathHandlerMap = methodHandlerMap.get(operation.getMethod());

		if (pathHandlerMap == null) {
			pathHandlerMap = new HashMap<String, HttpRestHandler>();
			methodHandlerMap.put(operation.getMethod(), pathHandlerMap);
		}

		pathHandlerMap.put(operation.getPath(), createHttpRestHandler(operation));
	}
	
	private HttpRestHandler createHttpRestHandler(final HttpRestOperation operation) throws NoSuchMethodException, SecurityException {
		if (operation == null)
			throw new NullArgumentException("operation");
		
		final HttpRestParameter[] parameters = operation.getParameters();
		final Class<?>[] parameterTypes = new Class<?>[parameters.length];
		
		for (int i = 0; i < parameterTypes.length; ++i)
			parameterTypes[i] = parameters[i].getType();
		
		final Method APIMethod = getClass().getMethod(operation.getName(), parameterTypes);
		
		return (request, response) -> {
			try {
				try {
					writeHandlerInvocationResult(response, APIMethod, translateArguments(request, operation.getMethod(), parameters));
				}
				catch (Throwable ex) {
					String className = ex.getClass().getName();
					String message = (ex.getMessage() != null) ? ex.getMessage() : "";
					String exceptionMessage = className + EXCEPTION_CLASS_NAME_MESSAGE_SEPARATOR + message;
					
					response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
					response.setCharacterEncoding(getDataSerializer().getEncoding());
					response.setContentType(getDataSerializer().getMimeType());
					
					String contentEncoding = response.getHeader(HttpHeaderField.CONTENT_ENCODING);
					boolean compressed = HttpHeaderFieldValue.CONTENT_ENCODING_GZIP.equals(contentEncoding);
					OutputStream outputStream = compressed ? new GZIPOutputStream(response.getOutputStream(), true) : response.getOutputStream();
				
					outputStream.write(exceptionMessage.getBytes(getDataSerializer().getEncoding()));
					outputStream.flush();
					outputStream.close();
					
					if (debugOutput != null)
						ex.printStackTrace(debugOutput);
				}
			}
			catch (Throwable ex) {
				throw new ServletException(ex);
			}
		};
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(HttpMethod.GET, request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(HttpMethod.POST, request, response);
	}
	
	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(HttpMethod.DELETE, request, response);
	}
	
	private Optional<HttpRestHandler> getHandler(HttpMethod method, String path) {
		Map<String, HttpRestHandler> handlers = methodHandlerMap.get(method);

		if (handlers == null)
			return Optional.empty();

		HttpRestHandler handler = handlers.get(path);
		
		if (handler == null)
			return Optional.empty();
		
		
		return Optional.of(handler);
	}
	
	
	private Object[] translateArguments(HttpServletRequest req, HttpMethod method, HttpRestParameter[] parameters) throws UnsupportedEncodingException {
		Object[] arguments = new Object[parameters.length];
	
		req.setCharacterEncoding(getDataSerializer().getEncoding());
		
		for (int i = 0; i < parameters.length; ++i) {
			HttpRestParameter parameter = parameters[i];
			
			String parameterStringValue = req.getParameter(parameter.getName());
			Object parameterValue = null;
			
			if (parameterStringValue == null) {
				if (parameter.isRequired())
					throw new NullArgumentException(parameter.getName());
				
				parameterValue = parameter.getDefaultValue();
			}
			else {
				try {
					Reader reader = new StringReader(parameterStringValue);
					parameterValue = ((method == HttpMethod.GET) || (method == HttpMethod.DELETE))
							? getQueryParameterDataSerializer().createObjectReader(reader).readObject()
							: getDataSerializer().createObjectReader(reader).readObject(); 
				}
				catch (DataFormatException ex) {
					throw new InvalidArgumentException(parameter.getName(), String.format(
							"Invalid format for parameter \"%s\" with value \"%s\".", parameter.getName(), parameterStringValue));
				}
				catch (IOException ex) {
					throw new IllegalStateException(ex);
				}
			}
			
			if ((parameterValue != null) && !isInstance(parameter.getType(), parameterValue))
				throw new InvalidArgumentException(parameter.getName(), String.format(
						"Invalid type \"%s\", while type \"%s\" was expected.",
						parameterValue.getClass().getName(), parameter.getType().getName()));
				
			arguments[i] = parameterValue;
		}
		return arguments;
	}
	
	
	private void processRequest(HttpMethod method, HttpServletRequest request, HttpServletResponse response) throws ServletException {
		
		Optional<HttpRestHandler> maybeHandler = getHandler(method, request.getPathInfo());
		
		if (!maybeHandler.isPresent()) {
			writeErrorNotFoundResponse(response);
			return;
		}
		
		HttpRestHandler handler = maybeHandler.get();

		response.setHeader(HttpHeaderField.CACHE_CONTROL, HttpHeaderFieldValue.CACHE_CONTROL_NO_CACHE);
		response.setHeader(HttpHeaderField.PRAGMA, HttpHeaderFieldValue.PRAGMA_NO_CACHE);
		response.setDateHeader(HttpHeaderField.EXPIRES, 0);
		
		threadLocalRequest.set(request);
		threadLocalResponse.set(response);
		
		handler.process(request, response);
	}

	private void writeHandlerInvocationResult(HttpServletResponse response, Method APIMethod, Object[] arguments) throws Throwable {		
		response.setStatus(HttpServletResponse.SC_OK);
		response.setCharacterEncoding(getDataSerializer().getEncoding());
		response.setContentType(getDataSerializer().getMimeType());
		
		try {
			Object invocationResult = APIMethod.invoke(this, arguments);
			
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			String contentEncoding = response.getHeader(HttpHeaderField.CONTENT_ENCODING);
			boolean compressed = HttpHeaderFieldValue.CONTENT_ENCODING_GZIP.equals(contentEncoding);
			OutputStream outputStream = compressed ? new GZIPOutputStream(byteArrayOutputStream, true) : byteArrayOutputStream;
			ObjectWriter writer =  getDataSerializer().createObjectWriter(new OutputStreamWriter(outputStream, getDataSerializer().getEncoding()));

			writer.writeObject(invocationResult);
			writer.flush();

			response.setContentLength(byteArrayOutputStream.size());
			response.getOutputStream().write(byteArrayOutputStream.toByteArray());
		}
		catch (IllegalArgumentException | IllegalAccessException ex) {
			throw ex;
		}
		catch (InvocationTargetException ex) {
			throw ex.getCause();
		}
	}
	
	private boolean isInstance(Class<?> type, Object obj) {
		Class<?> primitiveType = PRIMITIVE_TYPE_MAP.get(type);
		return (primitiveType != null) ? primitiveType.isInstance(obj) : type.isInstance(obj);
	}

	private void writeErrorNotFoundResponse(HttpServletResponse response) {
		response.setStatus(HttpServletResponse.SC_NOT_FOUND);
	}
}
