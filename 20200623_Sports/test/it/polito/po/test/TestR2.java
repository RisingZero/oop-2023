package it.polito.po.test;
import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import sports.*;

public class TestR2 {
    Sports sports;
    List<String> ls = null;

    @Before
    public void setUp() throws SportsException {
        sports = new Sports();
        sports.defineActivities("Trekking","Running","Swimming","Crossfit");

        sports.addCategory("Shorts", "Trekking", "Running", "Crossfit"); 
        sports.addCategory("TShirt", "Trekking", "Running", "Crossfit");
        sports.addCategory("Pants", "Trekking","Running"); 
        sports.addCategory("Swimsuit", "Swimming");
        sports.addCategory("Sweatshirt", "Trekking","Running","Swimming");
     }

    @Test
    public void testAddProduct() throws SportsException {
        sports.addProduct("p1", "Trekking", "Pants");
        sports.addProduct("p2", "Swimming", "Swimsuit");
        sports.addProduct("p3", "Running", "Shorts");
        sports.addProduct("p0", "Trekking", "TShirt");
        
        ls = sports.getProductsForCategory("Pants");
        assertEquals("[p1]",ls.toString());
    }

    @Test(expected=SportsException.class)
    public void testDuplicateProductName() throws SportsException{
        sports.addProduct("p1", "Trekking", "Pants");
        sports.addProduct("p2", "Swimming", "Swimsuit");
        sports.addProduct("p3", "Running", "Shorts");
        sports.addProduct("p0", "Trekking", "TShirt");
        
        sports.addProduct("p1", "Running", "Pants");
    }


    @Test
    public void testGetProductPerCategoryNone() throws SportsException {
        sports.addProduct("p1", "Trekking", "Pants");
        sports.addProduct("p2", "Swimming", "Swimsuit");
        sports.addProduct("p3", "Running", "Shorts");
        sports.addProduct("p0", "Trekking", "TShirt");
        
        ls = sports.getProductsForCategory("Socks");
        assertEquals("[]",ls.toString());

    }

    @Test
    public void testgetProductsPerActivity() throws SportsException {
        sports.addProduct("p1", "Trekking", "Pants");
        sports.addProduct("p2", "Swimming", "Swimsuit");
        sports.addProduct("p3", "Running", "Shorts");
        sports.addProduct("p0", "Trekking", "TShirt");
        
        ls = sports.getProductsForActivity("Trekking");
        assertEquals("[p0, p1]",ls.toString());

    }

    @Test
    public void testgetProductsPerActivityNone() throws SportsException {
        sports.addProduct("p1", "Trekking", "Pants");
        sports.addProduct("p2", "Swimming", "Swimsuit");
        sports.addProduct("p3", "Running", "Shorts");
        sports.addProduct("p0", "Trekking", "TShirt");
        
        ls = sports.getProductsForActivity("Soccer");
        assertEquals("[]",ls.toString());

    }

    @Test
    public void testGetProducts() throws SportsException {
        sports.addProduct("p1", "Trekking", "Pants");
        sports.addProduct("p2", "Swimming", "Swimsuit");
        sports.addProduct("p3", "Running", "Shorts");
        sports.addProduct("p0", "Trekking", "TShirt");
        
        ls = sports.getProducts("Trekking","Pants","TShirt");
        assertEquals("[p0, p1]",ls.toString());
    }

    @Test
    public void testGetProductsNone() throws SportsException {
        sports.addProduct("p1", "Trekking", "Pants");
        sports.addProduct("p2", "Swimming", "Swimsuit");
        sports.addProduct("p3", "Running", "Shorts");
        sports.addProduct("p0", "Trekking", "TShirt");
        
        ls = sports.getProducts("Soccer","Pants","TShirt");
        assertEquals("[]",ls.toString());
    }

}
