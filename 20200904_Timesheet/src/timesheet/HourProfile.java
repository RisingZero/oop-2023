package timesheet;


import java.util.random.RandomGenerator;
import java.util.ArrayList;

public class HourProfile {

	private String id;
	private int[] value = {0,0,0,0,0,0,0};
	
	public HourProfile(int[] profile) throws TimesheetException {
		if (profile.length != WeekDay.daysInAWeek)
			throw new TimesheetException();
		
		this.id = String.valueOf(RandomGenerator.getDefault().nextLong());
		this.value = profile;
	}
	
	public String getId() {
		return id;
	}
	
	public int getHourOfDay(int day) throws TimesheetException {
		if (day < 0 || day >= WeekDay.daysInAWeek)
			throw new TimesheetException();
		
		return value[day];
	}
	
	@Override
	public String toString() {
		ArrayList<String> s = new ArrayList<String>();
		
		for (int i = 0; i < WeekDay.daysInAWeek; i++) {
			s.add(String.format("%s: %d", WeekDay.getDayShortName(i), value[i]));
		}
		
		return String.join("; ", s);
	}
}
