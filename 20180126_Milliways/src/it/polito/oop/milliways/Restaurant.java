package it.polito.oop.milliways;

import java.util.*;
import java.util.Map.Entry;
import static java.util.stream.Collectors.*;

public class Restaurant {
	
	private Map<String,Race> races = new HashMap<>();
	private List<Hall> halls = new ArrayList<>();
	private Map<Party,Hall> seatings = new HashMap<>();

    public Restaurant() {
	}
	
	public Race defineRace(String name) throws MilliwaysException {
		if (races.containsKey(name))
			throw new MilliwaysException();
		
		Race r = new Race(name);
		races.put(name, r);
	    return r;
	}
	
	public Party createParty() {
	    return new Party();
	}
	
	public Hall defineHall(int id) throws MilliwaysException {
		Hall h = new Hall(id);
		
		if (halls.contains(h))
			throw new MilliwaysException();

		halls.add(h);
	    return h;
	}

	public List<Hall> getHallList() {
		return halls;
	}

	public Hall seat(Party party, Hall hall) throws MilliwaysException {
		if (!hall.isSuitable(party))
			throw new MilliwaysException();
		
		seatings.put(party, hall);
        return hall;
	}

	public Hall seat(Party party) throws MilliwaysException {
		for (Hall hall: halls) {
			if (hall.isSuitable(party)) {
				seatings.put(party, hall);
				return hall;
			}
			
		}
		throw new MilliwaysException();
	}

	public Map<Race, Integer> statComposition() {
		Map<Race,Integer> stats = new HashMap<>();
		
		for (Party party: seatings.keySet()) {
			Map<String,Integer> partyStats = party.getDescription();
			
			for (String raceName: partyStats.keySet()) {
				Race race = races.get(raceName);
				if (!stats.containsKey(race))
					stats.put(race, 0);
				
				stats.put(race, partyStats.get(raceName) + stats.get(race));
			}
		}
        return stats;
	}

	public List<String> statFacility() {
		Map<Integer,SortedSet<String>> stats = new TreeMap<>(Comparator.reverseOrder());
		Map<String,Integer> facilitiesHallsCount = new HashMap<>();
		
		for (Hall h: halls) {
			for (String f: h.getFacilities()) {
				if (!facilitiesHallsCount.containsKey(f))
					facilitiesHallsCount.put(f, 1);
				else
					facilitiesHallsCount.put(f, facilitiesHallsCount.get(f) + 1);
			}
		}
		
		for (String facility: facilitiesHallsCount.keySet()) {
			if (!stats.containsKey(facilitiesHallsCount.get(facility))) {
				stats.put(facilitiesHallsCount.get(facility),new TreeSet<String>());
			}
			
			stats.get(facilitiesHallsCount.get(facility)).add(facility);
		}
        
		List<String> out = new ArrayList<>();
		for (Integer num: stats.keySet()) {
			out.addAll(stats.get(num));
		}
		return out;
	}
	
	public Map<Integer,List<Integer>> statHalls() {
		return halls.stream().sorted()
			.collect(
					groupingBy(
							h -> h.getFacilities().size(),
							mapping(Hall::getId, toList())
					)
			);
	}

}
