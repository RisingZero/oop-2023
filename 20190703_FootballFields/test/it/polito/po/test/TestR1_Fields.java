package it.polito.po.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import it.polito.oop.futsal.Fields;
import it.polito.oop.futsal.FutsalException;
import it.polito.oop.futsal.Fields.Features;

public class TestR1_Fields {
    Fields company;
    
    @Before
    public void setUp() {
        company = new Fields();
    }

    @Test
    public void testFields() throws FutsalException {
        company.defineFields(new Features(false,false,false), 
                new Features(true,true,false), 
                new Features(true,false,true), 
                new Features(true,true,true) 
               );
        
        assertEquals(4,company.countFields());
        assertEquals(3,company.countIndoor());
    }
    
    @Test(expected=FutsalException.class)
    public void testFieldsWrong() throws FutsalException {
        company.defineFields(new Features(false,false,false), 
                new Features(false,true,false));
    }

    @Test(expected=FutsalException.class)
    public void testFieldsWrong2() throws FutsalException {
        company.defineFields(new Features(false,false,false), 
                new Features(false,false,true));
    }

    
    @Test
    public void testOpening() {
        company.setOpeningTime("19:30");
        assertEquals("19:30",company.getOpeningTime());
    }

    @Test
    public void testClosing() {
        company.setClosingTime("23:30");
        assertEquals("23:30",company.getClosingTime());
    }

    

}
