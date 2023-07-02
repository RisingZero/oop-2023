package diet;

import java.util.*;


/**
 * Represents a takeaway restaurant chain.
 * It allows managing restaurants, customers, and orders.
 */
public class Takeaway {
	
	private Food foodsRef;
	HashMap<String,Restaurant> restaurants = new HashMap<String,Restaurant>();
	TreeSet<Customer> customers = new TreeSet<Customer>();
	TreeSet<Order> orders = new TreeSet<Order>();

	/**
	 * Constructor
	 * @param food the reference {@link Food} object with materials and products info.
	 */
	public Takeaway(Food food){
		this.foodsRef = food;
	}

	/**
	 * Creates a new restaurant with a given name
	 *
	 * @param restaurantName name of the restaurant
	 * @return the new restaurant
	 */
	public Restaurant addRestaurant(String restaurantName) {
		Restaurant restaurant = new Restaurant(restaurantName);
		
		restaurants.put(restaurantName, restaurant);
		
		return restaurant;
	}

	/**
	 * Retrieves the names of all restaurants
	 *
	 * @return collection of restaurant names
	 */
	public Collection<String> restaurants() {
		return restaurants.keySet();
	}

	/**
	 * Creates a new customer for the takeaway
	 * @param firstName first name of the customer
	 * @param lastName	last name of the customer
	 * @param email		email of the customer
	 * @param phoneNumber mobile phone number
	 *
	 * @return the object representing the newly created customer
	 */
	public Customer registerCustomer(String firstName, String lastName, String email, String phoneNumber) {
		Customer customer = new Customer(
				firstName,
				lastName,
				email,
				phoneNumber
		);
		
		customers.add(customer);
		
		return customer;
	}

	/**
	 * Retrieves all registered customers
	 *
	 * @return sorted collection of customers
	 */
	public Collection<Customer> customers(){
		return customers;
	}


	/**
	 * Creates a new order for the chain.
	 *
	 * @param customer		 customer issuing the order
	 * @param restaurantName name of the restaurant that will take the order
	 * @param time	time of desired delivery
	 * @return order object
	 */
	public Order createOrder(Customer customer, String restaurantName, String time) {
		Restaurant restaurant = restaurants.get(restaurantName);
		
		if (!restaurant.isOpenAt(time)) {
			time = restaurant.getNextOpeningTime(time);
		}
		
		Order order = new Order(customer, restaurantName, time);
		
		restaurant.addOrder(order);
		
		return order;
	}

	/**
	 * Find all restaurants that are open at a given time.
	 *
	 * @param time the time with format {@code "HH:MM"}
	 * @return the sorted collection of restaurants
	 */
	public Collection<Restaurant> openRestaurants(String time){
		TreeMap<String,Restaurant> open = new TreeMap<String,Restaurant>();
		
		for (String r: restaurants.keySet()) {
			if (restaurants.get(r).isOpenAt(time))
				open.put(r, restaurants.get(r));
		}
		
		return open.values();
	}
}
