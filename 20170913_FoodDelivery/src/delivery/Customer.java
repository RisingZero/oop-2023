package delivery;

public class Customer implements Comparable<Customer> {

	private int id;
	private String name;
	private String email;
	private String phone;
	private String address;
	
	public Customer(int id, String name, String address, String phone, String email) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.phone = phone;
		this.address = address;
	}
	
	public int getId() { return id; }
	
	public String getName() { return name; } 
	
	public String getEmail() { return email; }
	
	public String getPhone() { return phone; }
	
	public String getAddress() { return address; }
	
	@Override
	public String toString() {
		return String.format("%s, %s, %s, %s",
				name,
				address,
				phone,
				email
			);
	}
	
	@Override
	public int compareTo(Customer oth) {
		return this.name.compareTo(oth.name);
	}
	
}
