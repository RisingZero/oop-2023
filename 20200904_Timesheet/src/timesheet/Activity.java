package timesheet;

public class Activity implements Comparable<Activity> {
	
	private String name;
	private boolean completed = false;
	
	public Activity(String name) {
		this.name = name;
	}
	
	public void complete() {
		this.completed = true;
	}
	
	public String getName() {
		return name;
	}
	
	public boolean isCompleted() {
		return completed;
	}
	
	@Override
	public int compareTo(Activity oth) {
		return this.name.compareTo(oth.name);
	}
	
	@Override
	public boolean equals(Object o) {
		Activity oth = (Activity)o;
		return this.name.equals(oth.name);
	}
}
