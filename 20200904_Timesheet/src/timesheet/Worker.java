package timesheet;

import java.util.random.RandomGenerator;

public class Worker {
	private String id;
	private String name;
	private String surname;
	private HourProfile profile;
	
	public Worker(String name, String surname, HourProfile profile) {
		this.id = String.valueOf(RandomGenerator.getDefault().nextLong());
		this.name = name;
		this.surname = surname;
		this.profile = profile;
	}
	
	public String getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public String getSurname() {
		return surname;
	}
	
	public String getFullName() {
		return getName() + " " + getSurname();
	}
	
	public HourProfile getHourProfile() {
		return profile;
	}
	
	@Override
	public String toString() {
		return String.format("%s %s (%s)", name, surname, profile.toString());
	}
}
