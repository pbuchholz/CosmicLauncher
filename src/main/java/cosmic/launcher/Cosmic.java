package cosmic.launcher;

import java.lang.management.ManagementFactory;

import javax.management.InstanceAlreadyExistsException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cosmic.launcher.execution.DefaultExecutionContextFactory;
import cosmic.launcher.execution.ExecutionContextFactory;
import cosmic.launcher.parameter.DefaultExecutionParameterFactory;
import cosmic.launcher.timetable.InMemoryTimetableRepository;
import cosmic.launcher.timetable.TimetableRepository;

/**
 * Main class of the cosmic launcher.
 * 
 * @author Philipp Buchholz
 */
public class Cosmic {

	private static final Logger LOGGER = LoggerFactory.getLogger(Cosmic.class);

	private ExecutionContextFactory executionContextFactory;
	private TimetableRepository timetableRepository;
	private Scheduler scheduler;
	private ShutdownHook shutdownHook;

	public static void main(String[] args) {
		try {
			Cosmic cosmic = new Cosmic();
			cosmic.prepareDefaults();
			cosmic.setupShutdownHook();
			cosmic.launch();
		} catch (Exception e) {
			LOGGER.error("Exception in main. Cosmic launcher will exit.", e);
		}
	}

	/**
	 * Initializes and sets up defaults.
	 * 
	 * @throws ReflectiveOperationException
	 */
	private void prepareDefaults() throws ReflectiveOperationException {
		// Default dependencies.
		this.timetableRepository = new InMemoryTimetableRepository();
		this.executionContextFactory = new DefaultExecutionContextFactory(new DefaultExecutionParameterFactory());
		this.scheduler = new DecoratedDefaultSchedulerFactory()
				.createDecoratedDefaultScheduler(executionContextFactory);

		// Configuration defaults.
		CosmicConfiguration.INSTANCE.setConcurrentExecuteables(10);
	}

	/**
	 * Setups the {@link ShutdownHook} needed to control the application over JMX.
	 * 
	 * @throws MalformedObjectNameException
	 * @throws NotCompliantMBeanException
	 * @throws MBeanRegistrationException
	 * @throws InstanceAlreadyExistsException
	 */
	private void setupShutdownHook() throws MalformedObjectNameException, InstanceAlreadyExistsException,
			MBeanRegistrationException, NotCompliantMBeanException {
		MBeanServer mbeanServer = ManagementFactory.getPlatformMBeanServer();
		ObjectName objectName = new ObjectName("cosmic.launcher.control:type=ShutdownHook");
		this.shutdownHook = ShutdownHook.newInstanceWithCleanupStrategy(new CleanupStrategy() {

			@Override
			public void cleanup() {
				LOGGER.info("Cleanup called. Will stop scheduling now.");
				scheduler.stopScheduling();
			}
		});
		mbeanServer.registerMBean(this.shutdownHook, objectName);
	}

	private void launch() throws Exception {

		assert (null != this.shutdownHook);
		assert (null != this.scheduler);
		assert (null != this.timetableRepository);

		this.timetableRepository.findAll().stream() //
				.forEach((timetable) -> {
					LOGGER.info("Scheduling timetable {}.", timetable);
					scheduler.schedule(timetable);
				});

		while (!this.shutdownHook.isShutdownSignaled()) {

		}

	}

}
