package it.polito.po.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import timesheet.Timesheet;
import timesheet.TimesheetException;

public class TestR4 {

	private Timesheet timesheet;
	private String profileID;
	private String workerID;

	@Before
	public void setUp() throws TimesheetException {
		timesheet = new Timesheet();
		timesheet.createProject("Development", 20);
		timesheet.createActivity("Development", "Testing");
		timesheet.createActivity("Development", "Coding");
		profileID = timesheet.createProfile(0, 8, 8, 8, 8, 6, 0);
		assertNotNull("No profile id returned", profileID);
		workerID = timesheet.createWorker("Marco", "Rossi", profileID);
	}
	
	@Test
	public void testReportEmpty() throws TimesheetException {
		assertEquals(0, timesheet.getWorkedHoursPerDay(workerID, 1));
		assertEquals(0, timesheet.getProjectHours("Development"));
	}
	
	@Test
	public void testReport() throws TimesheetException {
		timesheet.addReport(workerID, "Development", "Coding", 1, 4);
		timesheet.addReport(workerID, "Development", "Testing", 1, 4);
		timesheet.addReport(workerID, "Development", "Testing", 2, 8);
		assertEquals(8, timesheet.getWorkedHoursPerDay(workerID, 1));
		assertEquals(16, timesheet.getProjectHours("Development"));
	}
	
	@Test(expected = TimesheetException.class)
	public void testProjectHoursInvalid() throws TimesheetException {
		timesheet.getProjectHours("Accounting");
	}

	@Test(expected = TimesheetException.class)
	public void testWorkedHoursPerDayInvalidWorker() throws TimesheetException {
		timesheet.getWorkedHoursPerDay("Invalid", 1);
	}

	@Test(expected = TimesheetException.class)
	public void testWorkedHoursPerDayInvalidDay() throws TimesheetException {
		timesheet.getWorkedHoursPerDay(workerID, 0);
	}

	@Test(expected = TimesheetException.class)
	public void testReportInvalidWorker() throws TimesheetException {
		timesheet.addReport("Invalid", "Development", "Coding", 1, 4);
	}
	
	@Test(expected = TimesheetException.class)
	public void testReportInvalidDay() throws TimesheetException {
		timesheet.addReport(workerID, "Development", "Coding", 0, 4);
	}
	
	@Test(expected = TimesheetException.class)
	public void testReportInvalidHoliday() throws TimesheetException {
		timesheet.setHolidays(1);
		timesheet.addReport(workerID, "Development", "Coding", 1, 4);
	}
	
	@Test(expected = TimesheetException.class)
	public void testReportInvalidWorkedHours() throws TimesheetException {
		timesheet.addReport(workerID, "Development", "Coding", 1, -1);
	}
	
	@Test(expected = TimesheetException.class)
	public void testReportInvalidWorkDay() throws TimesheetException {
		timesheet.setFirstWeekDay(2);
		timesheet.addReport(workerID, "Development", "Coding", 6, 4);
	}
	
	@Test(expected = TimesheetException.class)
	public void testReportInvalidHoursPerDay() throws TimesheetException {
		timesheet.addReport(workerID, "Development", "Coding", 5, 8);
	}
	
	@Test(expected = TimesheetException.class)
	public void testReportInvalidProject() throws TimesheetException {
		timesheet.addReport(workerID, "Accounting", "Coding", 1, 8);
	}
	
	@Test(expected = TimesheetException.class)
	public void testReportInvalidActivity() throws TimesheetException {
		timesheet.addReport(workerID, "Development", "Debugging", 1, 8);
	}
	
	@Test(expected = TimesheetException.class)
	public void testReportInvalidHoursPerProject() throws TimesheetException {
		timesheet.addReport(workerID, "Development", "Coding", 1, 8);
		timesheet.addReport(workerID, "Development", "Coding", 2, 8);
		timesheet.addReport(workerID, "Development", "Coding", 3, 8);
	}
	
	@Test(expected = TimesheetException.class)
	public void testReportClosedActivity() throws TimesheetException {
		timesheet.closeActivity("Development", "Coding");
		timesheet.addReport(workerID, "Development", "Coding", 1, 8);
	}

}
