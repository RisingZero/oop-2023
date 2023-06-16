package it.polito.po.test;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import fit.Fit;
import fit.FitException;


public class TestR5 {
    private Fit f;
    
    @Before
    public void setUp() throws FitException {
    	this.f = new Fit();
    		f.addGymn("G1");
    		f.addGymn("G2");
    		f.addGymn("G3");
    		f.addLessons("G1", "Aerobica", 10, "1.8,1.9,2.8","Stefano", "Marco");
    		f.addLessons("G2", "Step", 5, "4.8,5.10","Giorgio","Stefano");
    		f.addLessons("G3", "Calisthenics", 10,"6.20","Giovanni","Luca");
    		f.addCustomer("C1"); //customer id 1
    		f.addCustomer("C2"); //customer id 2
    		f.addCustomer("C3"); //customer id 3
    		f.addCustomer("C4"); //customer id 4
    		f.addCustomer("C5"); //customer id 5
    		f.addCustomer("C6"); //customer id 6
    }
    
    
    @Test
    public void testAddLessonGivenWrongGymn() {
    	try {
        	f.addLessonGiven("G4", 1, 8, "Stefano");
    		fail ("Lesson given with wrong gymn name should not be allowed");
    	} catch(FitException ex) {} //ok
    }
    
    @Test
    public void testAddLessonGivenWrongDaySlot() {
    	try {
        	f.addLessonGiven("G1", 1, 10, "Stefano");
    		fail ("Lesson given with wrong slot should not be allowed");
    	} catch(FitException ex) {} //ok
    	try {
        	f.addLessonGiven("G1", 3, 8, "Stefano");
    		fail ("Lesson given with wrong day should not be allowed");
    	} catch(FitException ex) {} //ok   	
    	try {
        	f.addLessonGiven("G1", 2, 3, "Stefano");
    		fail ("Lesson given with wrong day and slot should not be allowed");
    	} catch(FitException ex) {} //ok   
    } 
    
    @Test
    public void testAddLessonGivenWrongInstructor() {
    	try {
        	f.addLessonGiven("G1", 1, 8, "Giovanni");
    		fail ("Lesson given with wrong instructor should not be allowed");
    	} catch(FitException ex) {} //ok
 
    } 
    
    @Test
    public void testGetLessonsGiven() throws FitException {
    		f.addLessonGiven("G1", 1, 8, "Stefano");
    		f.addLessonGiven("G1", 2, 8, "Stefano");
    		f.addLessonGiven("G2", 4, 8, "Stefano");
    		f.addLessonGiven("G1", 1, 9, "Marco");
    		assertEquals (2,f.getNumLessonsGiven("G1", "Stefano"));
    		assertEquals (1,f.getNumLessonsGiven("G2", "Stefano"));
    		assertEquals (1,f.getNumLessonsGiven("G1", "Marco"));
    		//assertEquals (0,f.getNumLessonsGiven("G1", "Pippo")); //Non existing instructor
    }
    
    @Test
    public void testGetLessonsGivenWrongGymn() throws FitException {
        f.addLessonGiven("G1", 1, 8, "Stefano");
        f.addLessonGiven("G1", 2, 8, "Stefano");
        f.addLessonGiven("G2", 4, 8, "Stefano");
        f.addLessonGiven("G1", 1, 9, "Marco");
        try {
    		f.getNumLessonsGiven("G4", "Stefano");
    		fail ("GetLessonsGiven given with wrong gymn name should not be allowed");
    	} catch(FitException ex) {} //ok
    }
    
}
