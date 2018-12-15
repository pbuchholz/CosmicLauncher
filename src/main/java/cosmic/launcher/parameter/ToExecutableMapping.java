package cosmic.launcher.parameter;

import javax.json.bind.annotation.JsonbProperty;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import cosmic.launcher.execution.Executable;

/**
 * Maps an {@link ExecutionParameter} to an {@link Executable}
 * 
 * @author Philipp Buchholz
 */
public class ToExecutableMapping {

	public static ToExecutableMapping create(String executableName) {
		ToExecutableMapping mapping = new ToExecutableMapping();
		mapping.setExecutableName(executableName);
		return mapping;
	}

	@JsonbProperty("executable-name")
	private String executableName;

	public String getExecutableName() {
		return this.executableName;
	}

	public void setExecutableName(String executableName) {
		this.executableName = executableName;
	}

	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj, false);
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this, false);
	}

}