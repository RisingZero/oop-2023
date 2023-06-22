package it.polito.po.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ TestR1_Fields.class, TestR2_Customers.class, TestR4_FieldOccupation.class, TestR3_Booking.class,
        TestR5_Stats.class })
public class AllTests {

}
