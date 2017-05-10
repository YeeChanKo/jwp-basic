package core.mvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface HandlerAdapter {
	boolean doesSupport(Object handler);

	ModelAndView handle(HttpServletRequest req, HttpServletResponse resp,
			Object handler) throws Exception;
}
