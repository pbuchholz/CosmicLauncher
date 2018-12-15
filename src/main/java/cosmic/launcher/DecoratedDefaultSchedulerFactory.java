package cosmic.launcher;

import cosmic.launcher.decorator.DecoratedFactory;
import cosmic.launcher.execution.ExecutionContextFactory;

public class DecoratedDefaultSchedulerFactory {

	private static final String STATIC_FACTORY_METHOD = "schedulerWith";

	private DecoratedFactory<Scheduler, DefaultScheduler> decoratedFactory = new DecoratedFactory<>();

	/**
	 * Creates and returns a decorateable instance of {@link DefaultScheduler}.
	 * 
	 * @param executionContextFactory
	 * @return
	 * @throws ReflectiveOperationException
	 */
	public Scheduler createDecoratedDefaultScheduler(ExecutionContextFactory executionContextFactory)
			throws ReflectiveOperationException {
		return decoratedFactory.createFactoryMethodInstance(Scheduler.class, DefaultScheduler.class,
				STATIC_FACTORY_METHOD, executionContextFactory);
	}

}
