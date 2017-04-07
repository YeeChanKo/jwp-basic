package next.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class HttpController implements Controller {
	private static final Logger log = LoggerFactory.getLogger(HttpController.class);

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) {
		String method = req.getMethod();

		try {
			switch (method) {
			case "GET":
				return doGet(req, resp);
			case "POST":
				return doPost(req, resp);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			return "error: " + e.getMessage();
		}

		return null;
	}

	public abstract String doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException;

	public abstract String doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException;

}
