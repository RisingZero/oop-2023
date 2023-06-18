package it.polito.po.test;

import discounts.*;

import static org.junit.Assert.*;

import java.util.*;

import org.junit.Before;
import org.junit.Test;


public class TestR5 {

	Discounts d;
	int cId1 = 0; 
	int cId2 = 0; 
	int cId3 = 0;
	
    @Before
    public void setUp() throws DiscountsException {
    	d = new Discounts();
        cId1 = d.issueCard("John Smith"); 
        cId2 = d.issueCard("Jane Air"); 
    	d.addProduct("cat3", "pt5", 30.4); 
    	d.addProduct("cat3", "pt3", 10); 
    	d.addProduct("cat2", "pt4", 20); 
    	d.addProduct("cat2", "pt2", 40); 
    	d.addProduct("cat7", "pt7", 40); 
    	d.addProduct("cat3", "pt6", 50); 
    	d.setDiscount("cat2", 10);
    	d.setDiscount("cat7", 40);
    	d.addPurchase(cId1, "pt5:1","pt3:2","pt4:2","pt2:1");
    	d.addPurchase(cId2, "pt5:1","pt3:2","pt4:2");
    	d.addPurchase("pt5:1","pt3:2");
    	d.addPurchase("pt6:3");
    }

    @Test
    public void productIdsPerNofUnits1() {
    	SortedMap<Integer, List<String>> map1 = d.productIdsPerNofUnits();
        assertNotNull("Missing products ids per number of units", map1);

    	assertEquals("Wrong map size", 4, map1.size());
    }

    @Test
    public void productIdsPerNofUnits2() {
    	SortedMap<Integer, List<String>> map1 = d.productIdsPerNofUnits();
    	assertNotNull("Missing products ids per number of units", map1);

    	assertEquals("Wrong number of units", "[1, 3, 4, 6]",map1.keySet().toString());
    	assertEquals("Wrong product lists", map1.toString(), 
    			"{1=[pt2], 3=[pt5, pt6], 4=[pt4], 6=[pt3]}");
    }

    
    @Test
    public void totalPurchasePerCard1() {
    	SortedMap<Integer, Integer> map2 = d.totalPurchasePerCard();
        assertNotNull("Missing total purchase per card", map2);
    	assertEquals("Wrong map size", 2, map2.size());
    }
    
    @Test
    public void totalPurchasePerCard2() {
    	SortedMap<Integer, Integer> map2 = d.totalPurchasePerCard();
        assertNotNull("Missing total purchase per card", map2);
        assertEquals("Wrong cards in map", "[1, 2]",map2.keySet().toString());

    	assertEquals("Wrong purchases", map2.toString(), "{1=122, 2=86}");
    }
	
    @Test
    public void totalPurchaseWithoutCard() {
    	int totalPurchasesWithoutCard = d.totalPurchaseWithoutCard();

    	assertEquals("Wrong total purchase without card", 200, totalPurchasesWithoutCard);
    }
    
    @Test
    public void totalDiscountPerCard1() {
    	SortedMap<Integer, Integer> map3 = d.totalDiscountPerCard();
        assertNotNull("Missing total discount per card", map3);
        
    	assertEquals("Wrong map size", 2, map3.size());
    }
    
    @Test
    public void totalDiscountPerCard2() {
    	SortedMap<Integer, Integer> map3 = d.totalDiscountPerCard();
        assertNotNull("Missing total discount per card", map3);
        assertEquals("Wrong cards in map", "[1, 2]",map3.keySet().toString());

    	assertEquals("Wrong map contents", map3.toString(), "{1=8, 2=4}");
    }
    
}
