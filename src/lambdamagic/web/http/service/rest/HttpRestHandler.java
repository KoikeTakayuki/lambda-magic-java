package lambdamagic.web.http.service.rest;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface HttpRestHandler {
	void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException;
}
