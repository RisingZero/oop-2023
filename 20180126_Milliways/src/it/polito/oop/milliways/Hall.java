package it.polito.oop.milliways;

import java.util.*;

public class Hall implements Comparable<Hall> {
	
	private int id;
	private SortedSet<String> facilities = new TreeSet<>();
	
	public Hall(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void addFacility(String facility) throws MilliwaysException {
		if (facilities.contains(facility))
			throw new MilliwaysException();
		
		facilities.add(facility);
	}

	public List<String> getFacilities() {
        return facilities.stream().toList();
	}
	
	int getNumFacilities(){
        return facilities.size();
	}

	public boolean isSuitable(Party party) {
		return facilities.containsAll(party.getRequirements());
	}
	
	public boolean equals(Object o) {
		Hall oth = (Hall)o;
		return Integer.valueOf(id).equals(oth.id);
	}
	
	public int hashCode() {
		return Integer.valueOf(this.id). hashCode();
	}

	@Override
	public int compareTo(Hall oth) {
		return this.id - oth.id;
	}
}
