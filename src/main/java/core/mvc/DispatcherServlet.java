package core.mvc;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import next.controller.Controller;

@WebServlet(name = "dispatcher", urlPatterns = "/", loadOnStartup = 1)
public class DispatcherServlet extends HttpServlet {
	private static final Logger log = LoggerFactory.getLogger(DispatcherServlet.class);
	private static final long serialVersionUID = 1L;

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		log.debug(req.getRequestURI());

		Controller controller = null;
		try {
			controller = RequestMapping.getControllerInstanceFromUri(req.getRequestURI());
		} catch (Exception e) {
			log.error(e.getMessage());
			resp.sendError(500, "Server Error");
		}

		if (controller == null) {
			resp.sendError(404, "Page Not Found");
		}

		String result = controller.execute(req, resp);
		if (result == null) {
			resp.sendError(404, "Page Not Found");
		}
		if (result.contains("redirect:")) {
			sendRedirect(req, resp, result);
		} else if (result.contains("error:")) {
			resp.sendError(400, "Bad Request: " + result.split("error:")[1].trim());
		} else {
			forward(req, resp, result);
		}
	}

	public void forward(HttpServletRequest req, HttpServletResponse resp, String forwardUrl) {

		try {
			RequestDispatcher rd = req.getRequestDispatcher(forwardUrl);
			rd.forward(req, resp);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}

	public void sendRedirect(HttpServletRequest req, HttpServletResponse resp, String result) {
		String[] str = result.split("redirect:");

		try {
			resp.sendRedirect(str[1].trim());
		} catch (IOException e) {
			log.error(e.getMessage());
		}
	}
}