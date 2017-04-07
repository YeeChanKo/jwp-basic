package next.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Controller {
	public abstract String execute(HttpServletRequest req, HttpServletResponse resp);
}
