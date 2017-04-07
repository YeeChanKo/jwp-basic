package next.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ForwardController implements Controller {
	private String forwardUrl;

	public ForwardController(String forwardUrl) {
		this.forwardUrl = forwardUrl;
	}

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) {
		return forwardUrl;
	}

}
