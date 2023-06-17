package it.polito.po.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import timesheet.*;

public class TestR1 {

	private Timesheet timesheet;
	
    @Before
    public void setUp() {
    	timesheet = new Timesheet();
    }

    @Test
    public void testHolidays() throws TimesheetException {
    	timesheet.setHolidays(1, 6, 115, 227, 359, 360);
    	assertTrue(timesheet.isHoliday(227));
    	assertFalse(timesheet.isHoliday(228));
    }
    
    @Test
    public void testHolidaysInvalid() throws TimesheetException {
    	timesheet.setHolidays(0);
    	System.out.println(timesheet.isHoliday(0));
    	assertFalse(timesheet.isHoliday(0));
    }
    
    @Test
    public void testHolidaysDuplicated() throws TimesheetException {
    	timesheet.setHolidays(1);
    	timesheet.setHolidays(2);
    	assertTrue(timesheet.isHoliday(1));
    	assertFalse(timesheet.isHoliday(2));
    }
    
    @Test
    public void testWeekDay() throws TimesheetException {
    	timesheet.setFirstWeekDay(0);
    	assertEquals(0, timesheet.getWeekDay(1));
    	timesheet.setFirstWeekDay(6);
    	assertEquals(6, timesheet.getWeekDay(1));
    	assertEquals(0, timesheet.getWeekDay(2));
    }
    
    @Test(expected=TimesheetException.class)
    public void testFirstWeekDayInvalid() throws TimesheetException {
    	timesheet.setFirstWeekDay(-1);
    }
    
    @Test(expected=TimesheetException.class)
    public void testFirstWeekDayInvalidUpper() throws TimesheetException {
    	timesheet.setFirstWeekDay(7);
    }
    
    @Test(expected=TimesheetException.class)
    public void testGetWeekDayInvalid() throws TimesheetException {
    	timesheet.getWeekDay(0);
    }
    
    @Test
    public void testGetWeekDayUndefined() throws TimesheetException {
    	assertEquals(2, timesheet.getWeekDay(9));
    }

}
