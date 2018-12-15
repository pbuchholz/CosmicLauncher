package cosmic.launcher.decorator;

import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogMethodInterceptor implements MethodInterceptor {

	@Override
	public void beforeMethodCall(Object target, Method method, Object[] args) {
		Logger targetLogger = LoggerFactory.getLogger(target.getClass());
		targetLogger.info("Before method {} call with argument {} on object {}.", method, args, target);
	}

	@Override
	public void afterMethodCall(Object target, Method method, Object ret) {
		Logger targetLogger = LoggerFactory.getLogger(target.getClass());
		targetLogger.info("After method {} call with return value {} on object {}.", method, ret, target);
	}

}
