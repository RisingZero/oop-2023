package it.polito.po.test;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Map;
import java.util.SortedMap;

import org.junit.Before;
import org.junit.Test;

import fit.Fit;
import fit.FitException;


public class TestR6 {
    private Fit f;

    @Before
    public void setUp() throws FitException {
    	this.f = new Fit();
    		f.addGymn("G1");
    		f.addGymn("G2");
    		f.addGymn("G3");
    		f.addGymn("G4");
    		
    		f.addLessons("G1", "Aerobica", 10, "1.8,1.9,2.8","Stefano", "Marco");
    		f.addLessons("G2", "Step", 5, "4.8,5.10","Giorgio","Stefano");
    		f.addLessons("G3", "Calisthenics", 10,"6.20","Giovanni","Luca");
    		f.addCustomer("C1"); //customer id 1
    		f.addCustomer("C2"); //customer id 2
    		f.addCustomer("C3"); //customer id 3
    		f.addCustomer("C4"); //customer id 4
    		f.addCustomer("C5"); //customer id 5
    		f.addCustomer("C6"); //customer id 6
    		
    		//R4
    		f.placeReservation(1, "G1", 1, 9);
    		f.placeReservation(1, "G2", 4, 8);
    		f.placeReservation(2, "G2", 4, 8);
    		f.placeReservation(3, "G1", 1, 9);
    		f.placeReservation(3, "G1", 2, 8);
    		f.placeReservation(3, "G3", 6, 20);
    }
    
    
    @Test
    public void testMostActiveGymn() {
    		String g = f.mostActiveGymn();
    		assertEquals("G1", g);
    	
    }
    
    @Test
    public void testtotalLessonsPerGymn() {   	
    		Map<String, Integer> m = f.totalLessonsPerGymn();
    		assertEquals(3, m.get("G1").intValue());
    		assertEquals(2, m.get("G2").intValue());
    		assertEquals(1, m.get("G3").intValue());
    		assertEquals(0, m.get("G4").intValue());	
    }
    
    @Test
    public void testslotsPerNofParticipants() throws FitException {
    		SortedMap<Integer, List<String>> m1 = f.slotsPerNofParticipants("G1");
    		List<String> ll =m1.get(0);
    		assertEquals("1.8", ll.get(0));
    		ll =m1.get(1);
    		assertEquals("2.8", ll.get(0));
    		ll =m1.get(2);
    		assertEquals("1.9", ll.get(0));
    }

    
}
