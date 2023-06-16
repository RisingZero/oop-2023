package fit;

import java.util.*;

public class Gym {
	
	private String name;
	private Lesson[][] calendar = new Lesson[7][13];
	
	public Gym(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public Lesson checkLesson(int day, int slot) throws FitException {
		if (day < 1 || day > 7 || slot < 8 || slot > 20)
			throw new FitException();
		
		return calendar[day-1][slot-8];
	}
	
	public Gym addLesson(Lesson l, int day, int slot) throws FitException {
		if (day < 1 || day > 7 || slot < 8 || slot > 20)
			throw new FitException();
		
		if (calendar[day-1][slot-8] != null)
			throw new FitException();
		
		calendar[day-1][slot-8] = l;
		
		return this;
	}
	
	public List<Lesson> getLessons() {
		List<Lesson> ls = new ArrayList<>();
		
		for (int day = 0; day < 7; day++) {
			for (int slot = 0; slot < 13; slot++) {
				if (calendar[day][slot] != null)
					ls.add(calendar[day][slot]);
			}
		}
		return ls;
	}
	
	public int countLessonsPerCustomer(int customerid) {
		int cnt = 0;
		for (int day = 0; day < 7; day++) {
			for (int slot = 0; slot < 13; slot++) {
				if (calendar[day][slot] != null && calendar[day][slot].getParticipants().contains(customerid))
					cnt++;
			}
		}
		return cnt;
	}
	
	public SortedMap<Integer,List<String>> getSlotsPerNofParticipants() {
		TreeMap<Integer,List<String>> out = new TreeMap<>();
		
		for (int day = 1; day <= 7; day++) {
			for (int slot = 8; slot <= 20; slot++) {
				try {
					Lesson l = this.checkLesson(day, slot);
					if (l != null) {
						int nop = l.getNumberOfParticipants();
						if (!out.containsKey(nop))
							out.put(nop, new ArrayList<String>());
						
						out.get(nop).add(String.format("%d.%d", day, slot));
					}
				} catch (Exception e) {}
				
			}
		}
		
		return out;
	}
}