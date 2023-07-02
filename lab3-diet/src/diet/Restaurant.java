package diet;

import java.util.HashSet;
import java.util.TreeSet;
import java.util.ArrayList;

import diet.Order.OrderStatus;

/**
 * Represents a restaurant class with given opening times and a set of menus.
 */
public class Restaurant {
	
	private String name;
	private HashSet<Menu> menus = new HashSet<Menu>();
	private ArrayList<String> openingHours = new ArrayList<String>();
	private TreeSet<Order> orders = new TreeSet<Order>();
	
	public Restaurant(String name) {
		this.name = name;
	}
	
	/**
	 * retrieves the name of the restaurant.
	 *
	 * @return name of the restaurant
	 */
	public String getName() {
		return name;
	}

	/**
	 * Define opening times.
	 * Accepts an array of strings (even number of elements) in the format {@code "HH:MM"},
	 * so that the closing hours follow the opening hours
	 * (e.g., for a restaurant opened from 8:15 until 14:00 and from 19:00 until 00:00,
	 * arguments would be {@code "08:15", "14:00", "19:00", "00:00"}).
	 *
	 * @param hm sequence of opening and closing times
	 */
	public void setHours(String ... hm) {
		for (String h: hm) {
			String padH = h.length() == 3 ? "0" + h : h;
			openingHours.add(padH);
		}
	}

	/**
	 * Checks whether the restaurant is open at the given time.
	 *
	 * @param time time to check
	 * @return {@code true} is the restaurant is open at that time
	 */
	public boolean isOpenAt(String time) {
		time = time.length() == 4 ? "0" + time : time;
		for (int i = 0; i < openingHours.size(); i += 2) {
			String oTime = openingHours.get(i);
			String cTime = openingHours.get(i+1);
			
			if (time.compareTo(oTime) >= 0 && time.compareTo(cTime) < 0) {
				return true;
			}
		}
		return false;
	}
	
	public String getNextOpeningTime(String time) {
		String nextTime = "";
		time = time.length() == 4 ? "0" + time : time;
		
		for (int i = 0; i < openingHours.size(); i++) {
			if (time.compareTo(openingHours.get(i)) > 0) {
				nextTime = openingHours.get((i + (i+1)%2 +1) % openingHours.size());
			}
		}
		
		return nextTime;
		
	}

	/**
	 * Adds a menu to the list of menus offered by the restaurant
	 *
	 * @param menu	the menu
	 */
	public void addMenu(Menu menu) {
		menus.add(menu);
	}

	/**
	 * Gets the restaurant menu with the given name
	 *
	 * @param name	name of the required menu
	 * @return menu with the given name
	 */
	public Menu getMenu(String name) {
		Menu menu = null;
		for (Menu m: menus) {
			if (m.getName() == name)
				menu = m; break;
		}
		return menu;
	}
	
	public void addOrder(Order order) {
		orders.add(order);
	}

	/**
	 * Retrieve all order with a given status with all the relative details in text format.
	 *
	 * @param status the status to be matched
	 * @return textual representation of orders
	 */
	public String ordersWithStatus(OrderStatus status) {
		StringBuilder o = new StringBuilder("");

		for (Order or: orders) {
			if (or.getStatus() == status) {
				o.append(or.toString());
			}
		}
		
		return o.toString();
	}
}
