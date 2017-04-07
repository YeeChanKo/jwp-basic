package next.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import core.db.DataBase;
import next.model.User;

public class LoginController extends HttpController {

	public String doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		return "/user/login.jsp";
	}

	public String doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String userId = req.getParameter("userId");
		String password = req.getParameter("password");
		User user = DataBase.findUserById(userId);
		if (user == null) {
			req.setAttribute("loginFailed", true);
			return "/user/login.jsp";
		}

		if (user.matchPassword(password)) {
			HttpSession session = req.getSession();
			session.setAttribute(UserSessionUtils.USER_SESSION_KEY, user);
			return "redirect: /";
		} else {
			req.setAttribute("loginFailed", true);
			return "/user/login.jsp";
		}
	}
}
