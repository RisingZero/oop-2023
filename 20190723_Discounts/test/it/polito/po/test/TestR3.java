package it.polito.po.test;

import discounts.*;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;


public class TestR3 {
	Discounts d;
	
    @Before
    public void setUp() {
    	d = new Discounts();
    }
    
    
    @Test
    public void setDiscount1() throws DiscountsException {
        
    	d.addProduct("cat1", "pt5", 30.4);
    	assertEquals("Wrong default discount", 0, d.getDiscount("cat1"));
    	d.setDiscount("cat1", 10);
        assertEquals("Wrong discount", 10, d.getDiscount("cat1"));
    }

    
    @Test(expected=DiscountsException.class)
    public void setDiscount2() throws DiscountsException {
    	d.addProduct("cat1", "pt5", 30.4); 
    	d.setDiscount("cat3", 10);
    }
    
    @Test(expected=DiscountsException.class)
    public void setDiscount3() throws DiscountsException {
    	d.addProduct("cat1", "pt5", 30.4); 
    	d.setDiscount("cat1", 70);
    }
    
    @Test
    public void getDiscount() throws DiscountsException {
    	d.addProduct("cat1", "pt5", 30.4); 
    	d.setDiscount("cat1", 30);
        int discount = d.getDiscount("cat1");
        assertEquals("Wrong discount value", 30, discount);
    }
}
