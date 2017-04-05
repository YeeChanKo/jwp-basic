package next.web;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.db.DataBase;
import next.model.User;

@WebServlet("/user/update")
public class UpdateUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String userId = (String) req.getParameter("userId");
		log(userId);
		User user = DataBase.findUserById(userId);
		
		req.setAttribute("user", user);
		RequestDispatcher rd = req.getRequestDispatcher("/user/update.jsp");
		rd.forward(req, resp);		
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// if(){
		// String req.getParameter("change")
		//
		// String userId = req.getParameter("userId");
		// String password = req.getParameter("password");
		// String name = req.getParameter("name");
		// String email = req.getParameter("email");
		//
		// User updatedUser = new User(userId, password, name, email);
		// DataBase.findUserById(userId).update(updatedUser);
		//
		// resp.sendRedirect("/user/list");
		//
		// } else{
		// resp.sendRedirect("/user/update.jsp?invalid=true");
		// }
	}
}
