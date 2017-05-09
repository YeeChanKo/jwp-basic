package core.nmvc;

import java.util.Map;
import java.util.Set;

import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Maps;

import core.annotation.Controller;

public class ControllerScanner {
	private static final Logger log = LoggerFactory
			.getLogger(ControllerScanner.class);

	private Reflections reflections;

	public ControllerScanner(Object... basePackage) {
		reflections = new Reflections(basePackage);
	}

	public Map<Class<?>, Object> getControllers() {
		Set<Class<?>> preInitiatedControllers = reflections
				.getTypesAnnotatedWith(Controller.class);
		return instantiateControllers(preInitiatedControllers);
	}

	private Map<Class<?>, Object> instantiateControllers(
			Set<Class<?>> preInitiatedControllers) {
		Map<Class<?>, Object> initiatedControllers = Maps.newHashMap();
		for (Class<?> clazz : preInitiatedControllers) {
			Object i = null;
			try {
				i = clazz.newInstance();
			} catch (Exception e) {
				log.error("", e);
				throw new RuntimeException();
			}
			initiatedControllers.put(clazz, i);
		}
		return initiatedControllers;
	}
}