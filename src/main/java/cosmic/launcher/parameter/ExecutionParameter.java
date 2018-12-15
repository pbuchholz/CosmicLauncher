package cosmic.launcher.parameter;

import javax.json.bind.annotation.JsonbProperty;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import cosmic.launcher.execution.Executable;

/**
 * Parameter used from {@link Executable}s.
 * 
 * @author Philipp Buchholz
 */
public class ExecutionParameter {

	@JsonbProperty("parameter-name")
	private String name;

	@JsonbProperty("parameter-value")
	private String value;

	@JsonbProperty("toExecutableMapping")
	private ToExecutableMapping toExecutableMapping;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public ToExecutableMapping getToExecutableMapping() {
		return toExecutableMapping;
	}

	public void setToExecutableMapping(ToExecutableMapping toExecutableMapping) {
		this.toExecutableMapping = toExecutableMapping;
	}

	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj, false);
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this, false);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
	}

}
