package cosmic.launcher.decorator;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.stream.Stream;

/**
 * Creates objects which can be decorated by {@link Decorator}s.
 * 
 * @author Philipp Buchholz
 *
 * @param <I>
 * @param <T>
 */
public class DecoratedFactory<I, T extends I> {

	public T createDefaultInstance(Class<I> interfaceType, Class<T> implementationType)
			throws ReflectiveOperationException {

		Constructor<T> constructor = implementationType.getConstructor();
		return decorate(constructor.newInstance(), interfaceType);
	}

	@SuppressWarnings("unchecked")
	public T createFactoryMethodInstance(Class<I> interfaceType, Class<T> implementationType, String factoryMethodName,
			Object... factoryMethodArgs) throws ReflectiveOperationException {

		Method factoryMethod = this.findFactoryMethod(implementationType, factoryMethodName);
		return decorate((T) factoryMethod.invoke(null, factoryMethodArgs), interfaceType);
	}

	private Method findFactoryMethod(Class<T> implementationType, String factoryMethodName) {
		return Stream.of(implementationType.getMethods()) //
				.filter((m) -> m.getName().equals(factoryMethodName)) //
				.findFirst() //
				.get();
	}

	@SuppressWarnings("unchecked")
	private T decorate(T implementation, Class<I> interfaceType) {
		DecoratorInvocationHandler decoratedInvocationHandler = new DecoratorInvocationHandler(implementation);

		return (T) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class[] { interfaceType },
				decoratedInvocationHandler);
	}

}
