package delivery;

import java.util.*;
import static java.util.stream.Collectors.*;

public class Delivery {
    
    public static enum OrderStatus { NEW, CONFIRMED, PREPARATION, ON_DELIVERY, DELIVERED } 
    
    private Map<Integer,Customer> customers = new HashMap<>();
    private int nextCustomerId = 1;
    
    private List<MenuItem> menuItems = new ArrayList<>();
    
    private Map<Integer,Order> orders = new HashMap<>();
    private int nextOrderId = 1;
    
    /**
     * Creates a new customer entry and returns the corresponding unique ID.
     * 
     * The ID for the first customer must be 1.
     * 
     * 
     * @param name name of the customer
     * @param address customer address
     * @param phone customer phone number
     * @param email customer email
     * @return unique customer ID (positive integer)
     */
    public int newCustomer(String name, String address, String phone, String email) throws DeliveryException {
    	if (customers.values().stream().map(Customer::getEmail).toList().contains(email))
    		throw new DeliveryException();
    	
    	Customer c = new Customer(nextCustomerId++, name, address, phone, email);
    	customers.put(c.getId(), c);
        return c.getId();
    }
    
    /**
     * Returns a string description of the customer in the form:
     * <pre>
     * "NAME, ADDRESS, PHONE, EMAIL"
     * </pre>
     * 
     * @param customerId
     * @return customer description string
     */
    public String customerInfo(int customerId){
        return customers.get(customerId).toString();
    }
    
    /**
     * Returns the list of customers, sorted by name
     * 
     */
    public List<String> listCustomers(){
        return customers.values().stream().sorted().map(Customer::toString).toList();
    }
    
    /**
     * Add a new item to the delivery service menu
     * 
     * @param description description of the item (e.g. "Pizza Margherita", "Bunet")
     * @param price price of the item (e.g. 5 EUR)
     * @param category category of the item (e.g. "Main dish", "Dessert")
     * @param prepTime estimate preparation time in minutes
     */
    public void newMenuItem(String description, double price, String category, int prepTime){
        MenuItem mi = new MenuItem(description, price, category, prepTime);
        
        menuItems.add(mi);
    }
    
    /**
     * Search for items matching the search string.
     * The items are sorted by category first and then by description.
     * 
     * The format of the items is:
     * <pre>
     * "[CATEGORY] DESCRIPTION : PRICE"
     * </pre>
     * 
     * @param search search string
     * @return list of matching items
     */
    public List<String> findItem(String search){
    	return new ArrayList<>(menuItems.stream().filter(mi -> mi.matches(search)).sorted().map(MenuItem::toString).toList());
    }
    
    /**
     * Creates a new order for the given customer.
     * Returns the unique id of the order, a progressive
     * integer number starting at 1.
     * 
     * @param customerId
     * @return order id
     */
    public int newOrder(int customerId){
    	Order o = new Order(customerId, nextOrderId++);
    	orders.put(o.getOrderId(), o);
        return o.getOrderId();
    }
    
    /**
     * Add a new item to the order with the given quantity.
     * 
     * If the same item is already present in the order is adds the
     * given quantity to the current quantity.
     * 
     * The method returns the final total quantity of the item in the order. 
     * 
     * The item is found through the search string as in {@link #findItem}.
     * If none or more than one item is matched, then an exception is thrown.
     * 
     * @param orderId the id of the order
     * @param search the item search string
     * @param qty the quantity of the item to be added
     * @return the final quantity of the item in the order
     * @throws DeliveryException in case of non unique match or invalid order ID
     */
    public int addItem(int orderId, String search, int qty) throws DeliveryException {
    	if (!orders.containsKey(orderId))
    		throw new DeliveryException();
    	
    	List<MenuItem> matchingItems = menuItems.stream().filter(mi -> mi.matches(search)).toList();
    	if (matchingItems.size() != 1)
    		throw new DeliveryException();
    	
    	return orders.get(orderId).addItem(matchingItems.get(0), qty);
    }
    
    /**
     * Show the items of the order using the following format
     * <pre>
     * "DESCRIPTION, QUANTITY"
     * </pre>
     * 
     * @param orderId the order ID
     * @return the list of items in the order
     * @throws DeliveryException when the order ID in invalid
     */
    public List<String> showOrder(int orderId) throws DeliveryException {
    	if (!orders.containsKey(orderId))
    		throw new DeliveryException();
    	
        return new ArrayList<>(orders.get(orderId).summary());
    }
    
    /**
     * Retrieves the total amount of the order.
     * 
     * @param orderId the order ID
     * @return the total amount of the order
     * @throws DeliveryException when the order ID in invalid
     */
    public double totalOrder(int orderId) throws DeliveryException {
    	if (!orders.containsKey(orderId))
    		throw new DeliveryException();
    	
        return orders.get(orderId).totalCheck();
    }
    
