package it.polito.po.test;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import fit.Fit;
import fit.FitException;


public class TestR3 {
    private Fit f;

    @Before
    public void setUp() {
    	this.f = new Fit();
    }
    
    
    @Test
    public void testAddCustomer() {
    	int customerid;
    	customerid = f.addCustomer("Stefano");
    	assertEquals (1,customerid);
    	customerid = f.addCustomer("Marco");
    	assertEquals (2,customerid);
    	customerid = f.addCustomer("Giovanni");
    	assertEquals (3,customerid);
    	customerid = f.addCustomer("Giorgio");
    	assertEquals (4,customerid);
    }
    
    @Test
    public void testgetCustomer() throws FitException {
    	f.addCustomer("Stefano");
    	f.addCustomer("Marco");
    	f.addCustomer("Giovanni");
    	f.addCustomer("Giorgio");
		assertEquals (f.getCustomer(1),"Stefano");
		assertEquals (f.getCustomer(2),"Marco");
		assertEquals (f.getCustomer(3),"Giovanni");
		assertEquals (f.getCustomer(4),"Giorgio");
    }
    
    @Test
    public void testgetMissingCustomer() {
    	f.addCustomer("Stefano");
    	f.addCustomer("Marco");
    	f.addCustomer("Giovanni");
    	f.addCustomer("Giorgio");
    	try {
    		assertEquals (f.getCustomer(10),"Francesco");
    		fail("undefined customer id should generate an exceptions");
        } catch(FitException ex){} //ok
    }
    
}
