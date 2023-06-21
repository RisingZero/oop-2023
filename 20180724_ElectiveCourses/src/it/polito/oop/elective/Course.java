package it.polito.oop.elective;

import java.util.*;

public class Course implements Comparable<Course> {
	
	private String name;
	private int seats;
	
	private List<Student> assignedStudents = new ArrayList<>();
	
	public Course(String name, int seats) {
		this.name = name;
		this.seats = seats;
	}
	
	public String getName() {
		return name;
	}
	
	public int getSeats() {
		return seats;
	}
	
	public int getAvailableSeats() {
		return seats - assignedStudents.size();
	}
	
	public List<Student> getAssignedStudents() {
		return assignedStudents.stream().sorted().toList();
	}
	
	public Course addStudent(Student s) {
		assignedStudents.add(s);
		return this;
	}
	
	@Override
	public int compareTo(Course oth) {
		return this.name.compareTo(oth.name);
	}

}
