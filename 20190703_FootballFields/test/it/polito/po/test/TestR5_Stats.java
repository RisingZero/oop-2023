package it.polito.po.test;

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import it.polito.oop.futsal.Fields;
import it.polito.oop.futsal.FutsalException;
import it.polito.oop.futsal.Fields.Features;

public class TestR5_Stats {
    private static final String CLOSING = "23:30";
    private static final String OPENING = "10:30";
    private Fields fields;
    private int p1;
    private int p2;
    private int p3;
    
    @Before
    public void setUp() throws FutsalException {
        fields = new Fields();

        fields.defineFields( new Features(false,false,false), 
                new Features(true,true,false), 
                new Features(true,false,true), 
                new Features(true,true,true) 
               ); //fields

        fields.setOpeningTime(OPENING);
        fields.setClosingTime(CLOSING);

        p1 = fields.newAssociate("Genny", "Sava", "3334445566");
        p2 = fields.newAssociate("Remo", "Williams", "3337778899");
        p3 = fields.newAssociate("Ugo", "Ughi", "3331112233");
        fields.newAssociate("Ivo", "Uva", "3336665544");
        
        fields.bookField(2, p1, "13:30");
        fields.bookField(3, p2, "21:30");
        fields.bookField(4, p3, "20:30");
        fields.bookField(4, p2, "22:30");
        fields.bookField(2, p1, "14:30");
        fields.bookField(2, p1, "22:30");

    }

    @Test
    public void testServedAssociates() {
        assertEquals(3,fields.countServedAssociates());
    }

    @Test
    public void testTurnover() {
        Map<Integer,Long> tom = fields.fieldTurnover();
        assertNotNull(tom);
        assertEquals(4,tom.size());
        assertEquals(0L,tom.get(1).longValue());
        assertEquals(3L,tom.get(2).longValue());
        assertEquals(1L,tom.get(3).longValue());
        assertEquals(2L,tom.get(4).longValue());
    }
    
    @Test
    public void testOccupation() {
        assertEquals(0.115,fields.occupation(),0.001);
    }
    


}
