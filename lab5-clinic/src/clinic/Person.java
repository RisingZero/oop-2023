package clinic;

public class Person {
	
	protected String name;
	protected String surname;
	protected String SSN;
	
	public Person(String name, String surname, String SSN) {
		this.name = name;
		this.surname = surname;
		this.SSN = SSN;
	}
	
	public String getName() {
		return name;
	}
	
	public String getSurname() {
		return surname;
	}
	
	public String getSSN() {
		return SSN;
	}
	
	@Override
	public String toString() {
		return String.format("%s %s (%s)", surname, name, SSN);
	}

}
