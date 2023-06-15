package sports;

import java.util.*;
import static java.util.stream.Collectors.*;

public class Product implements Comparable<Product> {

	private String name;
	private String activity;
	private Category category;
	private List<Rating> ratings = new ArrayList<>();
	
	public Product(String name, String activity, Category category) {
		this.name = name;
		this.activity = activity;
		this.category = category;
	}
	
	public String getName() {
		return name;
	}
	
	public String getActivity() {
		return activity;
	}
	
	public Category getCategory() {
		return category;
	}
	
	public Product addRating(String userName, int stars, String comment) throws SportsException {
		Rating r = new Rating(userName, stars, comment);
		ratings.add(r);
		return this;
	}
	
	public List<Rating> getRatings() {
		return ratings;
	}
	
	public double getAverageRating() {
		return ratings.stream()
			.collect(
				averagingInt(Rating::getStars)
			);
	}
	
	@Override
	public int compareTo(Product oth) {
		return this.name.compareTo(oth.name);
	}
	
	
}
