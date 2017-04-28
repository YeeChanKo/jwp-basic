package core.nmvc;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.reflections.ReflectionUtils;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Maps;

import core.annotation.Controller;
import core.annotation.RequestMapping;
import core.annotation.RequestMethod;

public class AnnotationHandlerMapping {
	private static final Logger log = LoggerFactory
			.getLogger(AnnotationHandlerMapping.class);

	private Object[] basePackage;

	private Map<HandlerKey, HandlerExecution> handlerExecutions = Maps
			.newHashMap();

	public AnnotationHandlerMapping(Object... basePackage) {
		this.basePackage = basePackage;
	}

	@SuppressWarnings("unchecked")
	public void initialize() {
		Reflections reflections = new Reflections(basePackage);

		Set<Class<?>> annotated = reflections
				.getTypesAnnotatedWith(Controller.class);

		log.debug(annotated.toString());

		for (Class<?> clazz : annotated) {
			Set<Method> ms = ReflectionUtils.getAllMethods(clazz,
					ReflectionUtils.withAnnotation(RequestMapping.class));

			Object i = null;
			try {
				i = clazz.newInstance();
			} catch (Exception e) {
				log.error("", e);
			}

			for (Method m : ms) {
				RequestMapping rm = m.getAnnotation(RequestMapping.class);
				HandlerKey hk = createHandlerKey(rm);
				handlerExecutions.put(hk, new HandlerExecution(i, m));
			}
		}
	}

	public HandlerExecution getHandler(HttpServletRequest request) {
		String requestUri = request.getRequestURI();
		RequestMethod rm = RequestMethod
				.valueOf(request.getMethod().toUpperCase());
		return handlerExecutions.get(new HandlerKey(requestUri, rm));
	}

	public HandlerKey createHandlerKey(RequestMapping rm) {
		return new HandlerKey(rm.value(), rm.method());
	}
}
