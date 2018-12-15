package cosmic.launcher.execution;

import cosmic.launcher.parameter.ToExecutableMapping;
import cosmic.launcher.timetable.TimetableEntry;

/**
 * Factory for {@link ExecutionContext}s.
 * 
 * @author Philipp Buchholz
 */
public interface ExecutionContextFactory {

	/**
	 * Creates an {@link ExecutionContext} based on the {@link ToExecutableMapping}
	 * passed in.
	 * 
	 * @param toExecutableMapping
	 * @param timetableEntry
	 * @return
	 */
	ExecutionContext createExecutionContext(ToExecutableMapping toExecutableMapping, TimetableEntry timetableEntry);

}
