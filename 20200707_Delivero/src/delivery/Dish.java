package delivery;

public class Dish implements Comparable<Dish> {

	private String name;
	private float price;
	
	public Dish(String name, float price) {
		this.name = name;
		this.price = price;
	}
	
	public String getName() {
		return name;
	}
	
	public float getPrice() {
		return price;
	}
	
	@Override
	public int compareTo(Dish oth) {
		return this.name.compareTo(oth.name);
	}
	
	@Override
	public boolean equals(Object o) {
		Dish oth = (Dish)o;
		return this.name.equals(oth.name);
	}
	
}
