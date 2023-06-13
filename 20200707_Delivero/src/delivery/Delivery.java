package delivery;

import java.util.*;
import static java.util.stream.Collectors.*;

public class Delivery {
	
	private Set<String> categories = new HashSet<String>();
	private Map<String,Restaurant> restaurants = new TreeMap<String,Restaurant>();
	
	private int nextOrderId = 1;
	private List<Order> orders = new ArrayList<Order>();
	
	// R1
	
    /**
     * adds one category to the list of categories managed by the service.
     * 
     * @param category name of the category
     * @throws DeliveryException if the category is already available.
     */
	public void addCategory (String category) throws DeliveryException {
		if (categories.contains(category))
			throw new DeliveryException();
		
		categories.add(category);
	}
	
	/**
	 * retrieves the list of defined categories.
	 * 
	 * @return list of category names
	 */
	public List<String> getCategories() {
		return categories.stream().toList();
	}
	
	/**
	 * register a new restaurant to the service with a related category
	 * 
	 * @param name     name of the restaurant
	 * @param category category of the restaurant
	 * @throws DeliveryException if the category is not defined.
	 */
	public void addRestaurant (String name, String category) throws DeliveryException {
		if (!categories.contains(category))
			throw new DeliveryException();
		
		Restaurant r = new Restaurant(name, category);
		restaurants.put(name,r);
	}
	
	/**
	 * retrieves an ordered list by name of the restaurants of a given category. 
	 * It returns an empty list in there are no restaurants in the selected category 
	 * or the category does not exist.
	 * 
	 * @param category name of the category
	 * @return sorted list of restaurant names
	 */
	public List<String> getRestaurantsForCategory(String category) {
		Map<String,List<String>> restaurantsPerCategory = restaurants.values().stream()
			.sorted()
			.collect(
				groupingBy(
					Restaurant::getCategory,
					mapping(Restaurant::getName, toList())
				)
			);
		
		if (!restaurantsPerCategory.containsKey(category))
			return new ArrayList<String>();
			
        return restaurantsPerCategory.get(category);
	}
	
	// R2
	
	/**
	 * adds a dish to the list of dishes of a restaurant. 
	 * Every dish has a given price.
	 * 
	 * @param name             name of the dish
	 * @param restaurantName   name of the restaurant
	 * @param price            price of the dish
	 * @throws DeliveryException if the dish name already exists
	 */
	public void addDish(String name, String restaurantName, float price) throws DeliveryException {
		if (!restaurants.containsKey(restaurantName))
			throw new DeliveryException();
		
		restaurants.get(restaurantName).addDish(name, price);
	}
	
	/**
	 * returns a map associating the name of each restaurant with the 
	 * list of dish names whose price is in the provided range of price (limits included). 
	 * If the restaurant has no dishes in the range, it does not appear in the map.
	 * 
	 * @param minPrice  minimum price (included)
	 * @param maxPrice  maximum price (included)
	 * @return map restaurant -> dishes
	 */
	public Map<String,List<String>> getDishesByPrice(float minPrice, float maxPrice) {
		Map<String,List<String>> dishesPerRestaurant = new HashMap<String,List<String>>();
		
		for (String rName : restaurants.keySet()) {
			Restaurant r = restaurants.get(rName);
			
			List<String> dishesInRange = r.getDishes().stream()
				.filter((Dish d) -> d.getPrice() >= minPrice && d.getPrice() <= maxPrice)
				.map(Dish::getName).toList();
			
			if (dishesInRange.size() > 0)
				dishesPerRestaurant.put(rName, dishesInRange);
		}
		
        return dishesPerRestaurant;
	}
	
	/**
	 * retrieve the ordered list of the names of dishes sold by a restaurant. 
	 * If the restaurant does not exist or does not sell any dishes 
	 * the method must return an empty list.
	 *  
	 * @param restaurantName   name of the restaurant
	 * @return alphabetically sorted list of dish names 
	 */
	public List<String> getDishesForRestaurant(String restaurantName) {
		if (!restaurants.containsKey(restaurantName))
			return new ArrayList<String>();
		
		return restaurants.get(restaurantName).getDishes().stream().sorted().map(Dish::getName).toList();
	}
	
	/**
	 * retrieves the list of all dishes sold by all restaurants belonging to the given category. 
	 * If the category is not defined or there are no dishes in the category 
	 * the method must return and empty list.
	 *  
	 * @param category     the category
	 * @return 
	 */
	public List<String> getDishesByCategory(String category) {
		if (!categories.contains(category))
			return new ArrayList<String>();
		
		return restaurants.values().stream().filter((Restaurant r) -> r.getCategory().equals(category))
			.flatMap((Restaurant r) -> r.getDishes().stream()).map(Dish::getName).toList();
	}
	
