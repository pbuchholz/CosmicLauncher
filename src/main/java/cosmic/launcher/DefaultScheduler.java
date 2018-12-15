package cosmic.launcher;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import cosmic.launcher.decorator.DecoratedFactory;
import cosmic.launcher.decorator.Log;
import cosmic.launcher.execution.Executable;
import cosmic.launcher.execution.Execution;
import cosmic.launcher.execution.ExecutionContext;
import cosmic.launcher.execution.ExecutionContextFactory;
import cosmic.launcher.parameter.ToExecutableMapping;
import cosmic.launcher.timetable.Timetable;
import cosmic.launcher.timetable.TimetableEntry;

/**
 * Scheduler which is responsible for the following tasks:
 * 
 * <ul>
 * <li>Scheduling the {@link TimetableEntry}s of one {@link Timetable}.</li>
 * <li>Report back the results of executing the {@link Executable} of a
 * {@link TimetableEntry} using {@link ExecutionCallback}s.</li>
 * </ul>
 * 
 * @author Philipp Buchholz
 */
public class DefaultScheduler implements Scheduler {

	private ExecutionContextFactory executionContextFactory;
	private ScheduledExecutorService scheduledExecutorService;

	private DefaultScheduler() {

	}

	/**
	 * Static factory which returns an instance which will use the passed in
	 * {@link ExecutionContextFactory}.
	 * 
	 * @param executionContextFactory
	 * @return
	 */
	public static DefaultScheduler schedulerWith(ExecutionContextFactory executionContextFactory) {
		DefaultScheduler scheduler = new DefaultScheduler();
		scheduler.executionContextFactory = executionContextFactory;
		return scheduler;
	}

	@Log
	@Override
	public List<Execution> schedule(Timetable timetable) {
		List<Execution> executions = new ArrayList<>();

		this.scheduledExecutorService = Executors
				.newScheduledThreadPool(CosmicConfiguration.INSTANCE.getConcurrentExecuteables(), new ThreadFactory() {

					private AtomicInteger threadCount = new AtomicInteger(0);

					@Override
					public Thread newThread(Runnable runnable) {
						Thread thread = new Thread(runnable);
						thread.setName("Executable-" //
								.concat(String.valueOf(threadCount.incrementAndGet())));
						return thread;
					}
				});

		for (TimetableEntry timetableEntry : timetable.getTimetableEntries()) {

			this.scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
				@Override
				public void run() {
					/* Each run is historized in an Execution. */
					Execution execution = new Execution();
					execution.setExecutableIdentifier(UUID.randomUUID());
					executions.add(execution);

					try {
						Executable executable = createDefaultInstance(timetableEntry.getExecutableType(),
								Executable.class);

						ExecutionContext executionContext = executionContextFactory.createExecutionContext( //
								ToExecutableMapping.create(executable.name()), timetableEntry);

						executable.execute(executionContext);
						execution.setDone(true);

					} catch (Exception e) {

						execution.setErronous(true);
						execution.setThrownException(e);
					}
				}
			}, 0, timetableEntry.calculatePeriod(), TimeUnit.MILLISECONDS);
		}

		return executions;
	}

	/**
	 * Stops the scheduling and shuts down the {@link ExecutorService} in use.
	 */
	@Override
	public void stopScheduling() {
		assert (null != this.scheduledExecutorService);
		assert (!this.scheduledExecutorService.isShutdown() && !this.scheduledExecutorService.isTerminated());

		this.scheduledExecutorService.shutdown();
	}

	/**
	 * Creates an instance of the given types using the default constructor.
	 * 
	 * @param implementationType
	 * @param interfaceType
	 * @return
	 * @throws ReflectiveOperationException
	 */
	private <I, T extends I> T createDefaultInstance(Class<T> implementationType, Class<I> interfaceType)
			throws ReflectiveOperationException {
		DecoratedFactory<I, T> decoratedFactory = new DecoratedFactory<>();
		return decoratedFactory.createDefaultInstance(interfaceType, implementationType);
	}

}
