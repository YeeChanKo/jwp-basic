package next.controller.qna;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import next.dao.QuestionDao;
import next.model.Question;

public class ModifyQuestionController extends AbstractController {
	private static final Logger log = LoggerFactory
			.getLogger(ModifyQuestionController.class);

	@Override
	public ModelAndView execute(HttpServletRequest req,
			HttpServletResponse response) throws Exception {
		long questionId = Long.parseLong(req.getParameter("questionId"));
		String title = req.getParameter("title");
		String contents = req.getParameter("contents");

		Question question = new QuestionDao().findById(questionId);
		question.setTitle(title);
		question.setContents(contents);

		Question updatedQuestion = new QuestionDao().update(question);
		log.debug(updatedQuestion.toString());

		String urlPath = (String) req.getSession()
				.getAttribute("lastModifyRequestUrl");

		return jspView("redirect:" + urlPath);
	}
}
