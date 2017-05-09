package core.nmvc;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.reflections.ReflectionUtils;

import com.google.common.collect.Maps;

import core.annotation.RequestMapping;
import core.annotation.RequestMethod;
import core.mvc.HandlerMapping;

public class AnnotationHandlerMapping implements HandlerMapping {

	private Object[] basePackage;

	private Map<HandlerKey, HandlerExecution> handlerExecutions = Maps
			.newHashMap();

	public AnnotationHandlerMapping(Object... basePackage) {
		this.basePackage = basePackage;
	}

	public void initialize() {
		Map<Class<?>, Object> co = new ControllerScanner(basePackage)
				.getControllers();

		Map<Method, Object> ms = getRequestMappingMethods(co);
		for (Method m : ms.keySet()) {
			Object i = ms.get(m);
			RequestMapping rm = m.getAnnotation(RequestMapping.class);
			HandlerKey hk = createHandlerKey(rm);
			handlerExecutions.put(hk, new HandlerExecution(i, m));
		}
	}

	@SuppressWarnings("unchecked")
	public Map<Method, Object> getRequestMappingMethods(
			Map<Class<?>, Object> co) {
		Map<Method, Object> mo = Maps.newHashMap();
		for (Class<?> clazz : co.keySet()) {
			Object i = co.get(clazz);
			Set<Method> ms = ReflectionUtils.getAllMethods(clazz,
					ReflectionUtils.withAnnotation(RequestMapping.class));
			for (Method m : ms) {
				mo.put(m, i);
			}
		}
		return mo;
	}

	public HandlerKey createHandlerKey(RequestMapping rm) {
		return new HandlerKey(rm.value(), rm.method());
	}

	@Override
	public HandlerExecution getHandler(HttpServletRequest request) {
		String requestUri = request.getRequestURI();
		RequestMethod rm = RequestMethod
				.valueOf(request.getMethod().toUpperCase());
		return handlerExecutions.get(new HandlerKey(requestUri, rm));
	}
}
