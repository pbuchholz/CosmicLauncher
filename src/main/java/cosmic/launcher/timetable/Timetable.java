package cosmic.launcher.timetable;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import cosmic.launcher.execution.Executable;

/**
 * The Timetable describes which {@link Executable} has to be executed at which
 * point in time.
 * 
 * @author Philipp Buchholz
 */
public class Timetable {

	private List<TimetableEntry> timetableEntries;
	private String timetableName;

	public List<TimetableEntry> getTimetableEntries() {
		return this.timetableEntries;
	}

	public void addTimetableEntry(TimetableEntry timetableEntry) {
		if (null == this.timetableEntries) {
			this.timetableEntries = new ArrayList<>();
		}
		this.timetableEntries.add(timetableEntry);

	}

	public String getTimetableName() {
		return this.timetableName;
	}

	public void setTimetableName(String timetableName) {
		this.timetableName = timetableName;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
	}

}
