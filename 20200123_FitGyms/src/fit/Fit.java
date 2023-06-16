package fit;

import java.util.*;
import static java.util.stream.Collectors.*;


public class Fit {
    
    public static int MONDAY    = 1;
    public static int TUESDAY   = 2;
    public static int WEDNESDAY = 3;
    public static int THURSDAY  = 4;
    public static int FRIDAY    = 5;
    public static int SATURDAY  = 6;
    public static int SUNDAY    = 7;
    
    private Map<String,Gym> gyms = new HashMap<>();
    
    private List<String> customers = new ArrayList<>();
    private int nextCustomerId = 1;
    
	public Fit() {
	
	}
	// R1 
	
	public void addGymn (String name) throws FitException {
		if (gyms.containsKey(name))
			throw new FitException();
		
		Gym g = new Gym(name);
		gyms.put(name, g);	
	}
	
	public int getNumGymns() {
		return gyms.size();
	}
	
	//R2

	public void addLessons (String gymname, 
	                        String activity, 
	                        int maxattendees, 
	                        String slots, 
	                        String ...allowedinstructors) throws FitException{
	    if (!gyms.containsKey(gymname))
	    	throw new FitException();
	    
	    Gym g = gyms.get(gymname);
	    
	    class DaySlot {
	    	public int day;
	    	public int slot;
	    	
	    	public DaySlot(int d, int s) {
	    		day = d;
	    		slot = s;
	    	}
	    }
	    List<DaySlot> validSlots = new ArrayList<>();
	    
	    String[] dayslots = slots.split(",");
	    for (String ds: dayslots) {
	    	Scanner sc = new Scanner(ds);
	    	sc.useDelimiter("\\.");
	    	int day = sc.nextInt();
	    	int slot = sc.nextInt();
	    	g.checkLesson(day, slot);
	    	validSlots.add(new DaySlot(day,slot));
	    	sc.close();
	    }
	    
	    for (DaySlot ds: validSlots) {
	    	g.addLesson(new Lesson(activity, g, maxattendees, allowedinstructors), ds.day, ds.slot);
	    }
	}
	
	//R3
	public int addCustomer(String name) {
		customers.add(name);
		
		return nextCustomerId++;
	}
	
	public String getCustomer (int customerid) throws FitException{
		try {
			return customers.get(customerid-1);
		} catch (Exception e) {
			throw new FitException();
		}
	}
	
	//R4
	
	public void placeReservation(int customerid, String gymname, int day, int slot) throws FitException{
		if (!gyms.containsKey(gymname))
			throw new FitException();
		
		Gym gym = gyms.get(gymname);
		String customer = this.getCustomer(customerid);		// customer check
		Lesson lesson = gym.checkLesson(day, slot);
		
		if (lesson == null)
			throw new FitException();
		
		if (lesson.getRemainingSeats() == 0)
			throw new FitException();
		
		lesson.addParticipant(customerid);
	}
	
	public int getNumLessons(int customerid) {
		return gyms.values().stream()
			.collect(
				summingInt((Gym g) -> g.countLessonsPerCustomer(customerid))
			);
	}
	
	
	//R5
	
	public void addLessonGiven (String gymname, int day, int slot, String instructor) throws FitException{
		if (!gyms.containsKey(gymname))
			throw new FitException();
		
		Gym gym = gyms.get(gymname);
		Lesson lesson = gym.checkLesson(day, slot);
		
		if (lesson == null)
			throw new FitException();
		
		lesson.setActualTrainer(instructor);
	}
	
	public int getNumLessonsGiven (String gymname, String instructor) throws FitException {
		if (!gyms.containsKey(gymname))
			throw new FitException();
		
		Gym gym = gyms.get(gymname);
		List<Lesson> lessons = gym.getLessons();
		
		return (int)lessons.stream()
			.filter((Lesson l) -> l.getActualTrainer() != null && l.getActualTrainer().equals(instructor))
			.count();
	}
	//R6
	
	public String mostActiveGymn() {
		return gyms.values().stream()
			.collect(
				maxBy(new Comparator<Gym>() {
					@Override
					public int compare(Gym g1, Gym g2) {
						return g1.getLessons().size() - g2.getLessons().size();
					}
				})
			).orElse(new Gym("")).getName();
	}
	
	public Map<String, Integer> totalLessonsPerGymn() {
		return gyms.values().stream()
			.collect(
				toMap(
						Gym::getName,
						(Gym g) -> g.getLessons().size()
				)
			);
	}
	
	public SortedMap<Integer, List<String>> slotsPerNofParticipants(String gymname) throws FitException{
		if (!gyms.containsKey(gymname))
			throw new FitException();
		
	    return gyms.get(gymname).getSlotsPerNofParticipants();
	}
	

	
	
	
	


}
