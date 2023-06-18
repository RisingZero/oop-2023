package it.polito.po.test;
import discounts.*;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class TestR1R2 {
	Discounts d;
	
    @Before
    public void setUp() {
    	d = new Discounts();
    }
    
    @Test
    public void testCards1() {
        int cId1 = d.issueCard("Mario Rossi"); 
        int cId2 = d.issueCard("Giuseppe Verdi"); 
        int cId3 = d.issueCard("Filippo Neri"); 
    	assertEquals(cId1, 1); 
    	assertEquals(cId2, 2); 
    	assertEquals(cId3, 3);
    }

    @Test
    public void testCardsNames() {
        int cId1 = d.issueCard("Salvo Mattei");
        String name = d.cardHolder(cId1);
        assertEquals("Wrong cardholder name", "Salvo Mattei", name);
    }

    @Test
    public void testCards2() {
    	for (int i = 1; i <= 5; i++) {
    	    d.issueCard("Renzo Mattei"+i);
    	}
    	assertEquals("Wrong number of issued cards", 5, d.nOfCards());
    }
    
    
    @Test
    public void addProduct1() throws DiscountsException  {
    	double price = 0;
    	d.addProduct("cat1", "pt5", 30.4); 
    	price = d.getPrice("pt5");
     	assertEquals(30.4, price, 0.001);
    }
    
    @Test(expected=DiscountsException.class)
    public void addProduct2() throws DiscountsException {
    	d.addProduct("cat1", "pt5", 30.4); 
    	d.addProduct("cat1", "pt6", 20.4); 
    	d.addProduct("cat1", "pt5", 30.4); 
     }

    @Test(expected=DiscountsException.class)
    public void addProduct3() throws DiscountsException {
    	d.addProduct("cat1", "pt5", 30.4); 
    	d.addProduct("cat1", "pt6", 20.4); 
		d.getPrice("pt100");
     }
    
    @Test
    public void averagePrice1() throws DiscountsException {
    	int average = 0;
    	d.addProduct("cat1", "pt5", 30.4); 
    	d.addProduct("cat1", "pt6", 30.4); 
		average = d.getAveragePrice("cat1");
		assertEquals("Wrong average prices for category 'cat1'", 30, average);
    }
    
    @Test(expected=DiscountsException.class)
    public void averagePriceUndefinedCategory() throws DiscountsException {
    	d.addProduct("cat1", "pt5", 30.4); 
    	d.addProduct("cat1", "pt6", 30.4); 
		d.getAveragePrice("cat2");
		fail("Expected exception"); //cat2 undef
    }
    
}
