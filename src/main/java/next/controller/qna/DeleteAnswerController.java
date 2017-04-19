package next.controller.qna;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.jdbc.DataAccessException;
import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import next.dao.AnswerDao;
import next.dao.QuestionDao;
import next.model.Question;
import next.model.Result;

public class DeleteAnswerController extends AbstractController {
	private AnswerDao answerDao = new AnswerDao();

	@Override
	public ModelAndView execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Long questionId = Long.parseLong(request.getParameter("questionId"));
		Long answerId = Long.parseLong(request.getParameter("answerId"));

		ModelAndView mav = jsonView();
		try {
			answerDao.delete(answerId);
			QuestionDao qd = new QuestionDao();
			Question q = qd.findById(questionId);
			q.setCountOfComment(q.getCountOfComment() - 1);
			qd.update(q);

			mav.addObject("result", Result.ok());
		} catch (DataAccessException e) {
			mav.addObject("result", Result.fail(e.getMessage()));
		}
		return mav;
	}
}
