package groups;

import java.util.*;

public class Group implements Comparable<Group> {
	
	private String name;
	private ProductType productType;
	private List<String> customers = new ArrayList<String>();
	private List<String> suppliers = new ArrayList<String>();
	
	public Group(String name, ProductType productType, String... customers) {
		this.name = name;
		this.productType = productType;
		this.customers = Arrays.asList(customers);
	}
	
	public String getName() {
		return name;
	}
	
	public ProductType getProductType() {
		return productType;
	}
	
	public List<String> getCustomers() {
		return customers;
	}
	
	public List<String> getSuppliers() {
		return suppliers;
	}
	
	public void setSuppliers(List<String> suppliers) {
		this.suppliers = suppliers;
	}
	
	@Override
	public int compareTo(Group oth) {
		return this.name.compareTo(oth.name);
	}

}
