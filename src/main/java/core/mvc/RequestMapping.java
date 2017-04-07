package core.mvc;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Maps;

import next.controller.Controller;
import next.controller.CreateUserController;
import next.controller.ForwardController;
import next.controller.HomeController;
import next.controller.ListUserController;
import next.controller.LoginController;
import next.controller.LogoutController;
import next.controller.ProfileController;
import next.controller.UpdateUserController;

public class RequestMapping {
	private static final Logger log = LoggerFactory.getLogger(RequestMapping.class);
	private static Map<String, Controller> mapper = Maps.newHashMap();

	static {
		mapper.put("/", new HomeController());

		mapper.put("/users/create", new CreateUserController());
		mapper.put("/users/form", new ForwardController("/user/form.jsp"));
		mapper.put("/users/loginForm", new ForwardController("/user/login.jsp"));

		mapper.put("/users", new ListUserController());
		mapper.put("/users/login", new LoginController());
		mapper.put("/users/logout", new LogoutController());
		mapper.put("/users/profile", new ProfileController());
		mapper.put("/users/update", new UpdateUserController());
		mapper.put("/users/updateForm", new UpdateUserController());
	}

	public static Controller getControllerInstanceFromUri(String requestURI) throws Exception {
		Controller controllerClass = mapper.get(requestURI);

		if (controllerClass != null) {
			return controllerClass;
		} else {
			return null;
		}
	}
}
