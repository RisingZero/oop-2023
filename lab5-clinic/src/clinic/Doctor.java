package clinic;

import java.util.*;

public class Doctor extends Person implements Comparable<Doctor> {
	
	private int badgeID;
	private String specialization;
	private Set<Patient> patients = new HashSet<Patient>();
	
	public Doctor(String name, String surname, String SSN, int badgeID, String specialization) {
		super(name, surname, SSN);
		this.badgeID = badgeID;
		this.specialization = specialization;
	}
	
	public int getId() {
		return badgeID;
	}
	
	public String getSpecialization() {
		return specialization;
	}
	
	public void addPatient(Patient p) {
		patients.add(p);
	}
	
	public Collection<Patient> getPatients() {
		return patients;
	}
		
	@Override
	public String toString() {
		return String.format("%s [%d]: %s", super.toString(), badgeID, specialization);
	}
	
	@Override
	public int compareTo(Doctor other) {
		String me = this.surname + this.name;
		String oth = other.surname + other.name;
		
		return me.compareTo(oth);
	}

}
