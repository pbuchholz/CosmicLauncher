package cosmic.launcher.decorator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Decorator(LogMethodInterceptor.class)
public @interface Log {

	// TODO LogLevel must be implemented here and is currently missing.

}
