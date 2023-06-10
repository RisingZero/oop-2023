package timesheet;

public class Report {
	
	private Worker worker;
	private Project project;
	private Activity activity;
	private int day;
	private int hours;
	
	public Report(Worker w, Project p, Activity a, int d, int h) throws TimesheetException {
		worker = w;
		project = p;
		activity = a;
		hours = h;
		day = d;
	}
	
	public Worker getWorker() {
		return worker;
	}
	
	public Project getProject() {
		return project;
	}
	
	public Activity getActivity() {
		return activity;
	}
	
	public int getDay() {
		return day;
	}
	
	public int getHours() {
		return hours;
	}
}
