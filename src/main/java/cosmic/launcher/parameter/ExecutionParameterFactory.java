package cosmic.launcher.parameter;

import java.util.List;

import cosmic.launcher.execution.Executable;

/**
 * Creates {@link ExecutionParameter}s for {@link Executable}s.
 * 
 * @author Philipp Buchholz
 */
public interface ExecutionParameterFactory {

	/**
	 * Finds the {@link List} of {@link ExecutionParameter}s for the passed in
	 * {@link ToExecutableMapping}.
	 * 
	 * @param toExecutableMapping
	 * @return
	 */
	List<ExecutionParameter> findExecutionParameterFor(ToExecutableMapping toExecutableMapping);

}
