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

@WebServlet(name = "dispatcher", urlPatterns = "/", loadOnStartup = 1)
public class DispatcherServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory
			.getLogger(DispatcherServlet.class);

	private LegacyHandlerMapping lhm;
	private AnnotationHandlerMapping ahm;

	private List<HandlerMapping<?>> hms = Lists.newArrayList();
	private List<HandlerAdapter> has = Lists.newArrayList();

	@Override
	public void init() throws ServletException {
		lhm = new LegacyHandlerMapping();
		lhm.initMapping();

		ahm = new AnnotationHandlerMapping("next.controller",
				"next.controller.qna", "next.controller.user");
		ahm.initialize();

		hms.add(lhm);
		hms.add(ahm);

		has.add(new LegacyHandlerAdapter());
		has.add(new AnnotationHandlerAdapter());
	}

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		logger.debug("Method : {}, Request URI : {}", req.getMethod(),
				req.getRequestURI());

		try {
			Object handler = getHandler(req);
			ModelAndView mav = execute(handler, req, resp);
			View view = mav.getView();
			view.render(mav.getModel(), req, resp);
		} catch (Exception e) {
			logger.error("", e);
			throw new ServletException(e.getMessage());
		}
	}

	public Object getHandler(HttpServletRequest req) {
		return hms.stream().map(hm -> hm.getHandler(req))
				.filter(h -> h.isPresent()).findFirst()
				.orElseThrow(
						() -> new IllegalArgumentException("invalid request"))
				.get();
	}

	private ModelAndView execute(Object handler, HttpServletRequest req,
			HttpServletResponse resp) throws Exception {
		return has.stream().filter(h -> h.doesSupport(handler)).findFirst()
				.map(h -> {
					try {
						return h.handle(req, resp, handler);
					} catch (Exception e) {
						throw new RuntimeException();
					}
				}).orElseThrow(() -> new RuntimeException());
	}
}
