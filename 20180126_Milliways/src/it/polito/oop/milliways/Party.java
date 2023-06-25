package it.polito.oop.milliways;

import java.util.*;
import java.util.Map.Entry;
import static java.util.stream.Collectors.*; 

public class Party {
	
	private Map<Race,Integer> raceCount = new HashMap<>();

    public void addCompanions(Race race, int num) {
    	raceCount.put(race, Optional.ofNullable(raceCount.get(race)).orElse(0) + num);
	}

	public int getNum() {
        return raceCount.values().stream().collect(summingInt(Integer::valueOf));
	}

	public int getNum(Race race) {
	    return raceCount.get(race);
	}

	public List<String> getRequirements() {
        return raceCount.keySet().stream()
        		.flatMap(r -> r.getRequirements().stream())
        		.collect(toCollection(TreeSet::new)).stream().toList();
	}

    public Map<String,Integer> getDescription(){
        return raceCount.keySet().stream()
        		.collect(
        				toMap(
        						Race::getName,
        						r -> raceCount.get(r)
        				)
        		);
    }
}
