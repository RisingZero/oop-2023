package fit;

import java.util.*;


public class Fit {
    
    public static int MONDAY    = 1;
    public static int TUESDAY   = 2;
    public static int WEDNESDAY = 3;
    public static int THURSDAY  = 4;
    public static int FRIDAY    = 5;
    public static int SATURDAY  = 6;
    public static int SUNDAY    = 7;
    
	public Fit() {
	
	}
	// R1 
	
	public void addGymn (String name) throws FitException {

	}
	
	public int getNumGymns() {
		return -1;
	}
	
	//R2

	public void addLessons (String gymnname, 
	                        String activity, 
	                        int maxattendees, 
	                        String slots, 
	                        String ...allowedinstructors) throws FitException{
	    
	}
	
	//R3
	public int addCustomer(String name) {
		return -1;
	}
	
	public String getCustomer (int customerid) throws FitException{
	    return null;
	}
	
	//R4
	
	public void placeReservation(int customerid, String gymnname, int day, int slot) throws FitException{

	}
	
	public int getNumLessons(int customerid) {
		return -1;
	}
	
	
	//R5
	
	public void addLessonGiven (String gymnname, int day, int slot, String instructor) throws FitException{

	}
	
	public int getNumLessonsGiven (String gymnname, String instructor) throws FitException {
	    return -1;
	}
	//R6
	
	public String mostActiveGymn() {
		return null;
	}
	
	public Map<String, Integer> totalLessonsPerGymn() {		
		return null;
	}
	
	public SortedMap<Integer, List<String>> slotsPerNofParticipants(String gymnname) throws FitException{
	    return null;
	}
	

	
	
	
	


}
