import lambdamagic.web.http.HttpMethod;
import lambdamagic.web.http.service.rest.HttpRestOperation;
import lambdamagic.web.http.service.rest.HttpRestParameter;
import lambdamagic.web.http.service.rest.HttpRestService;

public class RestAPISample extends HttpRestService {

	private static final long serialVersionUID = -8019334736240478927L;

	public RestAPISample() {
		try {
			registerOperation(
					new HttpRestOperation(HttpMethod.POST, "/authenticate", "authenticate",
						new HttpRestParameter("id", String.class),
						new HttpRestParameter("password", String.class, null)));

		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public String authenticate(String id, String password) {
		return id + " is trying to authenticate by password: " + password;
	}
	
}
