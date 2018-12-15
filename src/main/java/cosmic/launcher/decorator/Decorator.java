package cosmic.launcher.decorator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to mark another annotation as begin a {@link Decorator}.
 * 
 * @author Philipp Buchholz
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Decorator {

	/**
	 * The {@link MethodInterceptor} which should be used to intercept the annotated
	 * method.
	 * 
	 * @return
	 */
	Class<? extends MethodInterceptor> value();

}
