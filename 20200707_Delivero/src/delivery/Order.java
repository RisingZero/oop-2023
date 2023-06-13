package delivery;

import java.util.*;

public class Order {
	
	private class DishWithQuantity {
		public Dish dish;
		public int quantity;
		
		public DishWithQuantity(Dish d, int q) {
			dish = d;
			quantity = q;
		}
	}
	
	private int id;
	private List<DishWithQuantity> dishes = new ArrayList<DishWithQuantity>();
	private Restaurant restaurant;
	private String customer;
	private int deliveryTime;
	private int distance;
	private boolean pending = true;
	
	public Order(Restaurant r, String[] dishNames, int[] quantities, String customer, int deliveryTime, int distance) {
		restaurant = r;
		this.customer = customer;
		this.deliveryTime = deliveryTime;
		this.distance = distance;

		List<Dish> rd = r.getDishes();
		
		for (int i = 0; i < dishNames.length; i++) {
			Dish d = rd.get(rd.indexOf(new Dish(dishNames[i], 0)));
			this.dishes.add(new DishWithQuantity(d,quantities[i]));
		}
	}
	
	public String getRestaurantCategory() {
		return restaurant.getCategory();
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public boolean isPending() {
		return pending;
	}
	
	public void setAssigned() {
		this.pending = false;
	}
	
	public int getDeliveryTime() {
		return deliveryTime;
	}
	
	public int getDistance() {
		return distance;
	}
}
