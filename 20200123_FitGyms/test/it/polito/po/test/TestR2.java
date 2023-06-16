package it.polito.po.test;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import fit.Fit;
import fit.FitException;


public class TestR2 {
	
    private Fit f;

    @Before
    public void setUp() throws FitException {
    	this.f = new Fit();
		f.addGymn("G1");
    	f.addGymn("G2");
    	f.addGymn("G3");
   }
    
    @Test
    public void testaddLesson() throws FitException {
		f.addLessons("G1", "Crossfit", 10, "1.8,1.9,2.10,6.9", "Stefano");
		f.addLessons("G2", "Aerobica", 10, "1.8,1.9,2.10,6.9", "Marco,Giorvanni");
		f.addLessons("G3", "Calisthenics", 10, "1.8,1.9,2.10,6.9", "Stefano");
    }
    
    
    @Test
    public void testaddLessonWrongGymn() {
        try {
    		f.addLessons("G4", "Crossfit", 10, "6.9", "Stefano");
            fail("Not existing gym name should not be allowed");
        } catch(FitException ex){} //ok
    }
    
    @Test
    public void testaddLessonWrongDay() {
        try {
    		f.addLessons("G4", "Crossfit", 8, "21.9", "Stefano");
            fail("Wrong day of the week should not be allowed");
        } catch(FitException ex){} //ok
        try {
    		f.addLessons("G4", "Crossfit", 0, "21.9", "Stefano");
            fail("Wrong day of the week should not be allowed");
        } catch(FitException ex){} //ok
        
    }
    
    @Test
    public void testaddLessonWrongSlot() {
        try {
    		f.addLessons("G4", "Crossfit", 8, "3.7", "Stefano");
            fail("Wrong day of the week should not be allowed");
        } catch(FitException ex){} //ok
        try {
    		f.addLessons("G4", "Crossfit", 0, "3.22", "Stefano");
            fail("Wrong day of the week should not be allowed");
        } catch(FitException ex){} //ok
        
    }
    
    @Test
    public void testaddLessonBusySlots() {
        try {
    		f.addLessons("G4", "Crossfit", 8, "3.7,4.1,3.2", "Stefano");
            fail("Wrong day of the week should not be allowed");
        } catch(FitException ex){} //ok
        try {
    		f.addLessons("G4", "Crossfit", 0, "3.7", "Stefano");
            fail("Wrong day of the week should not be allowed");
        } catch(FitException ex){} //ok
        
    }
    
}
