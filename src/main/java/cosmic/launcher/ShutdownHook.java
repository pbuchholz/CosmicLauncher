package cosmic.launcher;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Simple shutdown hook which can be exposed over JMX to signal shutdown to the
 * application.
 * 
 * @author Philipp Buchholz
 */
public class ShutdownHook implements ShutdownHookMBean {

	private AtomicBoolean shutdownSignaled = new AtomicBoolean(false);
	private CleanupStrategy cleanupStrategy;

	private ShutdownHook() {

	}

	public static ShutdownHook newInstance() {
		return new ShutdownHook();
	}

	public static ShutdownHook newInstanceWithCleanupStrategy(CleanupStrategy cleanupStrategy) {
		ShutdownHook shutdownHook = new ShutdownHook();
		shutdownHook.cleanupStrategy = cleanupStrategy;
		return shutdownHook;
	}

	public boolean isShutdownSignaled() {
		return this.shutdownSignaled.get();
	}

	@Override
	public void shutdown() {
		if (this.shutdownSignaled.compareAndSet(false, true)) {
			if (null != this.cleanupStrategy) {
				this.cleanupStrategy.cleanup();
			}
		}
	}

}
