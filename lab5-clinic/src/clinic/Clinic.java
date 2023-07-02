package clinic;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;


/**
 * Represents a clinic with patients and doctors.
 * 
 */
public class Clinic {
	
	private Map<String, Person> patients = new HashMap<String, Person>();
	private Map<Integer, Doctor> doctors = new HashMap<Integer, Doctor>();

	/**
	 * Add a new clinic patient.
	 * 
	 * @param first first name of the patient
	 * @param last last name of the patient
	 * @param ssn SSN number of the patient
	 */
	public void addPatient(String first, String last, String ssn) {
		Patient p = new Patient(first, last, ssn);
		patients.put(ssn, p);
	}


	/**
	 * Retrieves a patient information
	 * 
	 * @param ssn SSN of the patient
	 * @return the object representing the patient
	 * @throws NoSuchPatient in case of no patient with matching SSN
	 */
	public String getPatient(String ssn) throws NoSuchPatient {
		if (patients.containsKey(ssn))
			return patients.get(ssn).toString();
		else
			throw new NoSuchPatient();
	}

	/**
	 * Add a new doctor working at the clinic
	 * 
	 * @param first first name of the doctor
	 * @param last last name of the doctor
	 * @param ssn SSN number of the doctor
	 * @param docID unique ID of the doctor
	 * @param specialization doctor's specialization
	 */
	public void addDoctor(String first, String last, String ssn, int docID, String specialization) {
		Doctor d = new Doctor(first, last, ssn, docID, specialization);
		doctors.put(docID, d);
		patients.put(ssn, d);

	}

	/**
	 * Retrieves information about a doctor
	 * 
	 * @param docID ID of the doctor
	 * @return object with information about the doctor
	 * @throws NoSuchDoctor in case no doctor exists with a matching ID
	 */
	public String getDoctor(int docID) throws NoSuchDoctor {
		if (doctors.containsKey(docID))
			return doctors.get(docID).toString();
		else
			throw new NoSuchDoctor();
	}
	
	/**
	 * Assign a given doctor to a patient
	 * 
	 * @param ssn SSN of the patient
	 * @param docID ID of the doctor
	 * @throws NoSuchPatient in case of not patient with matching SSN
	 * @throws NoSuchDoctor in case no doctor exists with a matching ID
	 */
	public void assignPatientToDoctor(String ssn, int docID) throws NoSuchPatient, NoSuchDoctor {
		if (!patients.containsKey(ssn))
			throw new NoSuchPatient();
		
		if (!doctors.containsKey(docID))
			throw new NoSuchDoctor();
		
		Patient p = (Patient)patients.get(ssn);
		Doctor d = doctors.get(docID);
		
		d.addPatient(p);
		p.setDoctor(d);
	}

	/**
	 * Retrieves the id of the doctor assigned to a given patient.
	 * 
	 * @param ssn SSN of the patient
	 * @return id of the doctor
	 * @throws NoSuchPatient in case of not patient with matching SSN
	 * @throws NoSuchDoctor in case no doctor has been assigned to the patient
	 */
	public int getAssignedDoctor(String ssn) throws NoSuchPatient, NoSuchDoctor {
		if (!patients.containsKey(ssn))
			throw new NoSuchPatient();
		
		Patient p = (Patient)patients.get(ssn);
		Doctor d = p.getDoctor().orElseThrow(NoSuchDoctor::new);
		
		return d.getId();
	}
	
	/**
	 * Retrieves the patients assigned to a doctor
	 * 
	 * @param id ID of the doctor
	 * @return collection of patient SSNs
	 * @throws NoSuchDoctor in case the {@code id} does not match any doctor 
	 */
	public Collection<String> getAssignedPatients(int id) throws NoSuchDoctor {
		if (!doctors.containsKey(id))
			throw new NoSuchDoctor();
		
		Doctor d = doctors.get(id);
		
		return d.getPatients().stream()
				.collect(
						mapping(Patient::getSSN, toList())
				);
	}
	
	/**
	 * Loads data about doctors and patients from the given stream.
	 * <p>
	 * The text file is organized by rows, each row contains info about
	 * either a patient or a doctor.</p>
	 * <p>
	 * Rows containing a patient's info begin with letter {@code "P"} followed by first name,
	 * last name, and SSN. Rows containing doctor's info start with letter {@code "M"},
	 * followed by badge ID, first name, last name, SSN, and speciality.<br>
	 * The elements on a line are separated by the {@code ';'} character possibly
	 * surrounded by spaces that should be ignored.</p>
	 * <p>
	 * In case of error in the data present on a given row, the method should be able
	 * to ignore the row and skip to the next one.<br>

	 * 
	 * @param reader reader linked to the file to be read
	 * @throws IOException in case of IO error
	 */
	public int loadData(Reader reader) throws IOException {
		int readLines = 0;
		try {
			BufferedReader bf = new BufferedReader(reader);
			
			for (String line: bf.lines().toList()) {
				List<String> fieldsList = Arrays.asList(line.split("\s*;\s*")).stream()
						.map(f -> f.strip())
						.toList();
				String[] fields = fieldsList.toArray(new String[fieldsList.size()]);
;				
				switch (fields[0]) {
					case "P":
						if (fields.length == 4) {
							addPatient(fields[1], fields[2], fields[3]);
							readLines++;
						}
						break;
					case "M":
						if (fields.length == 6) {
							try {
								this.addDoctor(fields[2], fields[3], fields[4], Integer.parseInt(fields[1]), fields[5]);
								readLines++;
							} catch (Exception ex) { }
						}
						break;
					default:
						break;
				}
			}
		} catch(UncheckedIOException ex) {
			throw new IOException();
		}
		return readLines;
	}


