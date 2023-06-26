package groups;
import java.util.*;
import java.util.Map.Entry;
import static java.util.stream.Collectors.*;


public class GroupHandling {
	
	private Map<String,ProductType> productTypes = new HashMap<>();
	private SortedSet<String> suppliers = new TreeSet<>();
	private Map<String,Group> groups = new HashMap<>();
	private List<Bid> bids = new ArrayList<>();
	
//R1	
	public void addProduct (String productTypeName, String... supplierNames) 
			throws GroupHandlingException {
		if (productTypes.containsKey(productTypeName))
			throw new GroupHandlingException("Duplicated product type name");
		
		ProductType pt = new ProductType(productTypeName, supplierNames);
		productTypes.put(productTypeName, pt);
		suppliers.addAll(Arrays.asList(supplierNames));
	}
	
	public List<String> getProductTypes (String supplierName) {
		return productTypes.values().stream().sorted()
			.filter(pt -> pt.getSuppliers().contains(supplierName))
			.map(ProductType::getName)
			.toList();
	}
	
//R2	
	public void addGroup (String name, String productName, String... customerNames) 
			throws GroupHandlingException {
		if (groups.containsKey(name))
			throw new GroupHandlingException("Duplicated group name");
		
		if (!productTypes.containsKey(productName))
			throw new GroupHandlingException("Product not defined");
		
		Group g = new Group(name, productTypes.get(productName), customerNames);
		groups.put(name,g);
	}
	
	public List<String> getGroups (String customerName) {
		return groups.values().stream().sorted()
			.filter(g -> g.getCustomers().contains(customerName))
			.map(Group::getName)
			.toList();
	}

//R3
	public void setSuppliers (String groupName, String... supplierNames)
			throws GroupHandlingException {
		if (!groups.containsKey(groupName))
			throw new GroupHandlingException("Group not defined");
		
		Group g = groups.get(groupName);
		List<String> groupSuppliers = new ArrayList<String>();
		
		for (String supplierName: supplierNames) {
			if (!suppliers.contains(supplierName))
				throw new GroupHandlingException("A supplier has not been defined");
			if (!g.getProductType().getSuppliers().contains(supplierName))
				throw new GroupHandlingException("One of the suppliers doesn't supply the group product type");
			
			groupSuppliers.add(supplierName);
		}
		
		g.setSuppliers(groupSuppliers);
	}
	
	public void addBid (String groupName, String supplierName, int price)
			throws GroupHandlingException {
		if (!groups.containsKey(groupName))
			throw new GroupHandlingException("Group has not been defined");
		
		Bid b = new Bid(groups.get(groupName), supplierName, price);
		bids.add(b);
	}
	
	public String getBids (String groupName) {
		Group g = groups.get(groupName);
		
		return String.join(",", bids.stream().sorted()
			.filter(b -> b.getGroup() == g)
			.map(Bid::toString)
			.toList());
	}
	
	
//R4	
	public void vote (String groupName, String customerName, String supplierName)
			throws GroupHandlingException {
		if (!groups.containsKey(groupName))
			throw new GroupHandlingException("Group has not been defined");
		
		if (!suppliers.contains(supplierName))
			throw new GroupHandlingException("Supplier has not been defined");
		
		Group g = groups.get(groupName);
		
		if (!g.getCustomers().contains(customerName))
			throw new GroupHandlingException("Customer is not in the group");
		
		List<Bid> supplierBids = bids.stream()
			.filter(b -> b.getGroup() == g)
			.filter(b -> b.getSupplier().equals(supplierName))
			.toList();
		
		if (supplierBids.size() == 0) 
			throw new GroupHandlingException("No bids found for group and supplier");
		
		supplierBids.get(0).vote();
	}
	
	public String  getVotes (String groupName) {
		Group g = groups.get(groupName);
		
		return String.join(",", bids.stream()
			.filter(b -> b.getGroup() == g && b.getVotes() > 0)
			.sorted((b1,b2) -> b1.getSupplier().compareTo(b2.getSupplier()))
			.map(Bid::getSupplierVotes)
			.toList());
	}
	
	public String getWinningBid (String groupName) {
		Group g = groups.get(groupName);
		
		return bids.stream()
			.filter(b -> b.getGroup() == g)
			.max((b1,b2) -> {
				if (b1.getVotes() == b2.getVotes())
					return b2.getPrice() - b1.getPrice();
				else
					return b1.getVotes() - b2.getVotes();
			}).orElseThrow().getSupplierVotes();
	}
	
//R5
	public SortedMap<String, Integer> maxPricePerProductType() { //serve toMap
		return bids.stream()
			.collect(
				groupingBy(
					b -> b.getGroup().getProductType().getName(),
					TreeMap::new,
					mapping(
						Bid::getPrice,
						collectingAndThen(maxBy(Integer::compare), o -> o.orElseThrow())
					)
				)
			);
	}
	
	public SortedMap<Integer, List<String>> suppliersPerNumberOfBids() {
		Map<String,Integer> supplierBidsCount = bids.stream()
			.collect(
				groupingBy(
					Bid::getSupplier,
					TreeMap::new,
					collectingAndThen(counting(), Long::intValue)
				)
			);
		return supplierBidsCount.entrySet().stream()
		.collect(
			groupingBy(
				Entry::getValue,
				TreeMap::new,
				mapping(Entry::getKey, toList())
			)
		).descendingMap();
	}
	
	public SortedMap<String, Long> numberOfCustomersPerProductType() {
		SortedMap<String,Long> stats = new TreeMap<>();
		
		for (ProductType pt: productTypes.values()) {
			List<String> customers =groups.values().stream().filter(g -> g.getProductType() == pt).flatMap(g -> g.getCustomers().stream()).toList();
			if (customers.size() > 0)
				stats.put(pt.getName(), Long.valueOf(customers.size()));
		}
        return stats;
	}
	
}
