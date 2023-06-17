package it.polito.po.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import timesheet.Timesheet;
import timesheet.TimesheetException;

public class TestR3 {

	private Timesheet timesheet;

	@Before
	public void setUp() {
		timesheet = new Timesheet();
	}

	@Test
	public void testProfile() throws TimesheetException {
		String firstProfileID = timesheet.createProfile(0, 8, 5, 8, 8, 6, 0);
		String secondProfileID = timesheet.createProfile(0, 8, 7, 7, 5, 6, 0);
		assertNotNull("No profile ID returned", firstProfileID);
		assertNotNull("No profile ID returned", secondProfileID);
		assertEquals("Sun: 0; Mon: 8; Tue: 5; Wed: 8; Thu: 8; Fri: 6; Sat: 0", timesheet.getProfile(firstProfileID));
		assertEquals("Sun: 0; Mon: 8; Tue: 7; Wed: 7; Thu: 5; Fri: 6; Sat: 0", timesheet.getProfile(secondProfileID));
	}

	@Test(expected = TimesheetException.class)
	public void testCreateProfileInvalid() throws TimesheetException {
		timesheet.createProfile(0, 8, 5, 8, 8, 6, 0, 0);
	}

	@Test(expected = TimesheetException.class)
	public void testGetProfileInvalid() throws TimesheetException {
		timesheet.getProfile("Non-existing");
	}

	@Test
	public void testWorker() throws TimesheetException {
		String profileID = timesheet.createProfile(0, 8, 8, 8, 8, 8, 0);
		assertNotNull("No profile ID returned", profileID);
		String firstWorkerID = timesheet.createWorker("Marco", "Rossi", profileID);
		String secondWorkerID = timesheet.createWorker("Giovanni", "Verdi", profileID);
		assertNotNull("No worker ID returned", firstWorkerID);
		assertNotNull("No worker ID returned", secondWorkerID);
		assertEquals("Marco Rossi (Sun: 0; Mon: 8; Tue: 8; Wed: 8; Thu: 8; Fri: 8; Sat: 0)",
				timesheet.getWorker(firstWorkerID));
		assertEquals("Giovanni Verdi (Sun: 0; Mon: 8; Tue: 8; Wed: 8; Thu: 8; Fri: 8; Sat: 0)",
				timesheet.getWorker(secondWorkerID));
	}

	@Test(expected = TimesheetException.class)
	public void testCreateWorkerInvalid() throws TimesheetException {
		timesheet.createWorker("Marco", "Rossi", "Non-existing");
	}

	@Test(expected = TimesheetException.class)
	public void testGetWorkderInvalid() throws TimesheetException {
		timesheet.getWorker("Non-existing");
	}

}
