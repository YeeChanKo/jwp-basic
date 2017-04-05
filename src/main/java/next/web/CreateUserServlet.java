package next.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.db.DataBase;
import next.model.User;
import util.StringUtil;

@WebServlet("/user/create")
public class CreateUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger log = LoggerFactory.getLogger(CreateUserServlet.class);

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String userId = req.getParameter("userId");
		String password = req.getParameter("password");
		String name = req.getParameter("name");
		String email = req.getParameter("email");

		if (!StringUtil.checkIfEmpty(userId) && !StringUtil.checkIfEmpty(password) && !StringUtil.checkIfEmpty(name)
				&& !StringUtil.checkIfEmpty(email)) {
			User user = new User(userId, password, name, email);
			log.debug("user : {}", user);
			DataBase.addUser(user);
			resp.sendRedirect("/");
		} else {
			resp.sendRedirect("/user/form.jsp?invalid=true");
		}
	}
}
