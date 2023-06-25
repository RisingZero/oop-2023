package transactions;

public class Request {
	
	private String id;
	private String place;
	private String product;
	
	public Request(String id, String place, String product) {
		this.id = id;
		this.place = place;
		this.product = product;
	}
	
	public String getId() {
		return id;
	}
	
	public String getPlace() {
		return place;
	}
	
	public String getProductId() {
		return product;
	}
	
	@Override
	public boolean equals(Object o) {
		Request oth = (Request)o;
		return this.id.equals(oth.id);
	}
	
	@Override
	public int hashCode() {
		return this.id.hashCode();
	}

}
