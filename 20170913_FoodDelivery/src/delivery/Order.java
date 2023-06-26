package delivery;

import java.util.*;
import java.util.Map.Entry;
import static java.util.stream.Collectors.*;
import delivery.Delivery.OrderStatus;

public class Order {

	private int orderId;
	private int customerId;
	private OrderStatus status = OrderStatus.NEW;
	
	public Map<MenuItem,Integer> items = new HashMap<>();
	
	public Order(int customerId, int orderId) {
		this.orderId = orderId;
		this.customerId = customerId;
	}
	
	public int getOrderId() { return orderId; }
	
	public int getCustomerId() { return customerId; }
	
	public OrderStatus getStatus() { return status; }
	
	public int addItem(MenuItem item, int quantity) {
		if (!items.containsKey(item)) {
			items.put(item, quantity);
			return quantity;
		} else {
			items.put(item, items.get(item) + quantity);
			return items.get(item);
		}
	}
	
	public List<String> summary() {
		return items.entrySet().stream()
			.map((Entry<MenuItem,Integer> e) -> {
				return String.format("%s, %d", e.getKey().getDescription(), e.getValue());
			}).toList();
	}
	
	public double totalCheck() {
		return items.entrySet().stream()
			.collect(
				summingDouble(
					(Entry<MenuItem,Integer> e) -> {
						return e.getKey().getPrice() * e.getValue();
					}
				)
			);
	}
	
	public int confirm() {
		this.status = OrderStatus.CONFIRMED;
		return 5 + items.keySet().stream().map(MenuItem::getPrepTime).max(Comparator.naturalOrder()).orElse(0) + 15;
	}
	
	public int start() {
		this.status = OrderStatus.PREPARATION;
		return items.keySet().stream().map(MenuItem::getPrepTime).max(Comparator.naturalOrder()).orElse(0) + 15;
	}
	
	public int deliver() {
		this.status = OrderStatus.ON_DELIVERY;
		return 15;
	}
	
	public void complete() {
		this.status = OrderStatus.DELIVERED;
	}
}
