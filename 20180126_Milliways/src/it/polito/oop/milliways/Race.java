package it.polito.oop.milliways;

import java.util.*;

public class Race {
	
	private String name;
	private SortedSet<String> requirements = new TreeSet<>();
	
	public Race(String name) {
		this.name = name;
	}
    
	public void addRequirement(String requirement) throws MilliwaysException {
		if (requirements.contains(requirement))
			throw new MilliwaysException();
		
		requirements.add(requirement);
	}
	
	public List<String> getRequirements() {
        return requirements.stream().toList();
	}
	
	public String getName() {
        return name;
	}
}
