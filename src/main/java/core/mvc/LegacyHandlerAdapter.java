package core.mvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LegacyHandlerAdapter implements HandlerAdapter {

	@Override
	public boolean doesSupport(Object handler) {
		return (handler instanceof Controller);
	}

	@Override
	public ModelAndView handle(HttpServletRequest req, HttpServletResponse resp,
			Object handler) throws Exception {
		return (ModelAndView) ((Controller) handler).execute(req, resp);
	}

}
