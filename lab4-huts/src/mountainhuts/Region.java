package mountainhuts;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

/**
 * Class {@code Region} represents the main facade
 * class for the mountains hut system.
 * 
 * It allows defining and retrieving information about
 * municipalities and mountain huts.
 *
 */
public class Region {
	
	private String name;
	private HashSet<AltitudeRange> altitudes = new HashSet<AltitudeRange>();
	private Map<String,Municipality> municipalities = new TreeMap<String,Municipality>();
	private Map<String,MountainHut> huts = new TreeMap<String,MountainHut>();

	/**
	 * Create a region with the given name.
	 * 
	 * @param name
	 *            the name of the region
	 */
	public Region(String name) {
		this.name = name;
	}

	/**
	 * Return the name of the region.
	 * 
	 * @return the name of the region
	 */
	public String getName() {
		return name;
	}

	/**
	 * Create the ranges given their textual representation in the format
	 * "[minValue]-[maxValue]".
	 * 
	 * @param ranges
	 *            an array of textual ranges
	 */
	public void setAltitudeRanges(String... ranges) {
		for (String r: ranges) {
			AltitudeRange range = new AltitudeRange(r);
			altitudes.add(range);
		}
	}

	/**
	 * Return the textual representation in the format "[minValue]-[maxValue]" of
	 * the range including the given altitude or return the default range "0-INF".
	 * 
	 * @param altitude
	 *            the geographical altitude
	 * @return a string representing the range
	 */
	public String getAltitudeRange(Integer altitude) {
		for (AltitudeRange range: altitudes) {
			if (range.checkRange(altitude)) {
				return range.toString();
			}
		}
		return "0-INF";
	}

	/**
	 * Return all the municipalities available.
	 * 
	 * The returned collection is unmodifiable
	 * 
	 * @return a collection of municipalities
	 */
	public Collection<Municipality> getMunicipalities() {
		return municipalities.values();
	}

	/**
	 * Return all the mountain huts available.
	 * 
	 * The returned collection is unmodifiable
	 * 
	 * @return a collection of mountain huts
	 */
	public Collection<MountainHut> getMountainHuts() {
		return huts.values();
	}

	/**
	 * Create a new municipality if it is not already available or find it.
	 * Duplicates must be detected by comparing the municipality names.
	 * 
	 * @param name
	 *            the municipality name
	 * @param province
	 *            the municipality province
	 * @param altitude
	 *            the municipality altitude
	 * @return the municipality
	 */
	public Municipality createOrGetMunicipality(String name, String province, Integer altitude) {
		if (municipalities.containsKey(name))
			return municipalities.get(name);
		
		Municipality m = new Municipality(name, province, altitude);
		municipalities.put(name,  m);
		
		return m;
	}

	/**
	 * Create a new mountain hut if it is not already available or find it.
	 * Duplicates must be detected by comparing the mountain hut names.
	 *
	 * @param name
	 *            the mountain hut name
	 * @param category
	 *            the mountain hut category
	 * @param bedsNumber
	 *            the number of beds in the mountain hut
	 * @param municipality
	 *            the municipality in which the mountain hut is located
	 * @return the mountain hut
	 */
	public MountainHut createOrGetMountainHut(String name, String category, Integer bedsNumber,
			Municipality municipality) {
		if (huts.containsKey(name))
			return huts.get(name);
		
		MountainHut h = new MountainHut(name, null, category, bedsNumber, municipality);
		huts.put(name, h);
		
		return h;
	}

	/**
	 * Create a new mountain hut if it is not already available or find it.
	 * Duplicates must be detected by comparing the mountain hut names.
	 * 
	 * @param name
	 *            the mountain hut name
	 * @param altitude
	 *            the mountain hut altitude
	 * @param category
	 *            the mountain hut category
	 * @param bedsNumber
	 *            the number of beds in the mountain hut
	 * @param municipality
	 *            the municipality in which the mountain hut is located
	 * @return a mountain hut
	 */
	public MountainHut createOrGetMountainHut(String name, Integer altitude, String category, Integer bedsNumber,
			Municipality municipality) {
		if (huts.containsKey(name))
			return huts.get(name);
			
		MountainHut h = new MountainHut(name, altitude, category, bedsNumber, municipality);
		huts.put(name, h);
		
		return h;
	}

