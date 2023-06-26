package delivery;

import java.util.Comparator;

public class MenuItem implements Comparable<MenuItem> {
	
	private String description;
	private double price;
	private String category;
	private int prepTime;
	
	public MenuItem (String description, double price, String category, int prepTime) {
		this.description = description;
		this.price = price;
		this.category = category;
		this.prepTime = prepTime;
	}
	
	public String getDescription() { return description; }
	
	public double getPrice() { return price; }
	
	public String getCategory() { return category; }
	
	public int getPrepTime() { return prepTime; }
	
	public boolean matches(String pattern) {
		if (pattern.isEmpty())
			return true;
		
		return this.description.toLowerCase().contains(pattern.toLowerCase());
	}
	
	@Override
	public String toString( ) {
		return String.format("[%s] %s : %.2f", category, description, price);
	}
	
	@Override
	public int compareTo(MenuItem oth) {
		return Comparator.comparing(MenuItem::getCategory)
				.thenComparing(MenuItem::getDescription)
				.compare(this, oth);
	}

}
