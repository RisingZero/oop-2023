package timesheet;

import java.util.List;
import java.util.Map;

public class Timesheet {

	// R1
	public void setHolidays(int... holidays) {
	}
	
	public boolean isHoliday(int day) {
		return false;
	}
	
	public void setFirstWeekDay(int weekDay) throws TimesheetException {
	}
	
	public int getWeekDay(int day) throws TimesheetException {
	    return -1;
	}
	
	// R2
	public void createProject(String projectName, int maxHours) throws TimesheetException {
	}
	
	public List<String> getProjects() {
        return null;
	}
	
	public void createActivity(String projectName, String activityName) throws TimesheetException {
	}
	
	public void closeActivity(String projectName, String activityName) throws TimesheetException {
	}
	
	public List<String> getOpenActivities(String projectName) throws TimesheetException {
        return null;
	}

	// R3
	public String createProfile(int... workHours) throws TimesheetException {
        return null;
	}
	
	public String getProfile(String profileID) throws TimesheetException {
        return null;
	}
	
	public String createWorker(String name, String surname, String profileID) throws TimesheetException {
        return null;
	}
	
	public String getWorker(String workerID) throws TimesheetException {
        return null;
	}
	
	// R4
	public void addReport(String workerID, String projectName, String activityName, int day, int workedHours) throws TimesheetException {
	}
	
	public int getProjectHours(String projectName) throws TimesheetException {
        return -1;
	}
	
	public int getWorkedHoursPerDay(String workerID, int day) throws TimesheetException {
        return -1;
	}
	
	// R5
	public Map<String, Integer> countActivitiesPerWorker() {
        return null;
	}
	
	public Map<String, Integer> getRemainingHoursPerProject() {
        return null;
	}
	
	public Map<String, Map<String, Integer>> getHoursPerActivityPerProject() {
        return null;
	}
}
