package cosmic.launcher;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import java.lang.reflect.Proxy;

import org.junit.Test;

import cosmic.launcher.execution.ExecutionContextFactory;

/**
 * Contains tests for {@link DecoratedDefaultSchedulerFactory}.
 * 
 * @author Philipp Buchholz
 */
public class DecoratedDefaultSchedulerFactoryTest {

	@Test
	public void shouldCreateDecoratedDefaultScheduler() throws ReflectiveOperationException {
		ExecutionContextFactory executionContextFactory = mock(ExecutionContextFactory.class);
		DecoratedDefaultSchedulerFactory decoratedDefaultSchedulerFactory = new DecoratedDefaultSchedulerFactory();
		Scheduler scheduler = decoratedDefaultSchedulerFactory.createDecoratedDefaultScheduler(executionContextFactory);

		assertNotNull("Scheduler has not been created and is null.", scheduler);
		assertTrue(scheduler instanceof Proxy);
		assertTrue(scheduler instanceof Scheduler);
	}

}
