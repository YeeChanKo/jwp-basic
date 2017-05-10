package core.mvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.nmvc.HandlerExecution;

public class AnnotationHandlerAdapter implements HandlerAdapter {

	@Override
	public boolean doesSupport(Object handler) {
		return (handler instanceof HandlerExecution);
	}

	@Override
	public ModelAndView handle(HttpServletRequest req, HttpServletResponse resp,
			Object handler) throws Exception {
		return (ModelAndView) ((HandlerExecution) handler).handle(req, resp);
	}

}
