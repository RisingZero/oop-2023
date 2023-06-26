package groups;

import java.util.*;

public class ProductType implements Comparable<ProductType> {
	
	private String name;
	private List<String> suppliers = new ArrayList<>();
	
	public ProductType(String name, String... supplierNames) {
		this.name = name;
		this.suppliers = Arrays.asList(supplierNames);
	}
	
	public String getName() {
		return name;
	}
	
	public List<String> getSuppliers() {
		return suppliers;
	}
	
	@Override
	public int compareTo(ProductType oth)  {
		return this.name.compareTo(oth.name);
	}

}
