package core.ref;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import next.model.Question;
import next.model.User;

public class ReflectionTest {
	private static final Logger logger = LoggerFactory
			.getLogger(ReflectionTest.class);

	@Test
	public void showClass() {
		Class<Question> clazz = Question.class;
		logger.debug(clazz.getName());
	}

	@Test
	public void newInstanceWithConstructorArgs() throws Exception {
		Class<User> clazz = User.class;
		logger.debug(clazz.getName());

		Object[] param = { "a", "b", "c", "d" };

		for (Constructor<?> c : clazz.getConstructors()) {
			Object o = c.newInstance(param);
			logger.debug(o.toString());
		}
	}

	@Test
	public void privateFieldAccess() throws Exception {
		Class<Student> clazz = Student.class;
		logger.debug(clazz.getName());

		Field f1 = clazz.getDeclaredField("name");
		Field f2 = clazz.getDeclaredField("age");

		f1.setAccessible(true);
		f2.setAccessible(true);

		Object o = clazz.newInstance();
		f1.set(o, "ko");
		f2.set(o, 20);

		if (o instanceof Student) {
			logger.debug("hello");
		}

		logger.debug(o.toString());
	}
}
