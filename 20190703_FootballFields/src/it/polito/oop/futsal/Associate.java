package it.polito.oop.futsal;

public class Associate {
	
	
	
	private int id;
	private String name;
	private String surname;
	private String phone;
	
	public Associate(String name, String surname, String phone, int id) {
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.phone = phone;
	}
	
	public int getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public String getSurname() {
		return surname;
	}
	
	public String getPhone() {
		return phone;
	}

}
