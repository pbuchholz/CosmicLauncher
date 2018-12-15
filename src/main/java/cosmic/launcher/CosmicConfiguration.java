package cosmic.launcher;

/**
 * Enum singleton which holds the configuration of the Cosmic launcher.
 * 
 * @author Philipp Buchholz
 */
public enum CosmicConfiguration {

	INSTANCE;

	private int concurrentExecuteables;

	public int getConcurrentExecuteables() {
		return this.concurrentExecuteables;
	}

	public void setConcurrentExecuteables(int conccurentExecuteables) {
		this.concurrentExecuteables = conccurentExecuteables;
	}

}
