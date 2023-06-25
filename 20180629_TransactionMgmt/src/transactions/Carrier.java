package transactions;

import java.util.*;

public class Carrier implements Comparable<Carrier> {
	
	private String name;
	private SortedSet<Region> regionsServed = new TreeSet<>();
	
	public Carrier(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public List<Region> getServedRegions() {
		return regionsServed.stream().toList();
	}
	
	public Carrier addRegion(Region r) {
		regionsServed.add(r);
		return this;
	}
	
	@Override
	public int compareTo(Carrier oth) {
		return this.name.compareTo(oth.name);
	}
	
	@Override
	public boolean equals(Object o) {
		Carrier oth = (Carrier)o;
		return this.name.equals(oth.name);
	}
	
	@Override
	public int hashCode() {
		return this.name.hashCode();
	}

}
