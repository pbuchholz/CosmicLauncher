package cosmic.launcher.timetable;

import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import cosmic.launcher.execution.Executable;

/**
 * Represents a single entry in a {@link Timetable} with the type of
 * {@link Executable} and time settings.
 * 
 * @author Philipp Buchholz
 */
public class TimetableEntry {

	private Class<? extends Executable> executeableType;
	private int hours;
	private int minutes;
	private int seconds;

	public Class<? extends Executable> getExecutableType() {
		return executeableType;
	}

	public int getHours() {
		return hours;
	}

	public int getMinutes() {
		return minutes;
	}

	public int getSeconds() {
		return seconds;
	}

	public static TimetableEntryBuilder Builder() {
		return new TimetableEntryBuilder();
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
	}

	public static final class TimetableEntryBuilder {

		private TimetableEntry timetableEntry;

		public TimetableEntryBuilder() {
			this.timetableEntry = new TimetableEntry();
		}

		public TimetableEntryBuilder executeableType(Class<? extends Executable> executeableType) {
			this.timetableEntry.executeableType = executeableType;
			return this;
		}

		public TimetableEntryBuilder hours(int hours) {
			this.timetableEntry.hours = hours;
			return this;
		}

		public TimetableEntryBuilder minutes(int minutes) {
			this.timetableEntry.minutes = minutes;
			return this;
		}

		public TimetableEntryBuilder seconds(int seconds) {
			this.timetableEntry.seconds = seconds;
			return this;
		}

		public TimetableEntry build() {
			return this.timetableEntry;
		}

	}

	public long calculatePeriod() {
		return TimeUnit.HOURS.toMillis(this.hours) + //
				TimeUnit.MINUTES.toMillis(this.minutes) + //
				TimeUnit.SECONDS.toMillis(this.seconds);
	}

}