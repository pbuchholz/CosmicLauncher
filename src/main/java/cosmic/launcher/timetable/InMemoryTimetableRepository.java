package cosmic.launcher.timetable;

import java.util.ArrayList;
import java.util.List;

import cosmic.launcher.CopyDirectoryExecuteable;

/**
 * In memory implementation of {@link TimetableRepository}.
 * 
 * @author Philipp Buchholz
 */
public class InMemoryTimetableRepository implements TimetableRepository {

	@Override
	public List<Timetable> findAll() {
		Timetable timetable = new Timetable();
		timetable.setTimetableName("CopyDirectoryTimetable");
		timetable.addTimetableEntry(TimetableEntry.Builder() //
				.seconds(10) //
				.executeableType(CopyDirectoryExecuteable.class) //
				.build());

		List<Timetable> timetables = new ArrayList<>();
		timetables.add(timetable);
		return timetables;
	}

}
