package it.polito.po.test;
import static org.junit.Assert.*;

import java.util.List;
import java.util.SortedMap;

import org.junit.Before;
import org.junit.Test;

import sports.*;

public class TestR4R5 {
    Sports sports;

    @Before
    public void setUp() throws SportsException {
        sports = new Sports();
        sports.defineActivities("Trekking","Running","Swimming","Crossfit");

        sports.addCategory("Shorts", "Trekking", "Running", "Crossfit"); 
        sports.addCategory("TShirt", "Trekking", "Running", "Crossfit");
        sports.addCategory("Pants", "Trekking","Running"); 
        sports.addCategory("Swimsuit", "Swimming");
        sports.addCategory("Sweatshirt", "Trekking","Running","Swimming");
        
        sports.addProduct("p1", "Trekking", "Pants");
        sports.addProduct("p2", "Swimming", "Swimsuit");
        sports.addProduct("p3", "Running", "Shorts");
        sports.addProduct("p0", "Trekking", "TShirt"); 
        
        sports.addRating("p1", "u1", 2, "Not what described");
        sports.addRating("p2", "u2", 3, "Reasonable product");
        sports.addRating("p2", "u1", 5, "Great");
        sports.addRating("p0", "u3", 2, "Really not a good one");
     }
    
    @Test
    public void testAverageStars() {
        double averageStars = sports.averageStars(); 
        assertEquals (3.0,averageStars,0.1);
    }
    
    @Test
    public void testGetStarsOfProduct() {
        double stars = sports.getStarsOfProduct("p2"); 
        assertEquals (4.0,stars,0.1);

        stars = sports.getStarsOfProduct("p1"); 
        assertEquals (2.0,stars,0.1);
    }
    
    @Test
    public void testStarsPerActivity() {
        SortedMap<String, Double> sm = sports.starsPerActivity();
        //System.out.println(sm);
        assertEquals("{Swimming=4.0, Trekking=2.0}", sm.toString());
    }
    
    @Test
    public void testProductsPerStars() {
        SortedMap<Double, List<String>> sml = sports.getProductsPerStars();
        assertEquals("{4.0=[p2], 2.0=[p0, p1]}", sml.toString());
    }

}
