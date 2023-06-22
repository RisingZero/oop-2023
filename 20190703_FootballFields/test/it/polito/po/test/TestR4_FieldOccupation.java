package it.polito.po.test;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import it.polito.oop.futsal.FieldOption;
import it.polito.oop.futsal.Fields;
import it.polito.oop.futsal.FutsalException;
import it.polito.oop.futsal.Fields.Features;

public class TestR4_FieldOccupation {
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
        
        fields.bookField(2, p1, "13:30");
        fields.bookField(3, p2, "21:30");
        fields.bookField(4, p3, "20:30");
        fields.bookField(4, p2, "22:30");
        fields.bookField(2, p1, "14:30");
        fields.bookField(2, p1, "22:30");

    }


    @Test
    public void testOccupation() {
        assertEquals(0,fields.getOccupation(1));
        assertEquals(3,fields.getOccupation(2));
        assertEquals(1,fields.getOccupation(3));
        assertEquals(2,fields.getOccupation(4));
    }
    
    @Test
    public void testOptions1() {
        List<FieldOption> options = fields.findOptions("17:30",new Features(false,false,false));
        
        assertNotNull(options);
        assertEquals(4,options.size());
        FieldOption first= options.get(0);
        assertEquals(1,first.getField());
        assertEquals(0,first.getOccupation());
        FieldOption last= options.get(3);
        assertEquals(2,last.getField());
        assertEquals(3,last.getOccupation());
    }
    
    @Test
    public void testOptions2() {
        List<FieldOption> options = fields.findOptions("17:30",new Features(true,false,false));
        
        assertNotNull(options);
        assertEquals(3,options.size());
    }

    @Test
    public void testOptions3() {
        List<FieldOption> options = fields.findOptions("17:30",new Features(true,true,false));
        
        assertNotNull(options);
        assertEquals(2,options.size());
    }

    @Test
    public void testOptions4() {
        List<FieldOption> options = fields.findOptions("17:30",new Features(true,false,true));
        
        assertNotNull(options);
        assertEquals(2,options.size());
    }
    
    @Test
    public void testOptions5() {
        List<FieldOption> options = fields.findOptions("17:30",new Features(true,true,true));
        
        assertNotNull(options);
        assertEquals(1,options.size());
        
        assertEquals(4,options.get(0).getField());
    }

}
