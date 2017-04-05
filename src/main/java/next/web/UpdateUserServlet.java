package next.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.db.DataBase;
import next.model.User;
import util.StringUtil;

@WebServlet("/user/update")
public class UpdateUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		try {
			User userInSession = (User) req.getSession().getAttribute("user");
			String idInSession = userInSession.getUserId();

			String userId = req.getParameter("userId");
			String password = req.getParameter("password");
			String name = req.getParameter("name");
			String email = req.getParameter("email");

			// userId form 값 변조하지 않았는지 세션 값과 비교
			if (!idInSession.equals(userId) || StringUtil.checkIfEmpty(userId) || StringUtil.checkIfEmpty(password)
					|| StringUtil.checkIfEmpty(name) || StringUtil.checkIfEmpty(email)) {
				throw new Exception();
			}

			User updatedUser = new User(userId, password, name, email);
			DataBase.findUserById(userId).update(updatedUser);

			resp.sendRedirect("/user/list");

		} catch (Exception e) {
			resp.sendRedirect("/user/update.jsp?invalid=true");
		}
	}
}
