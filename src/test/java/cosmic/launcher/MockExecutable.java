package cosmic.launcher;

import cosmic.launcher.execution.Executable;
import cosmic.launcher.execution.ExecutionContext;

/**
 * Represents a mock {@link Executable} which is only used for testing purposes.
 * 
 * @author Philipp Buchholz
 */
public class MockExecutable implements Executable {

	@Override
	public void execute(ExecutionContext executionContext) {

	}

	@Override
	public String name() {
		return "mock-executable";
	}

}
