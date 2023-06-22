package it.polito.po.test;

import static org.junit.Assert.*;

import java.time.LocalTime;

import org.junit.Before;
import org.junit.Test;

import it.polito.oop.futsal.Fields;
import it.polito.oop.futsal.Fields.Features;
import it.polito.oop.futsal.FutsalException;

public class TestR3_Booking {
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

    }

    @Test
    public void testBooking() throws FutsalException {
        fields.bookField(1, p1, "12:30");
        assertTrue(fields.isBooked(1, "12:30"));
    }


    @Test
    public void testBooking2() throws FutsalException {
        fields.bookField(1, p2, OPENING);
        assertTrue(fields.isBooked(1, OPENING));
    }

    @Test
    public void testBooking3() throws FutsalException {
        String time = LocalTime.parse(CLOSING).minusMinutes(60).toString();
        fields.bookField(1, p3, time);
        assertTrue(fields.isBooked(1, time));
    }

    @Test(expected=FutsalException.class)
    public void testBookingFail() throws FutsalException {
        fields.bookField(1, p1, "14:45");
    }

    @Test(expected=FutsalException.class)
    public void testBookingFail2() throws FutsalException {
        fields.bookField(99, p1, "14:30");
    }

    @Test(expected=FutsalException.class)
    public void testBookingFail3() throws FutsalException {
        fields.bookField(1, p3+99, "14:30");
    }


}
