package next.controller.qna;

import java.net.URI;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import next.controller.UserSessionUtils;
import next.dao.QuestionDao;
import next.dao.UserDao;
import next.model.Question;
import next.model.User;

public class ModifyFormController extends AbstractController {
	private static final Logger log = LoggerFactory
			.getLogger(ModifyFormController.class);

	@Override
	public ModelAndView execute(HttpServletRequest req,
			HttpServletResponse response) throws Exception {

		String questionId = req.getParameter("questionId");
		Question q = new QuestionDao().findById(Long.parseLong(questionId));
		User u = new UserDao().findByUserId(q.getWriter());

		URI uri = new URI(req.getHeader("Referer"));
		String path = uri.getPath() + "?" + uri.getQuery();
		log.debug(path);
		// 나중에 수정 후 해당 페이지로 이동할 수 있게 세션으로 저장해줌
		req.getSession().setAttribute("lastModifyRequestUrl", path);

		if (UserSessionUtils.isLogined(req.getSession())) {
			if (UserSessionUtils.isSameUser(req.getSession(), u)) {
				return jspView("/qna/form_modify.jsp").addObject("question", q);
			} else {
				return jspView("redirect:" + path);
			}
		} else {
			return jspView("redirect:/users/loginForm");
		}
	}
}
