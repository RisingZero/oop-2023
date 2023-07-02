package diet;


public class Customer implements Comparable<Customer> {
	
	private String firstName;
	private String lastName;
	private String email;
	private String phone;
	
	public Customer(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
	public Customer(String firstName, String lastName, String email, String phone) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.phone = phone;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public String getEmail() {
		return email;
	}
	
	public String getPhone() {
		return phone;
	}
	
	public void SetEmail(String email) {
		this.email = email;
	}
	
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	@Override
	public String toString() {
		return firstName + " " + lastName;
	}
	
	@Override
	public int compareTo(Customer other) {
		String me = lastName + firstName;
		String ot = other.lastName + other.firstName;
		return me.compareTo(ot);
	}
	
	
}
