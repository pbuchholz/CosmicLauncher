package cosmic.launcher.decorator;

import java.lang.reflect.Method;

public interface MethodInterceptor {

	void beforeMethodCall(Object target, Method method, Object[] args);

	void afterMethodCall(Object target, Method method, Object ret);

}
