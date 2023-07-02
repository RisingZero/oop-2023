package clinic;

import java.util.Optional;

public class Patient extends Person {
	
	private Doctor doctor;
	
	public Patient(String name, String surname, String SSN) {
		super(name, surname, SSN);
	}
	
	public void setDoctor(Doctor d) {
		this.doctor = d;
	}
	
	public Optional<Doctor> getDoctor() {
		return Optional.ofNullable(doctor);
	}

}
