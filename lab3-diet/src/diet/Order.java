package diet;

import java.util.TreeMap;

/**
 * Represents and order issued by an {@link Customer} for a {@link Restaurant}.
 *
 * When an order is printed to a string is should look like:
 * <pre>
 *  RESTAURANT_NAME, USER_FIRST_NAME USER_LAST_NAME : DELIVERY(HH:MM):
 *  	MENU_NAME_1->MENU_QUANTITY_1
 *  	...
 *  	MENU_NAME_k->MENU_QUANTITY_k
 * </pre>
 */
public class Order implements Comparable<Order> {

	/**
	 * Possible order statuses
	 */
	public enum OrderStatus {
		ORDERED, READY, DELIVERED
	}

	/**
	 * Accepted payment methods
	 */
	public enum PaymentMethod {
		PAID, CASH, CARD
	}
	
	private Customer customer;
	private String restaurant;
	private String time;
	private OrderStatus status = OrderStatus.ORDERED;
	private PaymentMethod paymentMethod = PaymentMethod.CASH;
	
	TreeMap<String,Integer> menus = new TreeMap<String,Integer>();
	
	public Order(Customer customer, String restaurant, String time) {
		this.customer = customer;
		this.restaurant = restaurant;
		this.time = time.length() == 4 ? "0" + time : time;
	}

	/**
	 * Set payment method
	 * @param pm the payment method
	 */
	public void setPaymentMethod(PaymentMethod pm) {
		this.paymentMethod = pm;
	}

	/**
	 * Retrieves current payment method
	 * @return the current method
	 */
	public PaymentMethod getPaymentMethod() {
		return paymentMethod;
	}

	/**
	 * Set the new status for the order
	 * @param os new status
	 */
	public void setStatus(OrderStatus os) {
		this.status = os;
	}

	/**
	 * Retrieves the current status of the order
	 *
	 * @return current status
	 */
	public OrderStatus getStatus() {
		return status;
	}

	/**
	 * Add a new menu to the order with a given quantity
	 *
	 * @param menu	menu to be added
	 * @param quantity quantity
	 * @return the order itself (allows method chaining)
	 */
	public Order addMenus(String menu, int quantity) {
		
		menus.put(menu, quantity);
		
		return this;
	}
	
	@Override
	public String toString() {
		StringBuilder o = new StringBuilder("");
		
		o
			.append(restaurant)
			.append(", ")
			.append(customer.toString())
			.append(" : (")
			.append(time)
			.append("):\n");
		
		for (String m: menus.keySet()) {
			o
				.append("\t")
				.append(m)
				.append("->")
				.append(menus.get(m))
				.append("\n");
		}
		
		return o.toString();
	}
	
	@Override
	public int compareTo(Order other) {
		String me = this.restaurant + customer.toString() + this.time;
		String ot = other.restaurant + other.customer.toString() + other.time;
		
		return me.compareTo(ot);
	}
	
}
