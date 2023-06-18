package discounts;

public class Category {
	
	private static final int MIN_DISCOUNT = 0;
	private static final int MAX_DISCOUNT = 50;

	private String code;
	private int discount = 0;
	
	public Category(String code) {
		this.code = code;
	}
	
	public void setDiscount(int discount) throws DiscountsException {
		if (discount < MIN_DISCOUNT || discount > MAX_DISCOUNT)
			throw new DiscountsException();
		
		this.discount = discount;
	}
	
	public int getDiscount() {
		return discount;
	}
	
	@Override
	public boolean equals(Object o) {
		Category c = (Category)o;
		return c.code.equals(this.code);
	}
}
