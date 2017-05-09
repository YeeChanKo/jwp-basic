package core.mvc;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;

import core.nmvc.AnnotationHandlerMapping;
import core.nmvc.HandlerExecution;
import javassist.NotFoundException;

@WebServlet(name = "dispatcher", urlPatterns = "/", loadOnStartup = 1)
public class DispatcherServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory
			.getLogger(DispatcherServlet.class);

	private LegacyHandlerMapping lhm;
	private AnnotationHandlerMapping ahm;

	private List<HandlerMapping> hm = Lists.newArrayList();

	@Override
	public void init() throws ServletException {
		lhm = new LegacyHandlerMapping();
		lhm.initMapping();

		ahm = new AnnotationHandlerMapping("next.controller",
				"next.controller.qna", "next.controller.user");
		ahm.initialize();

		hm.add(lhm);
		hm.add(ahm);
	}

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		logger.debug("Method : {}, Request URI : {}", req.getMethod(),
				req.getRequestURI());

		Object handler = getHandler(req);
		ModelAndView mav;
		try {
			if (handler instanceof Controller) {
				mav = ((Controller) handler).execute(req, resp);
			} else if (handler instanceof HandlerExecution) {
				mav = ((HandlerExecution) handler).handle(req, resp);
			} else {
				throw new NotFoundException("request mapping not found");
			}
			View view = mav.getView();
			view.render(mav.getModel(), req, resp);
		} catch (Exception e) {
			logger.error("", e);
			throw new ServletException(e.getMessage());
		}
	}

	public Object getHandler(HttpServletRequest req) {
		for (HandlerMapping handlerMapping : hm) {
			Object handler = handlerMapping.getHandler(req);
			if (handler != null) {
				return handler;
			}
		}
		return null;
	}
}
