package example;
import static org.junit.Assert.*;
import java.util.*;
import org.junit.Test;

import sports.*;

public class TestApp {
@Test
public void test() throws SportsException {
	Sports sports = new Sports();
	List<String> ls = null;
	//R1

	sports.defineActivities("Trekking","Running","Swimming","Crossfit");
	
	try {
	    sports.defineActivities (); 
	    fail("No activities"); 
	} catch(Exception ex){} //ok
	ls = sports.getActivities();
	assertEquals (4,ls.size());
	
	sports.addCategory("Shorts", "Trekking", "Running", "Crossfit"); 
	sports.addCategory("TShirt", "Trekking", "Running", "Crossfit");
    sports.addCategory("Pants", "Trekking","Running"); 
	sports.addCategory("Swimsuit", "Swimming");
	sports.addCategory("Sweatshirt", "Trekking","Running","Swimming");
	try {
	    sports.addCategory ("Pajamas", "Sleeping"); 
	    fail("Unknown activity not detected");
	} catch(Exception ex){} //ok
	
	assertEquals(5,sports.countCategories());
  
    ls = sports.getCategoriesForActivity("Soccer");
    assertEquals ("Empty list expected", 0,ls.size());
    ls = sports.getCategoriesForActivity("Trekking");
    assertEquals ("[Pants, Shorts, Sweatshirt, TShirt]",ls.toString());
    
    //R2
	sports.addProduct("p1", "Trekking", "Pants");
	sports.addProduct("p2", "Swimming", "Swimsuit");
	sports.addProduct("p3", "Running", "Shorts");
	sports.addProduct("p0", "Trekking", "TShirt");
    try {
    	sports.addProduct("p1", "Running", "Pants");
    	fail("Did not detect duplicated product name");
    } catch(Exception ex){} //ok
    
    ls = sports.getProductsForCategory("Pants");
    assertEquals("[p1]",ls.toString());

    ls = sports.getProductsForActivity("Trekking");
    assertEquals("[p0, p1]",ls.toString());

    ls = sports.getProducts("Trekking","Pants","TShirt");
    assertEquals("[p0, p1]",ls.toString());

 
    //R3
    sports.addRating("p1", "u1", 2, "Not what described");
    sports.addRating("p2", "u2", 3, "Reasonable product");
    sports.addRating("p2", "u1", 5, "Great");
    sports.addRating("p0", "u3", 2, "Really not a good one");
    try {
        sports.addRating("p3", "u3", 7, "comment");
        fail("wrong stars not detected");
    } catch(Exception ex){} //ok

    ls = sports.getRatingsForProduct ("p2");
    assertEquals(2,ls.size());
    assertEquals("[5 : Great, 3 : Reasonable product]",ls.toString());
 
 
    //R4
    double stars = sports.getStarsOfProduct("p2"); 
    assertEquals (4.0,stars,0.1);

    stars = sports.getStarsOfProduct("p1"); 
    assertEquals (2.0,stars,0.1);

    double averageStars = sports.averageStars(); 
    assertEquals (3.0,averageStars,0.1);

    //R5
    SortedMap<String, Double> sm = sports.starsPerActivity();
    //System.out.println(sm);
    assertEquals("{Swimming=4.0, Trekking=2.0}", sm.toString());
    
    SortedMap<Double, List<String>> sml = sports.getProductsPerStars();
    assertEquals("{4.0=[p2], 2.0=[p0, p1]}", sml.toString());
}
}
