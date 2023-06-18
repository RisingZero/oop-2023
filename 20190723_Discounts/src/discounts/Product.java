package discounts;

public class Product implements Comparable<Product> {
	
	private String code;
	private Category category;
	private double price;
	
	public Product(String code, Category category, double price) {
		this.code = code;
		this.category = category;
		this.price = price;
	}
	
	public String getCode() {
		return code;
	}
	
	public Category getCategory() {
		return category;
	}
	
	public double getPrice() {
		return price;
	}
	
	public double getDiscountedPrice() {
		return price - price * category.getDiscount() / 100;
	}
	
	@Override
	public boolean equals(Object o) {
		Product oth = (Product)o;
		return this.code.equals(oth.code);
	}
	
	@Override
	public int compareTo(Product oth) {
		return this.code.compareTo(oth.code);
	}

}
