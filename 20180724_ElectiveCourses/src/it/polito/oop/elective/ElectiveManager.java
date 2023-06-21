package it.polito.oop.elective;

import java.util.*;
import static java.util.stream.Collectors.*;

/**
 * Manages elective courses enrollment.
 * 
 *
 */
public class ElectiveManager {
	
	private Map<String,Course> courses = new HashMap<>();
	private Map<String,Student> students = new HashMap<>();
	
	private List<Student> unassignedStudents;
	
	private List<Notifier> notifiers = new ArrayList<>();

    /**
     * Define a new course offer.
     * A course is characterized by a name and a number of available positions.
     * 
     * @param name : the label for the request type
     * @param availablePositions : the number of available positions
     */
    public void addCourse(String name, int availablePositions) {
        Course c = new Course(name, availablePositions);
        this.courses.put(name, c);
    }
    
    /**
     * Returns a list of all defined courses
     * @return
     */
    public SortedSet<String> getCourses(){
        return new TreeSet<String>(courses.keySet());
    }
    
    /**
     * Adds a new student info.
     * 
     * @param id : the id of the student
     * @param gradeAverage : the grade average
     */
    public void loadStudent(String id, 
                                  double gradeAverage){
        if (students.containsKey(id)) {
        	students.get(id).setAverage(gradeAverage);
        } else {
        	Student s = new Student(id, gradeAverage);
        	students.put(id, s);
        }
    }

    /**
     * Lists all the students.
     * 
     * @return : list of students ids.
     */
    public Collection<String> getStudents(){
        return students.keySet();
    }
    
    /**
     * Lists all the students with grade average in the interval.
     * 
     * @param inf : lower bound of the interval (inclusive)
     * @param sup : upper bound of the interval (inclusive)
     * @return : list of students ids.
     */
    public Collection<String> getStudents(double inf, double sup){
    	return students.values().stream()
    		.filter((Student s) -> {
    			return s.getAverage() >= inf && s.getAverage() <= sup;
    		})
    		.map(Student::getId).toList();
    }


    /**
     * Adds a new enrollment request of a student for a set of courses.
     * <p>
     * The request accepts a list of course names listed in order of priority.
     * The first in the list is the preferred one, i.e. the student's first choice.
     * 
     * @param id : the id of the student
     * @param selectedCourses : a list of of requested courses, in order of decreasing priority
     * 
     * @return : number of courses the user expressed a preference for
     * 
     * @throws ElectiveException : if the number of selected course is not in [1,3] or the id has not been defined.
     */
    public int requestEnroll(String id, List<String> courses)  throws ElectiveException {
    	if (!students.containsKey(id))
    		throw new ElectiveException();
    	
    	if (courses == null || courses.size() < 1 || courses.size() > 3)
    		throw new ElectiveException();
    	
    	if (!this.courses.keySet().containsAll(courses))
    		throw new ElectiveException();
    	
    	Student s = students.get(id);
    	
    	int i = 0;
    	for (String courseName : courses) {
    		Course c = this.courses.get(courseName);
    		s.setPreference(c, i++);
    	}
    	
    	notifiers.forEach((Notifier nf) -> {
    		nf.requestReceived(id);
    	});
    	
        return i;
    }
    
    /**
     * Returns the number of students that selected each course.
     * <p>
     * Since each course can be selected as 1st, 2nd, or 3rd choice,
     * the method reports three numbers corresponding to the
     * number of students that selected the course as i-th choice. 
     * <p>
     * In case of a course with no requests at all
     * the method reports three zeros.
     * <p>
     * 
     * @return the map of list of number of requests per course
     */
    public Map<String,List<Long>> numberRequests(){
    	return courses.values().stream()
    		.collect(
    			toMap(
    				Course::getName,
    				(Course c) -> {
    					List<Long> req = new ArrayList<>();
    					
						req.add(this.students.values().stream()
							.filter(
								(Student s) -> s.getPreference(0) == c
							).collect(counting()));
						req.add(this.students.values().stream()
								.filter(
									(Student s) -> s.getPreference(1) == c
								).collect(counting()));
						req.add(this.students.values().stream()
								.filter(
									(Student s) -> s.getPreference(2) == c
								).collect(counting()));
    					
    					return req;
    				}
    			)
    		);
    }
    
    
    /**
     * Make the definitive class assignments based on the grade averages and preferences.
     * <p>
     * Student with higher grade averages are assigned to first option courses while they fit
     * otherwise they are assigned to second and then third option courses.
     * <p>
     *  
     * @return the number of students that could not be assigned to one of the selected courses.
     */
    public long makeClasses() {
    	List<Student> studentsToBeAssigned = students.values().stream().sorted().toList();
    	List<Student> unassignedStudents = new ArrayList<>();
    	
    	for (Student s: studentsToBeAssigned) {
    		int i = 0;
    		while (i < 3) {
    			Course c = s.getPreference(i);
    			if (c != null && c.getAvailableSeats() > 0) {
    				c.addStudent(s);
    				s.assignCourse(c);
    				this.notifiers.forEach((Notifier nf) -> {
    					nf.assignedToCourse(s.getId(), c.getName());
    				});
    				break;
    			}
    			i++;
    		}
    		if (i == 3)
    			unassignedStudents.add(s);
    	}
    	this.unassignedStudents = unassignedStudents;
        return unassignedStudents.size();
    }
    
    
    /**
     * Returns the students assigned to each course.
     * 
     * @return the map course name vs. student id list.
     */
    public Map<String,List<String>> getAssignments(){
        return courses.values().stream()
        	.collect(
        		toMap(
        			Course::getName,
        			(Course c) -> c.getAssignedStudents().stream().map(Student::getId).toList()
        		)
        	);
    }
    
    
    /**
     * Adds a new notification listener for the announcements
     * issues by this course manager.
     * 
     * @param listener : the new notification listener
     */
    public void addNotifier(Notifier listener) {
        notifiers.add(listener);
    }
    
    /**
     * Computes the success rate w.r.t. to first 
     * (second, third) choice.
     * 
     * @param choice : the number of choice to consider.
     * @return the success rate (number between 0.0 and 1.0)
     */
    public double successRate(int choice){
    	double numberOfStudents = students.size();
    	double assignedStudents = students.values().stream()
    		.filter((Student s) -> {
    			return s.getAssignedPreference() == choice;
    		}).collect(counting());
    	
    	System.out.println(assignedStudents);
    	System.out.println(numberOfStudents);
    	
        return assignedStudents/numberOfStudents;
    }

    
    /**
     * Returns the students not assigned to any course.
     * 
     * @return the student id list.
     */
    public List<String> getNotAssigned(){
        return unassignedStudents.stream().map(Student::getId).toList();
    }
    
    
}
