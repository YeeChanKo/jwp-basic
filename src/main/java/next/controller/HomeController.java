package next.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.db.DataBase;

public class HomeController extends HttpController {
	public String doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setAttribute("users", DataBase.findAll());
		return "index.jsp";
	}

	@Override
	public String doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		return null;
	}
}
