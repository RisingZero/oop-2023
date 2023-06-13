package delivery;

import java.util.*;
import static java.util.stream.Collectors.*;

public class Restaurant implements Comparable<Restaurant> {
	
	private String name;
	private String category;
	private List<Dish> dishes = new ArrayList<Dish>();
	
	private List<Integer> ratingValues = new ArrayList<Integer>();
	
	public Restaurant(String name, String category) {
		this.name = name;
		this.category = category;
	}
	
	public String getName() {
		return name;
	}
	
	public String getCategory() {
		return category;
	}
	
	public Restaurant addDish(String name, float price) throws DeliveryException {
		Dish d = new Dish(name, price);
		if (dishes.contains(d))
			throw new DeliveryException();
		
		dishes.add(d);
		
		return this;
	}
	
	public List<Dish> getDishes() {
		return dishes;
	}
	
	public Restaurant addRating(int value) {
		if (value >= 0 && value <= 5) {
			ratingValues.add(value);
		}
		return this;
	}
	
	public double getAverageRating() {
		if (this.ratingValues.size() == 0)
			return -1;
		return ratingValues.stream().collect(averagingDouble(Integer::intValue));
	}
	
	@Override
	public int compareTo(Restaurant oth) {
		return this.name.compareTo(oth.name);
	}

}
