package cosmic.launcher.execution;

import java.util.UUID;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Return by the {@link SchedulerTest} to enable tracking of
 * {@link Executable}s.
 * 
 * @author Philipp Buchholz
 */
public class Execution {

	private boolean done;
	private boolean erronous;
	private UUID executableIdentifier;
	private Exception thrownException;

	public UUID getExecutableIdentifier() {
		return executableIdentifier;
	}

	public void setExecutableIdentifier(UUID executableIdentifier) {
		this.executableIdentifier = executableIdentifier;
	}

	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj, false);
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this, false);
	}

	public boolean isDone() {
		return done;
	}

	public void setDone(boolean done) {
		this.done = done;
	}

	public boolean isErronous() {
		return erronous;
	}

	public void setErronous(boolean erronous) {
		this.erronous = erronous;
	}

	public Exception getThrownException() {
		return thrownException;
	}

	public void setThrownException(Exception thrownException) {
		this.thrownException = thrownException;
	}

}