	/**
	 * Creates a new region and loads its data from a file.
	 * 
	 * The file must be a CSV file and it must contain the following fields:
	 * <ul>
	 * <li>{@code "Province"},
	 * <li>{@code "Municipality"},
	 * <li>{@code "MunicipalityAltitude"},
	 * <li>{@code "Name"},
	 * <li>{@code "Altitude"},
	 * <li>{@code "Category"},
	 * <li>{@code "BedsNumber"}
	 * </ul>
	 * 
	 * The fields are separated by a semicolon (';'). The field {@code "Altitude"}
	 * may be empty.
	 * 
	 * @param name
	 *            the name of the region
	 * @param file
	 *            the path of the file
	 */
	public static Region fromFile(String name, String file) {
		Region r = new Region(name);
		List<String> lines = readData(file);
		
		for (String record: lines.subList(1, lines.size())) {
			String[] fields = record.split(";");
			
			Municipality m = r.createOrGetMunicipality(fields[1], fields[0], Integer.parseInt(fields[2]));
			if (fields[4].length() == 0)
				r.createOrGetMountainHut(fields[3], fields[5], Integer.parseInt(fields[6]), m);
			else
				r.createOrGetMountainHut(fields[3], Integer.parseInt(fields[4]), fields[5], Integer.parseInt(fields[6]), m);
		}
		
		return r;
	}

	/**
	 * Reads the lines of a text file.
	 *
	 * @param file path of the file
	 * @return a list with one element per line
	 */
	public static List<String> readData(String file) {
		try (BufferedReader in = new BufferedReader(new FileReader(file))) {
			return in.lines().collect(toList());
		} catch (IOException e) {
			System.err.println(e.getMessage());
			return new ArrayList<>();
		}
	}

	/**
	 * Count the number of municipalities with at least a mountain hut per each
	 * province.
	 * 
	 * @return a map with the province as key and the number of municipalities as
	 *         value
	 */
	public Map<String, Long> countMunicipalitiesPerProvince() {
		Collection<Municipality> municipalities = this.getMunicipalities();
		return municipalities.stream()
				.collect(groupingBy(Municipality::getProvince, counting()));
	}

	/**
	 * Count the number of mountain huts per each municipality within each province.
	 * 
	 * @return a map with the province as key and, as value, a map with the
	 *         municipality as key and the number of mountain huts as value
	 */
	public Map<String, Map<String, Long>> countMountainHutsPerMunicipalityPerProvince() {
		Collection<MountainHut> huts = this.getMountainHuts();
		return huts.stream()
				.collect(
					groupingBy(h -> {
							return h.getMunicipality().getProvince();
						},
						groupingBy(h -> {
								return h.getMunicipality().getName();
							}, 
							counting()
						)
					)
				);
	}

	/**
	 * Count the number of mountain huts per altitude range. If the altitude of the
	 * mountain hut is not available, use the altitude of its municipality.
	 * 
	 * @return a map with the altitude range as key and the number of mountain huts
	 *         as value
	 */
	public Map<String, Long> countMountainHutsPerAltitudeRange() {
		Collection<MountainHut> huts = this.getMountainHuts();
		return huts.stream()
				.collect(
					groupingBy(
						h -> this.getAltitudeRange(h.getAltitude().orElse(h.getMunicipality().getAltitude())),
						counting()
					)
				);
	}

	/**
	 * Compute the total number of beds available in the mountain huts per each
	 * province.
	 * 
	 * @return a map with the province as key and the total number of beds as value
	 */
	public Map<String, Integer> totalBedsNumberPerProvince() {
		Collection<MountainHut> huts = this.getMountainHuts();
		return huts.stream()
				.collect(
					groupingBy(
						h -> h.getMunicipality().getProvince(),
						HashMap::new,
						summingInt(MountainHut::getBedsNumber)
					)
				);
	}

	/**
	 * Compute the maximum number of beds available in a single mountain hut per
	 * altitude range. If the altitude of the mountain hut is not available, use the
	 * altitude of its municipality.
	 * 
	 * @return a map with the altitude range as key and the maximum number of beds
	 *         as value
	 */
	public Map<String, Optional<Integer>> maximumBedsNumberPerAltitudeRange() {
		Collection<MountainHut> huts = this.getMountainHuts();
		return huts.stream()
				.collect(
					groupingBy(
						h -> this.getAltitudeRange(h.getAltitude().orElse(h.getMunicipality().getAltitude())),
						mapping(h -> h.getBedsNumber(), maxBy(Integer::compareTo))
					)
				);
	}

	/**
	 * Compute the municipality names per number of mountain huts in a municipality.
	 * The lists of municipality names must be in alphabetical order.
	 * 
	 * @return a map with the number of mountain huts in a municipality as key and a
	 *         list of municipality names as value
	 */
	public Map<Long, List<String>> municipalityNamesPerCountOfMountainHuts() {
		Collection<Municipality> municipalities = this.getMunicipalities();
		Collection<MountainHut> huts = this.getMountainHuts();
		return municipalities.stream()
				.collect(
					groupingBy(
						m -> {
							return Long.valueOf(huts.stream()
								.collect(
									Collectors.filtering(h -> h.getMunicipality() == m, toList())
								).size());
						},
						mapping(m -> m.getName(), toList())
					)
				);
	}

}
