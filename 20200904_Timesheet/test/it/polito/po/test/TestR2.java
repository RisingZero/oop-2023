package it.polito.po.test;

import static org.junit.Assert.*;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import timesheet.Timesheet;
import timesheet.TimesheetException;

public class TestR2 {

	private Timesheet timesheet;

	@Before
	public void setUp() {
		timesheet = new Timesheet();
	}

	@Test
	public void testProjects() throws TimesheetException {
		timesheet.createProject("Development", 10);
		timesheet.createProject("Accounting", 10);
		timesheet.createProject("Research", 15);
		List<String> projects = timesheet.getProjects();
		assertNotNull("No project list returned", projects);
		assertEquals(3, projects.size());
		assertEquals("Research: 15", projects.get(0));
		assertEquals("Accounting: 10", projects.get(1));
		assertEquals("Development: 10", projects.get(2));
	}

	@Test(expected = TimesheetException.class)
	public void testProjectsInvalid() throws TimesheetException {
		timesheet.createProject("Development", -1);
	}

	@Test
	public void testProjectsDuplicated() throws TimesheetException {
		timesheet.createProject("Development", 10);
		timesheet.createProject("Accounting", 10);
		timesheet.createProject("Research", 15);
		timesheet.createProject("Development", 20);
		List<String> projects = timesheet.getProjects();
		assertNotNull("No project list returned", projects);
		assertEquals(3, projects.size());
		assertEquals("Development: 20", projects.get(0));
		assertEquals("Research: 15", projects.get(1));
		assertEquals("Accounting: 10", projects.get(2));
	}

	@Test
	public void testActivites() throws TimesheetException {
		timesheet.createProject("Development", 10);
		timesheet.createActivity("Development", "Testing");
		timesheet.createActivity("Development", "Coding");
		List<String> activities = timesheet.getOpenActivities("Development");
		assertNotNull("No activity list returned", activities);
		assertEquals(2, activities.size());
		assertEquals("Coding", activities.get(0));
		assertEquals("Testing", activities.get(1));
	}

	@Test
	public void testActivitesEmpty() throws TimesheetException {
		timesheet.createProject("Development", 10);
		List<String> activities = timesheet.getOpenActivities("Development");
		assertNotNull("No activity list returned", activities);
		assertEquals(0, activities.size());
	}
	
	@Test(expected = TimesheetException.class)
	public void testActivitesInvalid() throws TimesheetException {
		timesheet.getOpenActivities("Development");
	}

	@Test(expected = TimesheetException.class)
	public void testCreateActivitesInvalid() throws TimesheetException {
		timesheet.createActivity("Development", "Testing");
	}
	
	@Test
	public void testClosedActivites() throws TimesheetException {
		timesheet.createProject("Development", 10);
		timesheet.createActivity("Development", "Testing");
		timesheet.createActivity("Development", "Coding");
		timesheet.closeActivity("Development", "Coding");
		List<String> activities = timesheet.getOpenActivities("Development");
		assertNotNull("No activity list returned", activities);
		assertEquals(1, activities.size());
		assertEquals("Testing", activities.get(0));
	}

	@Test(expected = TimesheetException.class)
	public void testCloseActivityInvalid() throws TimesheetException {
		timesheet.createProject("Development", 10);
		timesheet.closeActivity("Development", "Testing");
	}
	
	@Test(expected = TimesheetException.class)
	public void testCloseActivityProjectInvalid() throws TimesheetException {
		timesheet.closeActivity("Development", "Testing");
	}

}
