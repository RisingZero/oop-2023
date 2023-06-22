package it.polito.po.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import it.polito.oop.futsal.Fields;
import it.polito.oop.futsal.FutsalException;

public class TestR2_Customers {
    Fields fields;
    
    @Before
    public void setUp() {
        fields = new Fields();
    }

    @Test
    public void testAssociateGetters() throws FutsalException {
        int p1 = fields.newAssociate("Genny", "Sava", "3334445566");
        
        assertEquals("Genny",fields.getFirst(p1));
        assertEquals("Sava",fields.getLast(p1));
        assertEquals("3334445566",fields.getPhone(p1));
    }
    
    @Test
    public void testUniqueCode() {
        int p1 = fields.newAssociate("Genny", "Sava", "3334445566");
        int p2 = fields.newAssociate("Remo", "Williams", "3337778899");

        assertTrue(p1!=p2);
    }
    
    @Test
    public void testAssociateCount() {
        fields.newAssociate("Genny", "Sava", "3334445566");
        fields.newAssociate("Remo", "Williams", "3337778899");
        fields.newAssociate("Ugo", "Ughi", "3331112233");
        
        assertEquals(3,fields.countAssociates());
    }

    @Test(expected=FutsalException.class)
    public void testAssociateGettersExc1() throws FutsalException {
        int p1 = fields.newAssociate("Genny", "Sava", "3334445566");
        
        fields.getFirst(p1+99);
    }

    @Test(expected=FutsalException.class)
    public void testAssociateGettersExc2() throws FutsalException {
        int p1 = fields.newAssociate("Genny", "Sava", "3334445566");
        
        fields.getLast(p1-99);
    }

    @Test(expected=FutsalException.class)
    public void testAssociateGettersExc3() throws FutsalException {
        int p1 = fields.newAssociate("Genny", "Sava", "3334445566");
        
        fields.getPhone(p1-1);
    }

}
