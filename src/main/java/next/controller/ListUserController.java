package next.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.db.DataBase;

public class ListUserController extends HttpController {
	public String doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if (!UserSessionUtils.isLogined(req.getSession())) {
			return "redirect: /users/loginForm";
		}

		req.setAttribute("users", DataBase.findAll());

		return "/user/list.jsp";
	}

	@Override
	public String doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		return null;
	}
}
