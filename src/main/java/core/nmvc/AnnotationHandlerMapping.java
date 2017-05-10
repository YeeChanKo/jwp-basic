package core.nmvc;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.reflections.ReflectionUtils;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

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

		Set<Method> ms = getRequestMappingMethods(co.keySet());
		for (Method m : ms) {
			Object i = co.get(ms.getClass());
			RequestMapping rm = m.getAnnotation(RequestMapping.class);
			HandlerKey hk = createHandlerKey(rm);
			handlerExecutions.put(hk, new HandlerExecution(i, m));
		}
	}

	@SuppressWarnings("unchecked")
	public Set<Method> getRequestMappingMethods(Set<Class<?>> clazzs) {
		Set<Method> ms = Sets.newHashSet();
		for (Class<?> clazz : clazzs) {
			ms.addAll(ReflectionUtils.getAllMethods(clazz,
					ReflectionUtils.withAnnotation(RequestMapping.class)));
		}
		return ms;
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
