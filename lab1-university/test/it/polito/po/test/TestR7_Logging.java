package it.polito.po.test;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import university.University;

import static it.polito.po.test.TestR5_Exams.*;

public class TestR7_Logging {

	private LogSpy logSpy; 
	static final String universityName = "Politecnico di Torino";
	private University poli;
	
	@Before
	public void setUp() {
		poli = new University(universityName);
		poli.setRector("Guido", "Saracco");
		
		logSpy = new LogSpy("University");
	}
	
	@After
	public void tearDown() {
		logSpy.disable();
	}
	
	@Test
	public void testLogging() {		
		poli.enroll("Mario","Rossi");	
		assertEquals("No log record for enroll",1,logSpy.countRecords());
		String s = logSpy.lastRecord().getMessage();
		assertContained("Wrong log message","Rossi",s);
		
		poli.activate("Object Oriented Programming", "James Gosling");
		assertEquals("No log record for activate",2,logSpy.countRecords());
		s = logSpy.lastRecord().getMessage();
		assertContained("Wrong log message","Object Oriented",s);
		
		poli.register(10000,10);
		assertEquals("No log record for register",3,logSpy.countRecords());
		s = logSpy.lastRecord().getMessage();
		assertContained("Wrong log message","10000",s);
	}

	private static class LogSpy {
		private LogRecord[] records = new LogRecord[20];
		private int nextRecord=0;
		Logger ul;
		LogRecord lastRecord() {
			return records[nextRecord-1];
		}
		public int countRecords() {
			return nextRecord;
		}
		LogSpy(String logName){
			ul = Logger.getLogger(logName);
			ul.setLevel(Level.OFF);
			ul.setFilter( r -> {
				if(nextRecord==records.length) {
					records = Arrays.copyOf(records, nextRecord*2);
				}
				records[nextRecord++] = r; // keep track of the attempted log
				return false;			   // but prevent publishing
			});
			ul.setLevel(Level.INFO);
		}
		public void disable() {
			ul.setFilter(null);
		}		
	}


}
