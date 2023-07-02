package mountainhuts;

import java.util.Optional;

/**
 * Represents a mountain hut
 * 
 * It includes a name, optional altitude, category,
 * number of beds and location municipality.
 *  
 *
 */
public class MountainHut {
		
	private String name;
	private Integer altitude;
	private String category;
	private int bedsNumber;
	private Municipality municipality;
	
	public MountainHut(String name, Integer altitude, String category, Integer bedsNumber, Municipality municipality) {
		this.name = name;
		this.altitude = altitude;
		this.category = category;
		this.bedsNumber = bedsNumber;
		this.municipality = municipality;
	}

	public String getName() {
		return name;
	}

	public Optional<Integer> getAltitude() {
		return Optional.ofNullable(altitude);
	}

	public String getCategory() {
		return category;
	}

	public Integer getBedsNumber() {
		return bedsNumber;
	}

	public Municipality getMunicipality() {
		return municipality;
	}
}
