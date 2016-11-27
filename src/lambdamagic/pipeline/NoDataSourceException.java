package lambdamagic.pipeline;

public class NoDataSourceException extends IllegalStateException {

	private static final long serialVersionUID = -194564933095241422L;

	public NoDataSourceException() {
		super("Pipeline has no data source");
	}
}