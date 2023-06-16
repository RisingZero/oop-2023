package it.polito.po.test;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import fit.Fit;
import fit.FitException;


public class TestR4 {
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
    public void testPlaceReservationWrongCustomerId() {
    	try {
    		f.placeReservation(10, "G1", 1, 9);
    		fail ("Reservation with non existant customer should not be allowed");
    	} catch(FitException ex) {} //ok
    }
    
    @Test
    public void testPlaceReservationWrongGymn() {
    	try {
    		f.placeReservation(1, "G5", 1, 9);
    		fail ("Reservation with wrong gymn name should not be allowed");
    	} catch(FitException ex) {} //ok
    }
    
    @Test
    public void testPlaceReservationWrongDaySlot() {
    	try {
    		f.placeReservation(1, "G1", 1, 10);
    		fail ("Reservation with wrong slot should not be allowed");
    	} catch(FitException ex) {} //ok
    	try {
    		f.placeReservation(1, "G1", 3, 8);
    		fail ("Reservation with wrong day should not be allowed");
    	} catch(FitException ex) {} //ok   	
    	try {
    		f.placeReservation(1, "G1", 2, 3);
    		fail ("Reservation with wrong day and slot should not be allowed");
    	} catch(FitException ex) {} //ok   
    } 
    
    @Test
    public void testPlaceReservationMaxAttendees() {
    	try {
    		f.placeReservation(1, "G2", 4, 8);
    		f.placeReservation(2, "G2", 4, 8);
    		f.placeReservation(3, "G2", 4, 8);
    		f.placeReservation(4, "G2", 4, 8);
    		f.placeReservation(5, "G2", 4, 8);
    		f.placeReservation(6, "G2", 4, 8);    		
    		fail ("Reservation in full classes should not be allowed");
    	} catch(FitException ex) {} //ok 
    } 
    
    @Test
   public void testPlaceReservationDoubleRegistration() {
    	try {
    		f.placeReservation(1, "G2", 4, 8);
    		f.placeReservation(1, "G2", 4, 8);   		
    		fail ("Double reservation in  classes should not be allowed");
    	} catch(FitException ex) {} //ok 
    } 
    
    
    @Test
    public void testGetNumLessons() throws FitException {
		f.placeReservation(1, "G1", Fit.MONDAY, 8);
		f.placeReservation(1, "G1", Fit.TUESDAY, 8);
		f.placeReservation(1, "G2", Fit.THURSDAY, 8);  
		f.placeReservation(2, "G1", Fit.MONDAY, 8);
		f.placeReservation(2, "G2", Fit.THURSDAY, 8);	
		assertEquals (3,f.getNumLessons(1));
		assertEquals (2,f.getNumLessons(2));
    }
    
    
    
    
    
    
    
}
