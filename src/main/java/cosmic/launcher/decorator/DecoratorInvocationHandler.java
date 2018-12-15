package cosmic.launcher.decorator;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * {@link InvocationHandler} which decorates before
 * {@link MethodInterceptor#beforeMethodCall(Object, Method, Object[])} and
 * after {@link MethodInterceptor#afterMethodCall(Object, Method, Object)} with
 * cross cutting concerns.
 * 
 * @author Philipp Buchholz
 */
public class DecoratorInvocationHandler implements InvocationHandler {

	private Object target;

	public DecoratorInvocationHandler(Object target) {
		this.target = target;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		try {
			Optional<Annotation> decorator = this.findDecorator(target, method);

			if (decorator.isPresent()) {
				MethodInterceptor methodInterceptor = this.readMethodInterceptor(decorator.get());
				methodInterceptor.beforeMethodCall(proxy, method, args);
				Object ret = method.invoke(target, args);
				methodInterceptor.afterMethodCall(proxy, method, ret);
				return ret;
			} else {
				return method.invoke(target, args);
			}

		} catch (ReflectiveOperationException e) {
			throw new RuntimeException(e);
		}

	}

	/**
	 * Reads the {@link MethodInterceptor} from the passed in {@link Decorator}.
	 * 
	 * @param decorator
	 * @return {@link MethodInterceptor} of the {@link Decorator} passed in.
	 * @throws ReflectiveOperationException
	 */
	private MethodInterceptor readMethodInterceptor(Annotation decorator) throws ReflectiveOperationException {
		assert (null != decorator);

		Class<? extends MethodInterceptor> methodInterceptorType = decorator.annotationType() //
				.getAnnotation(Decorator.class).value();

		Constructor<? extends MethodInterceptor> constructor = methodInterceptorType.getConstructor();
		return constructor.newInstance();
	}

	/**
	 * Find the {@link Decorator} annotation on the passed in method if the
	 * {@link Method} is decorated.
	 * 
	 * @param target
	 * @param method
	 * @return {@link Decorator} annotation if {@link Method} is decorated otherwise
	 *         <code>null</code>.
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 */
	private Optional<Annotation> findDecorator(Object target, Method method)
			throws NoSuchMethodException, SecurityException {
		Method targetMethod = target.getClass().getMethod(method.getName(), //
				method.getParameterTypes());

		return Stream.of(targetMethod.getAnnotations()) //
				.filter((annotation) -> annotation.annotationType().isAnnotationPresent(Decorator.class)) //
				.findFirst();
	}

}
