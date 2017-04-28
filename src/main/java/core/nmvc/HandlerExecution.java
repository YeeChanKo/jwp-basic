package core.nmvc;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.mvc.ModelAndView;

public class HandlerExecution {
	Object i;
	Method m;

	public HandlerExecution(Object i, Method m) {
		super();
		this.i = i;
		this.m = m;
	}

	public ModelAndView handle(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		return (ModelAndView) m.invoke(i, request, response);
	}
}
