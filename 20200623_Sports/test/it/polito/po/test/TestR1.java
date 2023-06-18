package it.polito.po.test;
import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import sports.*;

public class TestR1 {
    Sports sports;

    @Before
    public void setUp() {
        sports = new Sports();
    }

    @Test
    public void testActivities() throws SportsException {
        sports.defineActivities("Trekking","Running","Swimming","Crossfit");
        List<String> activities = sports.getActivities();
        assertNotNull("Missing activities", activities);
        assertEquals (4,activities.size());
    }

    @Test
    public void testActivitiesSorted() throws SportsException {
        sports.defineActivities("Trekking","Running","Swimming","Crossfit");
        List<String> activities = sports.getActivities();
        assertNotNull("Missing activities", activities);
        assertEquals ("[Crossfit, Running, Swimming, Trekking]",activities.toString());
    }

    @Test(expected=SportsException.class)
    public void testDefineNoActivities() throws SportsException{
        sports.defineActivities ();
    }
    @Test
    public void testAddCategory() throws SportsException{
        sports.defineActivities("Trekking","Running","Swimming","Crossfit");

        sports.addCategory("Shorts", "Trekking", "Running", "Crossfit"); 
        sports.addCategory("TShirt", "Trekking", "Running", "Crossfit");
        sports.addCategory("Pants", "Trekking","Running"); 
        sports.addCategory("Swimsuit", "Swimming");
        sports.addCategory("Sweatshirt", "Trekking","Running","Swimming");
        
        assertEquals(5,sports.countCategories());
    }
    
    @Test(expected=SportsException.class)
    public void testAddCategoryUnknownActivity() throws SportsException {
        sports.defineActivities("Trekking","Running","Swimming","Crossfit");

        sports.addCategory ("Pajamas", "Sleeping"); 
    }
    
    @Test
    public void testCategorieForActivity() throws SportsException {
        sports.defineActivities("Trekking","Running","Swimming","Crossfit");

        sports.addCategory("Shorts", "Trekking", "Running", "Crossfit"); 
        sports.addCategory("TShirt", "Trekking", "Running", "Crossfit");
        sports.addCategory("Pants", "Trekking","Running"); 
        sports.addCategory("Swimsuit", "Swimming");
        sports.addCategory("Sweatshirt", "Trekking","Running","Swimming");
 
        List<String> ls = sports.getCategoriesForActivity("Trekking");
        assertNotNull("Missing activities", ls);
        assertEquals (ls.toString(), "[Pants, Shorts, Sweatshirt, TShirt]");
    }
    
    @Test
    public void testCategorieForActivityEmpty() throws SportsException {
        sports.defineActivities("Trekking","Running","Swimming","Crossfit");

        sports.addCategory("Shorts", "Trekking", "Running", "Crossfit"); 
        sports.addCategory("TShirt", "Trekking", "Running", "Crossfit");
        sports.addCategory("Pants", "Trekking","Running"); 
        sports.addCategory("Swimsuit", "Swimming");
        sports.addCategory("Sweatshirt", "Trekking","Running","Swimming");
 
        List<String> ls = sports.getCategoriesForActivity("Soccer");
        assertNotNull("Missing activities", ls);
        assertEquals ("Empty list expected", 0,ls.size());
    }
}
