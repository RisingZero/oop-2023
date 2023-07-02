package social;

import java.util.*;

public class Person {
	
	private String code;
	private String firstName;
	private String lastName;
	
	private Set<Person> friends = new HashSet<Person>();
	
	public Person(String code, String firstName, String lastName) {
		this.code = code;
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
	public String getCode() {
		return code;
	}
	
	public boolean addFriend(Person f) {
		return friends.add(f);
	}
	
	public Collection<Person> getFriends() {
		return this.friends;
	}
	
	@Override
	public String toString() {
		return String.format("%s %s %s", code, firstName, lastName);
	}
	
	@Override
	public boolean equals(Object oth) {
		return this.code.equals(((Person)oth).code);
	}

}
