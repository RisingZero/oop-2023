package discounts;

import java.util.*;
import static java.util.stream.Collectors.*;

public class Purchase {
	
	private int id;
	private List<Product> products = new ArrayList<>();
	private List<Integer> productQuantities = new ArrayList<>();
	
	private int card = -1;
	
	public Purchase(int id, List<Product> products, List<Integer> quantities, int cardId) {
		this.id = id;
		this.products = products;
		this.productQuantities = quantities;
		this.card = cardId;
	}
	
	public Purchase(int id, List<Product> products, List<Integer> quantities) {
		this.id = id;
		this.products = products;
		this.productQuantities = quantities;
	}
	
	public int getId() {
		return id;
	}
	
	public int getCard() {
		return card;
	}
	
	public boolean hasCard() {
		return card != -1;
	}
	
	public double getTotAmount() {
		double amount = 0;
		for (int i = 0; i < products.size(); i++) {
			amount += products.get(i).getPrice() * productQuantities.get(i);
		}
		return amount;
	}
	
	public double getDiscountedAmount() {
		if (card == -1)
			return this.getTotAmount();
		
		double amount = 0;
		for (int i = 0; i < products.size(); i++) {
			amount += products.get(i).getDiscountedPrice() * productQuantities.get(i);
		}
		return amount;
	}
	
	public double getDiscountQuantity() {
		return this.getTotAmount() - this.getDiscountedAmount();
	}
	
	public int getUnits() {
		return productQuantities.stream().collect(
			summingInt(Integer::intValue)
		);
	}
	
	public int getProductUnits(Product p) {
		int idx = products.indexOf(p);
		
		if (idx == -1)
			return 0;
		else
			return productQuantities.get(idx);
	}
}