	//R3
	
	/**
	 * creates a delivery order. 
	 * Each order may contain more than one product with the related quantity. 
	 * The delivery time is indicated with a number in the range 8 to 23. 
	 * The delivery distance is expressed in kilometers. 
	 * Each order is assigned a progressive order ID, the first order has number 1.
	 * 
	 * @param dishNames        names of the dishes
	 * @param quantities       relative quantity of dishes
	 * @param customerName     name of the customer
	 * @param restaurantName   name of the restaurant
	 * @param deliveryTime     time of delivery (8-23)
	 * @param deliveryDistance distance of delivery
	 * 
	 * @return order ID
	 */
	public int addOrder(String dishNames[], int quantities[], String customerName, String restaurantName, int deliveryTime, int deliveryDistance) {
		Restaurant r = restaurants.get(restaurantName);
		Order o = new Order(r, dishNames, quantities, customerName, deliveryTime, deliveryDistance);
		o.setId(nextOrderId++);
		orders.add(o);
		
	    return o.getId();
	}
	
	/**
	 * retrieves the IDs of the orders that satisfy the given constraints.
	 * Only the  first {@code maxOrders} (according to the orders arrival time) are returned
	 * they must be scheduled to be delivered at {@code deliveryTime} 
	 * whose {@code deliveryDistance} is lower or equal that {@code maxDistance}. 
	 * Once returned by the method the orders must be marked as assigned 
	 * so that they will not be considered if the method is called again. 
	 * The method returns an empty list if there are no orders (not yet assigned) 
	 * that meet the requirements.
	 * 
	 * @param deliveryTime required time of delivery 
	 * @param maxDistance  maximum delivery distance
	 * @param maxOrders    maximum number of orders to retrieve
	 * @return list of order IDs
	 */
	public List<Integer> scheduleDelivery(int deliveryTime, int maxDistance, int maxOrders) {
		
		List<Order> ordersToAssign = orders.stream()
			.filter((Order o) -> {
				return o.isPending() && o.getDeliveryTime() == deliveryTime && o.getDistance() <= maxDistance;
			})
			.sorted((Order o1, Order o2) -> Integer.valueOf(o1.getId()).compareTo(o2.getId()))
			.limit(maxOrders).toList();
		
		ordersToAssign.forEach((Order o) -> o.setAssigned());
        return ordersToAssign.stream().map(Order::getId).toList();
	}
	
	/**
	 * retrieves the number of orders that still need to be assigned
	 * @return the unassigned orders count
	 */
	public int getPendingOrders() {
        return (int)orders.stream().filter((Order o) -> o.isPending()).count();
	}
	
	// R4
	/**
	 * records a rating (a number between 0 and 5) of a restaurant.
	 * Ratings outside the valid range are discarded.
	 * 
	 * @param restaurantName   name of the restaurant
	 * @param rating           rating
	 */
	public void setRatingForRestaurant(String restaurantName, int rating) {
		if (restaurants.containsKey(restaurantName))
			this.restaurants.get(restaurantName).addRating(rating);
	}
	
	/**
	 * retrieves the ordered list of restaurant. 
	 * 
	 * The restaurant must be ordered by decreasing average rating. 
	 * The average rating of a restaurant is the sum of all rating divided by the number of ratings.
	 * 
	 * @return ordered list of restaurant names
	 */
	public List<String> restaurantsAverageRating() {
		return restaurants.values().stream()
			.sorted((Restaurant r1, Restaurant r2) -> Double.valueOf(r2.getAverageRating()).compareTo(Double.valueOf(r1.getAverageRating())))
			.filter((Restaurant r) -> r.getAverageRating() != -1)
			.map(Restaurant::getName)
			.toList();
	}
	
	//R5
	/**
	 * returns a map associating each category to the number of orders placed to any restaurant in that category. 
	 * Also categories whose restaurants have not received any order must be included in the result.
	 * 
	 * @return map category -> order count
	 */
	public Map<String,Long> ordersPerCategory() {
		Map<String,Long> ordersPerCat = orders.stream().collect(
			groupingBy(Order::getRestaurantCategory, counting())
		);
		
		for (Restaurant r : restaurants.values()) {
			if (!ordersPerCat.containsKey(r.getCategory()))
				ordersPerCat.put(r.getCategory(),0L);
		}
        return ordersPerCat;
	}
	
	/**
	 * retrieves the name of the restaurant that has received the higher average rating.
	 * 
	 * @return restaurant name
	 */
	public String bestRestaurant() {
        return restaurantsAverageRating().get(0);
	}
}
 