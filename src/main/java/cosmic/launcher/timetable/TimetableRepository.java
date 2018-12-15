package cosmic.launcher.timetable;

import java.util.List;

/**
 * Repository which enables collection like access to {@link Timetable}s.
 * 
 * @author Philipp Buchholz
 */
public interface TimetableRepository {

	List<Timetable> findAll();

}
