package next.controller.qna;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import next.dao.AnswerDao;
import next.dao.QuestionDao;
import next.model.Answer;
import next.model.Question;

public class AddAnswerController extends AbstractController {
	private static final Logger log = LoggerFactory
			.getLogger(AddAnswerController.class);

	@Override
	public ModelAndView execute(HttpServletRequest req,
			HttpServletResponse response) throws Exception {
		long questionId = Long.parseLong(req.getParameter("questionId"));
		Answer answer = new Answer(req.getParameter("writer"),
				req.getParameter("contents"), questionId);
		log.debug("answer : {}", answer);

		Answer savedAnswer = new AnswerDao().insert(answer);
		Question q = new QuestionDao().findById(questionId);
		q.setCountOfComment(q.getCountOfComment() + 1);
		Question updatedQuestion = new QuestionDao().update(q);

		return jsonView().addObject("answer", savedAnswer).addObject("question",
				updatedQuestion);
	}
}
