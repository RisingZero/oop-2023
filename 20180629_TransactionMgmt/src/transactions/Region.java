package transactions;

import java.util.*;

public class Region implements Comparable<Region> {
	
	private String name;
	private SortedSet<String> places = new TreeSet<>();
	
	public Region (String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public List<String> getPlaces() {
		return places.stream().toList();
	}
	
	public Region addPlace(String name) {
		places.add(name);
		return this;
	}
	
	@Override
	public int compareTo(Region oth) {
		return this.name.compareTo(oth.name);
	}
	
	@Override
	public boolean equals(Object o) {
		Region oth = (Region)o;
		return this.name.equals(oth.name);
	}
	
	@Override
	public int hashCode() {
		return this.name.hashCode();
	}
}