    /**
     * Retrieves the status of an order
     * 
     * @param orderId the order ID
     * @return the current status of the order
     * @throws DeliveryException when the id is invalid
     */
    public OrderStatus getStatus(int orderId) throws DeliveryException {
    	if (!orders.containsKey(orderId))
    		throw new DeliveryException();
    	
        return orders.get(orderId).getStatus();
    }
    
    /**
     * Confirm the order. The status goes from {@code NEW} to {@code CONFIRMED}
     * 
     * Returns the delivery time estimate computed as the sum of:
     * <ul>
     * <li>start-up delay (conventionally 5 min)
     * <li>preparation time (max of all item preparation time)
     * <li>transportation time (conventionally 15 min)
     * </ul>
     * 
     * @param orderId the order id
     * @return delivery time estimate in minutes
     * @throws DeliveryException when order ID invalid to status not {@code NEW}
     */
    public int confirm(int orderId) throws DeliveryException {
    	if (!orders.containsKey(orderId))
    		throw new DeliveryException();
    	
    	Order order = orders.get(orderId);
    	
    	if (order.getStatus() != OrderStatus.NEW)
    		throw new DeliveryException();
    	
        return order.confirm();
    }

    /**
     * Start the order preparation. The status goes from {@code CONFIRMED} to {@code PREPARATION}
     * 
     * Returns the delivery time estimate computed as the sum of:
     * <ul>
     * <li>preparation time (max of all item preparation time)
     * <li>transportation time (conventionally 15 min)
     * </ul>
     * 
     * @param orderId the order id
     * @return delivery time estimate in minutes
     * @throws DeliveryException when order ID invalid to status not {@code CONFIRMED}
     */
    public int start(int orderId) throws DeliveryException {
    	if (!orders.containsKey(orderId))
    		throw new DeliveryException();
    	
    	Order order = orders.get(orderId);
    	
    	if (order.getStatus() != OrderStatus.CONFIRMED)
    		throw new DeliveryException();
    	
        return order.start();
    }

    /**
     * Begins the order delivery. The status goes from {@code PREPARATION} to {@code ON_DELIVERY}
     * 
     * Returns the delivery time estimate computed as the sum of:
     * <ul>
     * <li>transportation time (conventionally 15 min)
     * </ul>
     * 
     * @param orderId the order id
     * @return delivery time estimate in minutes
     * @throws DeliveryException when order ID invalid to status not {@code PREPARATION}
     */
    public int deliver(int orderId) throws DeliveryException {
    	if (!orders.containsKey(orderId))
    		throw new DeliveryException();
    	
    	Order order = orders.get(orderId);
    	
    	if (order.getStatus() != OrderStatus.PREPARATION)
    		throw new DeliveryException();
    	
        return order.deliver();
    }
    
    /**
     * Complete the delivery. The status goes from {@code ON_DELIVERY} to {@code DELIVERED}
     * 
     * 
     * @param orderId the order id
     * @return delivery time estimate in minutes
     * @throws DeliveryException when order ID invalid to status not {@code ON_DELIVERY}
     */
    public void complete(int orderId) throws DeliveryException {
    	if (!orders.containsKey(orderId))
    		throw new DeliveryException();
    	
    	Order order = orders.get(orderId);
    	
    	if (order.getStatus() != OrderStatus.ON_DELIVERY)
    		throw new DeliveryException();
    	
        order.complete();
    }
    
    /**
     * Retrieves the total amount for all orders of a customer.
     * 
     * @param customerId the customer ID
     * @return total amount
     */
    public double totalCustomer(int customerId){
    	return orders.values().stream()
    			.filter(o -> o.getStatus() != OrderStatus.NEW)
    			.filter(o -> o.getCustomerId() == customerId)
    			.collect(
    				summingDouble(Order::totalCheck)
    			);
    }
    
    /**
     * Computes the best customers by total amount of orders.
     *  
     * @return the classification
     */
    public SortedMap<Double,List<String>> bestCustomers(){
    	return customers.values().stream()
    		.collect(
    			groupingBy(
    				c -> this.totalCustomer(c.getId()),
    				TreeMap::new,
    				mapping(Customer::toString, toList())
    			)
    		).descendingMap();
    }
    
// NOT REQUIRED
//
//    /**
//     * Computes the best items by total amount of orders.
//     *  
//     * @return the classification
//     */
//    public List<String> bestItems(){
//        return null;
//    }
//    
//    /**
//     * Computes the most popular items by total quantity ordered.
//     *  
//     * @return the classification
//     */
//    public List<String> popularItems(){
//        return null;
//    }

}
