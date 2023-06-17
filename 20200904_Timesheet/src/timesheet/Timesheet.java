package timesheet;

import java.util.List;
import java.util.Map;
import java.util.*;
import static java.util.stream.Collectors.*;

public class Timesheet {
	
	private Set<Integer> holidays = new TreeSet<Integer>();
	private WeekDay dayOfWeek = new WeekDay();
	
	private Map<String,Project> projects = new TreeMap<String,Project>();
	private Map<String,HourProfile> hprofiles = new HashMap<String,HourProfile>();
	private Map<String,Worker> workers = new HashMap<String,Worker>();
	private ArrayList<Report> reports = new ArrayList<Report>();

	// R1
	public void setHolidays(int... holidays) {
		if (this.holidays.size() > 0)
			return;
		
		for (int day : holidays) {
			if (day >= 1 && day <= 365) {
				this.holidays.add(day);
			}
		}
	}
	
	public boolean isHoliday(int day) {
		return this.holidays.contains(day);
	}
	
	public void setFirstWeekDay(int weekDay) throws TimesheetException {
		this.dayOfWeek = new WeekDay(weekDay);
	}
	
	public int getWeekDay(int day) throws TimesheetException {
		if (day < 1)
			throw new TimesheetException();
		
		WeekDay d = new WeekDay((this.dayOfWeek.value() + day - 1) % WeekDay.daysInAWeek);
	    return d.value();
	}
	
	// R2
	public void createProject(String projectName, int maxHours) throws TimesheetException {
		if (maxHours < 0)
			throw new TimesheetException();
		
		if (!this.projects.containsKey(projectName)) {
			this.projects.put(projectName, new Project(projectName, maxHours));
		} else {
			this.projects.get(projectName).setHours(maxHours);
		}
	}
	
	public List<String> getProjects() {
		return projects.values().stream()
			.sorted(new Comparator<Project>() {
				@Override public int compare(Project p1, Project p2) {
					if (p1.getHours() == p2.getHours())
						return p1.compareTo(p2);
					else
						return p2.getHours() - p1.getHours();
				}
			})
			.map(Project::toString)
			.toList();
	}
	
	public void createActivity(String projectName, String activityName) throws TimesheetException {
		if (!projects.containsKey(projectName))
			throw new TimesheetException();
		
		projects.get(projectName)
			.addActivity(activityName);
	}
	
	public void closeActivity(String projectName, String activityName) throws TimesheetException {
		if (!projects.containsKey(projectName))
			throw new TimesheetException();
		
		projects.get(projectName)
			.closeActivity(activityName);
	}
	
	public List<String> getOpenActivities(String projectName) throws TimesheetException {
		if (!projects.containsKey(projectName))
			throw new TimesheetException();
		
		return projects.get(projectName).getActivities(false).stream()
			.map(Activity::getName)
			.toList();
	}

	// R3
	public String createProfile(int... workHours) throws TimesheetException {
		HourProfile hp = new HourProfile(workHours);
		
		hprofiles.put(hp.getId(), hp);
        return hp.getId();
	}
	
	public String getProfile(String profileID) throws TimesheetException {
		if (!hprofiles.containsKey(profileID))
			throw new TimesheetException();
		
        return hprofiles.get(profileID).toString();
	}
	
	public String createWorker(String name, String surname, String profileID) throws TimesheetException {
		if (!hprofiles.containsKey(profileID))
			throw new TimesheetException();
		
		Worker w = new Worker(name, surname, hprofiles.get(profileID));
		workers.put(w.getId(), w);
        return w.getId();
	}
	
	public String getWorker(String workerID) throws TimesheetException {
		if (!workers.containsKey(workerID))
			throw new TimesheetException();
		
		return workers.get(workerID).toString();
	}
	
	// R4
	public void addReport(String workerID, String projectName, String activityName, int day, int workedHours) throws TimesheetException {
		if (!workers.containsKey(workerID) || !projects.containsKey(projectName))
			throw new TimesheetException();
		
		if (day < 1 || day > 365 || this.isHoliday(day))
			throw new TimesheetException();
		
		if (workedHours < 0)
			throw new TimesheetException();
		
		if (this.workers.get(workerID).getHourProfile().getHourOfDay(this.getWeekDay(day)) < workedHours)
			throw new TimesheetException();
		
		Worker w = this.workers.get(workerID);
		Project p = this.projects.get(projectName);
		List<Activity> aList = p.getActivities();
		
		if (!aList.contains(new Activity(activityName)))
			throw new TimesheetException();
		
		Activity a = aList.get(aList.indexOf(new Activity(activityName)));
		if (a.isCompleted())
			throw new TimesheetException();
		
		int allocatedHours = this.reports.stream()
			.filter((Report r) -> r.getProject() == p)
			.collect(
				summingInt(Report::getHours)
			);
		
		if (allocatedHours + workedHours > p.getHours())
			throw new TimesheetException();
		
		reports.add(new Report(w,p,a,day,workedHours));
	}
	
	public int getProjectHours(String projectName) throws TimesheetException {
		if (!projects.containsKey(projectName))
			throw new TimesheetException();
		
		Project p = projects.get(projectName);
        return this.reports.stream()
			.filter((Report r) -> r.getProject() == p)
			.collect(
				summingInt(Report::getHours)
			);
	}
	
	public int getWorkedHoursPerDay(String workerID, int day) throws TimesheetException {
		if (day < 1 || !workers.containsKey(workerID))
			throw new TimesheetException();
		
		Worker w = workers.get(workerID);
		
        return this.reports.stream()
    		.filter((Report r) -> r.getWorker() == w)
    		.filter((Report r) -> r.getDay() == day)
    		.collect(
				summingInt(Report::getHours)
    		);
	}
	
	// R5
	public Map<String, Integer> countActivitiesPerWorker() {
		return reports.stream()
			.filter((Report r) -> r.getHours() >= 1)
			.collect(
				groupingBy(
					(Report r) -> r.getWorker().getId(),
					mapping(
						(Report r) -> {return r.getProject().getName() + r.getActivity().getName();},
						collectingAndThen(toSet(), s -> s.size())
					)
				)
			);
	}
	
	public Map<String, Integer> getRemainingHoursPerProject() {
		Map<String,Integer> ph = reports.stream()
			.collect(
				groupingBy(
					(Report r) -> r.getProject().getName(),
					summingInt(Report::getHours)
				)
			);
		
		Map<String,Integer> pmh = projects.values().stream()
			.collect(
				groupingBy(
					Project::getName,
					summingInt(Project::getHours)
				)
			);
		
        Map<String,Integer> rh = new HashMap<>();
        for (String p: ph.keySet()) {
        	rh.put(p, pmh.get(p) - ph.get(p));
        }
        return rh;
	}
	
	public Map<String, Map<String, Integer>> getHoursPerActivityPerProject() {
        return reports.stream()
        	.collect(
        		groupingBy(
        			(Report r) -> r.getProject().getName(),
        			groupingBy(
        				(Report r) -> r.getActivity().getName(),
        				summingInt(Report::getHours)
        			)
        		)
        	);
	}
}
