package timesheet;

import java.util.*;

public class Project implements Comparable<Project> {
	
	private String name;
	private int hours;
	private Map<String,Activity> activities = new TreeMap<String,Activity>();
	
	public Project(String name, int hours) {
		this.name = name;
		this.hours = hours;
	}
	
	public String getName() {
		return name;
	}
	
	public void setHours(int hours) {
		this.hours = hours;
	}
	
	public int getHours() {
		return hours;
	}
	
	public List<Activity> getActivities() {
		return new ArrayList<Activity>(activities.values());
	}
	
	public List<Activity> getActivities(boolean completed) {
		return this.getActivities().stream()
			.filter((Activity a) -> a.isCompleted() == completed)
			.sorted()
			.toList();
	}
	
	public Project addActivity(String name) {
		this.activities.put(name, new Activity(name));
		
		return this;
	}
	
	public Project closeActivity(String activityName) throws TimesheetException {
		if (!activities.containsKey(activityName))
			throw new TimesheetException();
		
		activities.get(activityName).complete();
		
		return this;
	}
	
	@Override
	public String toString() {
		return String.format("%s: %d", name, hours);
	}
	
	@Override
	public int compareTo(Project o) {
		return this.name.compareTo(o.name);
	}
	
	@Override
	public boolean equals(Object o) {
		Project oth = (Project)o;
		return this.name.equals(oth.name);
	}
}
