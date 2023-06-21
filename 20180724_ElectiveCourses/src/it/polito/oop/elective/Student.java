package it.polito.oop.elective;

import java.util.Arrays;

public class Student implements Comparable<Student> {
	
	private String id;
	private double average;
	
	private Course[] coursePreferences;
	private Course courseAssigned;
	
	public Student(String id, double average) {
		this.id = id;
		this.average = average;
		this.coursePreferences = new Course[3];
	}
	
	public String getId() {
		return id;
	}
	
	public double getAverage() {
		return average;
	}
	
	public void setAverage(double average) {
		this.average = average;
	}
	
	public Course getPreference(int i) {
		return coursePreferences[i];
	}
	
	public Student setPreference(Course c, int i) {
		coursePreferences[i] = c;
		return this;
	}
	
	public void assignCourse(Course c) {
		this.courseAssigned = c;
	}
	
	public int getAssignedPreference() {
		if (courseAssigned == null)
			return -1;
		return Arrays.asList(coursePreferences).indexOf(courseAssigned) + 1;
	}
	
	public int compareTo(Student oth) {
		return Double.valueOf(oth.average).compareTo(this.average);
	}

}
