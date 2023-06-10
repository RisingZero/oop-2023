package timesheet;

public class WeekDay {
	
	final static String[] days = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
	final static int daysInAWeek = 7;
	
	private int value = 1;
	
	public WeekDay(int dayValue) throws TimesheetException {
		if (dayValue < 0 || dayValue > 6)
			throw new TimesheetException();
		
		this.value = dayValue;
	}
	
	public WeekDay() {
	}
	
	public int value() {
		return value;
	}
	
	@Override
	public String toString() {
		return days[this.value];
	}
	
	@Override
	public boolean equals(Object o) {
		WeekDay oth = (WeekDay)o;
		return this.value == oth.value;
	}
	
	public static String getDayName(int day) {
		if (day >= 0 && day <= WeekDay.daysInAWeek)
			return days[day];
		return "NOTADAY";
	}
	
	public static String getDayShortName(int day) {
		return WeekDay.getDayName(day).substring(0, 3);
	}
}
