package it.polito.po.test;
import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import sports.*;

public class TestR3 {
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
     }

    @Test
    public void testAddRating() throws SportsException {
        sports.addRating("p1", "u1", 2, "Not what described");
        sports.addRating("p2", "u2", 3, "Reasonable product");
        sports.addRating("p2", "u1", 5, "Great");
        sports.addRating("p0", "u3", 2, "Really not a good one");

        List<String> ratings = sports.getRatingsForProduct ("p2");
        assertNotNull(ratings);
        assertEquals(2,ratings.size());
    }

    @Test
    public void testAddRatingSorted() throws SportsException {
        sports.addRating("p1", "u1", 2, "Not what described");
        sports.addRating("p2", "u2", 3, "Reasonable product");
        sports.addRating("p2", "u1", 5, "Great");
        sports.addRating("p0", "u3", 2, "Really not a good one");

        List<String> ratings = sports.getRatingsForProduct ("p2");

        assertEquals("[5 : Great, 3 : Reasonable product]",ratings.toString());
    }

    @Test(expected=SportsException.class)
    public void testAddRatingWrong() throws SportsException {
         sports.addRating("p3", "u3", 7, "comment");
    }
 
    @Test
    public void testGetRatingNone() throws SportsException {
        sports.addRating("p1", "u1", 2, "Not what described");
        sports.addRating("p2", "u2", 3, "Reasonable product");
        sports.addRating("p2", "u1", 5, "Great");
        sports.addRating("p0", "u3", 2, "Really not a good one");

        List<String> ratings = sports.getRatingsForProduct ("p3");

        assertEquals("[]",ratings.toString());
    }
}
