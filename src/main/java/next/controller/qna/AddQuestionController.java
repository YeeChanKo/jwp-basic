package next.controller.qna;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import next.controller.UserSessionUtils;
import next.dao.QuestionDao;
import next.model.Question;

public class AddQuestionController extends AbstractController {
	private static final Logger log = LoggerFactory
			.getLogger(AddQuestionController.class);

	private QuestionDao questionDao = new QuestionDao();

	@Override
	public ModelAndView execute(HttpServletRequest req,
			HttpServletResponse response) throws Exception {
		Question question = new Question(
				UserSessionUtils.getUserFromSession(req.getSession())
						.getUserId(),
				req.getParameter("title"), req.getParameter("contents"));
		log.debug("question : {}", question);

		Question savedQuestion = questionDao.insert(question);
		return jspView("redirect:/").addObject("question", savedQuestion);
	}
}
