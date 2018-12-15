package cosmic.launcher.execution;

/**
 * Represents some work which is executed as a single unit.
 * 
 * @author Philipp Buchholz
 */
public interface Executable {

	/**
	 * Executes the {@link Executable} in the passed in {@link ExecutionContext}.
	 * 
	 * @param executionParameters
	 * @return
	 */
	void execute(ExecutionContext executionContext);

	/**
	 * Returns the name of this unit of work.
	 * 
	 * @return
	 */
	String name();

}
