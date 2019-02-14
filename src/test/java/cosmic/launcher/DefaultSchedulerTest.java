package cosmic.launcher;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.Test;

import cosmic.launcher.execution.Execution;
import cosmic.launcher.execution.ExecutionContext;
import cosmic.launcher.execution.ExecutionContextFactory;
import cosmic.launcher.parameter.ToExecutableMapping;
import cosmic.launcher.timetable.Timetable;
import cosmic.launcher.timetable.TimetableEntry;

/**
 * Contains tests for {@link DefaultScheduler}.
 * 
 * @author Philipp Buchholz
 */
public class DefaultSchedulerTest {

	private DefaultScheduler defaultScheduler;

	/**
	 * Returns a {@link TimetableEntry} for {@link MockExecutable} which should be
	 * scheduled every 5 seconds.
	 * 
	 * @return {@link TimetableEntry}
	 */
	private TimetableEntry buildTimetableEntryForEvery5Seconds() {
		return TimetableEntry.Builder() //
				.executeableType(MockExecutable.class) //
				.seconds(5) //
				.build();
	}

	/**
	 * Sets up the system under test.
	 */
	@Before
	public void setupSut() {
		ExecutionContextFactory executionContextFactory = mock(ExecutionContextFactory.class);
		ExecutionContext executionContext = ExecutionContext.builder() //
				.timetableEntry(this.buildTimetableEntryForEvery5Seconds()) //
				.build();

		when(executionContextFactory.createExecutionContext(any(ToExecutableMapping.class), any(TimetableEntry.class))) //
				.thenReturn(executionContext);
		this.defaultScheduler = DefaultScheduler.schedulerWith(executionContextFactory);
	}

	@Test
	public void shouldScheduleMockExecutableEveryFiveSecond() throws InterruptedException {
		Timetable timetable = new Timetable();
		timetable.addTimetableEntry(this.buildTimetableEntryForEvery5Seconds());

		List<Execution> executions = this.defaultScheduler.schedule(timetable);

		Thread.sleep(TimeUnit.SECONDS.toMillis(11));

		this.defaultScheduler.stopScheduling();

		assertNotNull("Execution list is null.", executions);
		assertEquals("Wrong count of executions.", 3, executions.size());
	}

}
