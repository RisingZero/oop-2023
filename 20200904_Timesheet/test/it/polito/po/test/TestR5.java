package it.polito.po.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import timesheet.Timesheet;
import timesheet.TimesheetException;

public class TestR5 {

	private Timesheet timesheet;
	private String profileID;
	private String firstWorkerID;
	private String secondWorkerID;

	@Before
	public void setUp() throws TimesheetException {
		timesheet = new Timesheet();
		timesheet.createProject("Development", 20);
		timesheet.createActivity("Development", "Planning");
		timesheet.createActivity("Development", "Testing");
		timesheet.createActivity("Development", "Coding");
		timesheet.createProject("Research", 20);
		timesheet.createActivity("Research", "Planning");
		timesheet.createActivity("Research", "Reporting");
		profileID = timesheet.createProfile(0, 8, 8, 8, 8, 6, 0);
		assertNotNull(profileID);
		firstWorkerID = timesheet.createWorker("Marco", "Rossi", profileID);
		assertNotNull(firstWorkerID);
		secondWorkerID = timesheet.createWorker("Giovanni", "Bianchi", profileID);
		assertNotNull(secondWorkerID);
		timesheet.addReport(firstWorkerID, "Development", "Planning", 1, 2);
		timesheet.addReport(firstWorkerID, "Development", "Coding", 1, 4);
		timesheet.addReport(firstWorkerID, "Development", "Testing", 1, 2);
		timesheet.addReport(firstWorkerID, "Research", "Planning", 2, 8);
		timesheet.addReport(secondWorkerID, "Research", "Planning", 1, 4);
		timesheet.addReport(secondWorkerID, "Development", "Coding", 1, 4);
		timesheet.addReport(secondWorkerID, "Research", "Planning", 2, 6);
		timesheet.addReport(secondWorkerID, "Research", "Reporting", 2, 2);
	}

	@Test
	public void testCountActivitiesPerWorker() throws TimesheetException {
		Map<String, Integer> activitiesPerWorker = timesheet.countActivitiesPerWorker();
		assertNotNull("Missing return value", activitiesPerWorker);
		assertEquals(2, activitiesPerWorker.size());
		assertEquals(Integer.valueOf(4), activitiesPerWorker.get(firstWorkerID));
		assertEquals(Integer.valueOf(3), activitiesPerWorker.get(secondWorkerID));
	}

	@Test
	public void testGetRemainingHoursPerProject() throws TimesheetException {
		Map<String, Integer> remainingHoursPerProject = timesheet.getRemainingHoursPerProject();
		assertNotNull("Missing return value", remainingHoursPerProject);
		assertEquals(2, remainingHoursPerProject.size());
		assertEquals(Integer.valueOf(8), remainingHoursPerProject.get("Development"));
		assertEquals(Integer.valueOf(0), remainingHoursPerProject.get("Research"));
	}

	@Test
	public void testGetHoursPerActivityPerProject() throws TimesheetException {
		Map<String, Map<String, Integer>> hoursPerActivityPerProject = timesheet.getHoursPerActivityPerProject();
		assertNotNull("Missing return value", hoursPerActivityPerProject);
		assertEquals(2, hoursPerActivityPerProject.size());
		Map<String, Integer> development = hoursPerActivityPerProject.get("Development");
		assertNotNull("Missing return value", development);
		assertEquals(3, development.size());
		assertEquals(Integer.valueOf(2), development.get("Planning"));
		assertEquals(Integer.valueOf(8), development.get("Coding"));
		assertEquals(Integer.valueOf(2), development.get("Testing"));
		Map<String, Integer> research = hoursPerActivityPerProject.get("Research");
		assertNotNull("Missing return value", research);
		assertEquals(2, research.size());
		assertEquals(Integer.valueOf(18), research.get("Planning"));
		assertEquals(Integer.valueOf(2), research.get("Reporting"));
	}

}
