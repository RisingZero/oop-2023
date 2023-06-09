package example;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;
import java.util.Map;

import org.junit.Test;

import timesheet.Timesheet;
import timesheet.TimesheetException;

public class TestApp {

    @Test
    public void test() throws TimesheetException {
        // R1
        Timesheet timesheet = new Timesheet();
        
        timesheet.setHolidays(1, 6, 115, 227, 359, 360);
        assertTrue(timesheet.isHoliday(227));

        timesheet.setFirstWeekDay(0);
        assertEquals(0, timesheet.getWeekDay(1));

        // R2
        timesheet.createProject("Development", 13);
        timesheet.createProject("Research", 15);
        List<String> projects = timesheet.getProjects();
        assertNotNull(projects);
        assertEquals("Research: 15", projects.get(0));

        timesheet.createActivity("Development", "Testing");
        timesheet.createActivity("Development", "Coding");
        List<String> activities = timesheet.getOpenActivities("Development");
        assertNotNull(activities);
        assertEquals("Coding", activities.get(0));

        timesheet.closeActivity("Development", "Coding");
        activities = timesheet.getOpenActivities("Development");
        assertEquals("Testing", activities.get(0));

        // R3
        String profileID = timesheet.createProfile(0, 8, 5, 8, 8, 6, 0);
        assertNotNull(profileID);
        assertEquals("Sun: 0; Mon: 8; Tue: 5; Wed: 8; Thu: 8; Fri: 6; Sat: 0", timesheet.getProfile(profileID));

        try {
            timesheet.getProfile("Non-existing");
            fail("Non existing profile should rise an exception");
        } catch(TimesheetException e) {
            // OK!
        }
        
        String workerID = timesheet.createWorker("Marco", "Rossi", profileID);
        assertNotNull(workerID);
        assertEquals("Marco Rossi (Sun: 0; Mon: 8; Tue: 5; Wed: 8; Thu: 8; Fri: 6; Sat: 0)",
                timesheet.getWorker(workerID));

        timesheet.addReport(workerID, "Development", "Testing", 2, 4);
        timesheet.addReport(workerID, "Development", "Testing", 4, 8);
        assertEquals(8, timesheet.getWorkedHoursPerDay(workerID, 4));
        assertEquals(12, timesheet.getProjectHours("Development"));

        // R5
        
        Map<String, Integer> activitiesPerWorker = timesheet.countActivitiesPerWorker();
        assertNotNull(activitiesPerWorker);
        assertEquals(1, activitiesPerWorker.size());
        assertEquals(Integer.valueOf(1), activitiesPerWorker.get(workerID));

        Map<String, Integer> remainingHoursPerProject = timesheet.getRemainingHoursPerProject();
        assertNotNull(remainingHoursPerProject);
        assertEquals(Integer.valueOf(1), remainingHoursPerProject.get("Development"));
    }
}
