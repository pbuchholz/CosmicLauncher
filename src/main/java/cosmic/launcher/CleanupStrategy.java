package cosmic.launcher;

/**
 * Provides the possibility to clean up system resources before shutdown.
 * 
 * @author Philipp Buchholz
 */
public interface CleanupStrategy {

	void cleanup();
	
}
