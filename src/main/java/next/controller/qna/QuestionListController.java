package next.controller.qna;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import next.dao.QuestionDao;
import next.model.Question;

public class QuestionListController extends AbstractController {

	@Override
	public ModelAndView execute(HttpServletRequest req,
			HttpServletResponse response) throws Exception {

		List<Question> questions = new QuestionDao().findAll();

		return jsonView().addObject("questions", questions);
	}
}
