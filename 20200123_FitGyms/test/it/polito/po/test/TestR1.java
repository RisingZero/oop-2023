package it.polito.po.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import fit.Fit;
import fit.FitException;


public class TestR1 {
	
    private Fit f;

	
    @Before
    public void setUp() {
    	this.f = new Fit();
    }
    
    @Test
    public void testNumGymn() throws FitException {
    		assertEquals (0,f.getNumGymns());
        	f.addGymn("G1");
        	assertEquals (1,f.getNumGymns());
        	f.addGymn("G2");
        	assertEquals (2,f.getNumGymns());
    }
    
    @Test
    public void addGymnNoDuplicates() throws FitException {
    		f.addGymn("G1");
    		f.addGymn("G2");
    		f.addGymn("G3");
    		assertTrue(f.getNumGymns() > 0);
    }
    
    @Test
    public void addGymnDuplicates() {
        try {
    		f.addGymn("G1");
    		f.addGymn("G2");
    		f.addGymn("G1");
            fail("Duplicate gym name should not be allowed");
        } catch(FitException ex){} //ok
    }
    

}
