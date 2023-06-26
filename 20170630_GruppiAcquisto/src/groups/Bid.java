package groups;

public class Bid implements Comparable<Bid> {
	
	private Group group;
	private String supplier;
	private int price;
	private int votes;
	
	public Bid(Group group, String supplier, int price) throws GroupHandlingException {
		if (!group.getSuppliers().contains(supplier))
			throw new GroupHandlingException("The supplier is not a supplier for this group");
		
		this.group = group;
		this.supplier = supplier;
		this.price = price;
	}
	
	public Group getGroup() { return group; }
	public String getSupplier () { return supplier; }
	public int getPrice() { return price; }
	public int getVotes() { return votes; }
	
	public void vote() {
		this.votes++;
	}
	
	public String getSupplierVotes() {
		return String.format("%s:%d",this.getSupplier(),this.getVotes());
	}
	
	@Override
	public String toString() {
		return String.format("%s:%d", supplier, price);
	}
	
	@Override
	public int compareTo(Bid oth) {
		if (this.price == oth.price)
			return this.supplier.compareTo(oth.supplier);
		else
			return this.price - oth.price;
	}

}
