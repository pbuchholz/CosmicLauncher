package cosmic.launcher.execution;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import cosmic.launcher.parameter.ExecutionParameter;
import cosmic.launcher.timetable.TimetableEntry;

/**
 * Context in which an {@link Executable} is being executed.
 * 
 * @author Philipp Buchholz
 */
public class ExecutionContext {

	private List<ExecutionParameter> executionParameters;
	private TimetableEntry timetableEntry;

	public List<ExecutionParameter> getExecutionParameters() {
		return this.executionParameters;
	}

	public ExecutionParameter executionParameterByName(String name) {
		return executionParameters.stream() //
				.filter((parameter) -> parameter.getName().equals(name)) //
				.findFirst() //
				.get();
	}

	public TimetableEntry getTimetableEntry() {
		return this.timetableEntry;
	}

	public static Builder builder() {
		return new Builder();
	}

	public final static class Builder {
		private ExecutionContext executionContext;

		public Builder() {
			this.executionContext = new ExecutionContext();
		}

		public Builder executionParameters(List<ExecutionParameter> executionParameters) {
			this.executionContext.executionParameters = executionParameters;
			return this;
		}

		public Builder executionParameter(ExecutionParameter executionParameter) {
			if (null == this.executionContext.executionParameters) {
				this.executionContext.executionParameters = new ArrayList<>();
			}
			this.executionContext.executionParameters.add(executionParameter);
			return this;
		}

		public Builder timetableEntry(TimetableEntry timetableEntry) {
			this.executionContext.timetableEntry = timetableEntry;
			return this;
		}

		public ExecutionContext build() {
			return this.executionContext;
		}

	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
	}

}
