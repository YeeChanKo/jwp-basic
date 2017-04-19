package next.controller.qna;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import next.controller.UserSessionUtils;

public class QuestionFormController extends AbstractController {

	@Override
	public ModelAndView execute(HttpServletRequest req,
			HttpServletResponse response) throws Exception {
		if (UserSessionUtils.isLogined(req.getSession())) {
			return jspView("/qna/form.jsp");
		} else {
			return jspView("redirect:/users/loginForm");
		}
	}
}
