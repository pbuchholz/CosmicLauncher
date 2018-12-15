package cosmic.launcher.execution;

import java.util.List;

import cosmic.launcher.parameter.ExecutionParameter;
import cosmic.launcher.parameter.ExecutionParameterFactory;
import cosmic.launcher.parameter.ToExecutableMapping;
import cosmic.launcher.timetable.TimetableEntry;

/**
 * Default implementation of {@link ExecutionContextFactory}.
 * 
 * @author Philipp Buchholz
 */
public class DefaultExecutionContextFactory implements ExecutionContextFactory {

	private ExecutionParameterFactory executionParameterFactory;

	public DefaultExecutionContextFactory(ExecutionParameterFactory executionParameterFactory) {
		this.executionParameterFactory = executionParameterFactory;
	}

	@Override
	public ExecutionContext createExecutionContext(ToExecutableMapping toExecutableMapping,
			TimetableEntry timetableEntry) {
		List<ExecutionParameter> executionParameters = executionParameterFactory
				.findExecutionParameterFor(toExecutableMapping);
		return ExecutionContext.builder() //
				.executionParameters(executionParameters) //
				.timetableEntry(timetableEntry) //
				.build();
	}

}
