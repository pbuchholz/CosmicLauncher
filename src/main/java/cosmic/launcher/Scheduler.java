package cosmic.launcher;

import java.util.List;

import cosmic.launcher.execution.Execution;
import cosmic.launcher.timetable.Timetable;

/**
 * Represents a Scheduler which schedules {@link Timetable}s.
 * 
 * @author Philipp Buchholz
 */
public interface Scheduler {

	/**
	 * Schedules the Executables associated with the TimetableEntry of the passed in
	 * {@link Timetable} and returns a {@link List} containing an {@link Execution}
	 * for each schedules run.
	 * 
	 * @param timetable
	 * @return
	 */
	List<Execution> schedule(Timetable timetable);
	
	/**
	 * Stops the scheduling currently active.
	 */
	void stopScheduling();
	

}