	/**
	 * Loads data about doctors and patients from the given stream.
	 * <p>
	 * The text file is organized by rows, each row contains info about
	 * either a patient or a doctor.</p>
	 * <p>
	 * Rows containing a patient's info begin with letter {@code "P"} followed by first name,
	 * last name, and SSN. Rows containing doctor's info start with letter {@code "M"},
	 * followed by badge ID, first name, last name, SSN, and speciality.<br>
	 * The elements on a line are separated by the {@code ';'} character possibly
	 * surrounded by spaces that should be ignored.</p>
	 * <p>
	 * In case of error in the data present on a given row, the method calls the
	 * {@link ErrorListener#offending} method passing the line itself,
	 * ignores the row, and skip to the next one.<br>
	 * 
	 * @param reader reader linked to the file to be read
	 * @param listener listener used for wrong line notifications
	 * @throws IOException in case of IO error
	 */
	public int loadData(Reader reader, ErrorListener listener) throws IOException {
		int readLines = 0;
		try {
			BufferedReader bf = new BufferedReader(reader);
			
			for (String line: bf.lines().toList()) {
				String[] fields = line.split("\s*;\s*");
				
				switch (fields[0]) {
					case "P":
						if (fields.length == 4) {
							addPatient(fields[1], fields[2], fields[3]);
							readLines++;
						} else
							listener.offending(line);
						break;
					case "M":
						if (fields.length == 6) {
							try {
								this.addDoctor(fields[2], fields[3], fields[4], Integer.parseInt(fields[1]), fields[5]);
								readLines++;
							} catch (Exception ex) { 
								listener.offending(line);
							}
						} else
							listener.offending(line);
						break;
					default:
						listener.offending(line);
						break;
				}
			}
		} catch(UncheckedIOException ex) {
			throw new IOException();
		}
		return readLines;
	}
	
	/**
	 * Retrieves the collection of doctors that have no patient at all.
	 * The doctors are returned sorted in alphabetical order
	 * 
	 * @return the collection of doctors' ids
	 */
	public Collection<Integer> idleDoctors(){
		return doctors.values().stream()
			.collect(
				filtering(d -> d.getPatients().size() == 0, toCollection(TreeSet::new))
			).stream()
			.collect(
				mapping(d -> d.getId(), toList())
			);
	}

	/**
	 * Retrieves the collection of doctors having a number of patients larger than the average.
	 * 
	 * @return  the collection of doctors' ids
	 */
	public Collection<Integer> busyDoctors(){
		double average = doctors.values().stream()
			.collect(
				Collectors.averagingInt(d -> d.getPatients().size())
			);
		
		return doctors.values().stream()
			.collect(
				filtering(d -> d.getPatients().size() > average, toCollection(TreeSet::new))
			).stream()
			.collect(
				mapping(d -> d.getId(), toList())
			);
	}

	/**
	 * Retrieves the information about doctors and relative number of assigned patients.
	 * <p>
	 * The method returns list of strings formatted as "{@code ### : ID SURNAME NAME}" where {@code ###}
	 * represent the number of patients (printed on three characters).
	 * <p>
	 * The list is sorted by decreasing number of patients.
	 * 
	 * @return the collection of strings with information about doctors and patients count
	 */
	public Collection<String> doctorsByNumPatients(){
		
		class DoctorPatientMapping implements Comparable<DoctorPatientMapping> {
			public int n;
			public Doctor d;
			
			public DoctorPatientMapping(Doctor d) {
				this.d = d;
				this.n = d.getPatients().size();
			}
			
			@Override
			public int compareTo(DoctorPatientMapping o) {
				return o.n - this.n;
			}
			
			@Override
			public String toString() {
				return String.format("%03d : %d %s %s", d.getPatients().size(), d.getId(), d.getSurname(), d.getName());
			}
		}
		
		Set<DoctorPatientMapping> dmap = new TreeSet<DoctorPatientMapping>(doctors.values().stream()
			.map(d -> new DoctorPatientMapping(d))
			.toList());
		
		return dmap.stream()
			.collect(
				mapping(d -> {
					return d.toString();
				}, toList())
			);
	}
	
	/**
	 * Retrieves the number of patients per (their doctor's)  speciality
	 * <p>
	 * The information is a collections of strings structured as {@code ### - SPECIALITY}
	 * where {@code SPECIALITY} is the name of the speciality and 
	 * {@code ###} is the number of patients cured by doctors with such speciality (printed on three characters).
	 * <p>
	 * The elements are sorted first by decreasing count and then by alphabetic speciality.
	 * 
	 * @return the collection of strings with speciality and patient count information.
	 */
	public Collection<String> countPatientsPerSpecialization(){
		
		class SpecialityPatientMapping implements Comparable<SpecialityPatientMapping> {
			public int n;
			public String s;
			
			public SpecialityPatientMapping(String s, int n) {
				this.s = s;
				this.n = n;
			}
			
			@Override
			public int compareTo(SpecialityPatientMapping o) {
				if (o.n == this.n) {
					return this.s.compareTo(o.s);
				} else {
					return o.n - this.n;
				}
			}
			
			@Override
			public String toString() {
				return String.format("%03d - %s", n, s);
			}
		}
		
		Map<String,Integer> specializationStats = doctors.values().stream()
			.collect(
				filtering(d -> d.getPatients().size() > 0, 
					groupingBy(Doctor::getSpecialization, summingInt(d -> d.getPatients().size())))
			);
		
		Set<SpecialityPatientMapping> orderedStats = new TreeSet<SpecialityPatientMapping>();
		for (String spec: specializationStats.keySet()) {
			orderedStats.add(new SpecialityPatientMapping(spec, specializationStats.get(spec)));
		}
		
		return orderedStats.stream()
			.map(d -> d.toString())
			.toList();
	}

}
