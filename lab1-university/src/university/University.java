package university;
import java.util.logging.Logger;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * This class represents a university education system.
 * 
 * It manages students and courses.
 *
 */
public class University {
	
	private String name;
	private String rector;
	
	private Student[] students;
	private int enrolledStudents = 0;
	
	private Course[] courses;
	private int activeCourses = 0;
	
	private final static int baseStudentId = 10000, baseCourseId = 10;
	

// R1
	/**
	 * Constructor
	 * @param name name of the university
	 */
	public University(String name) {
		this.name = name;
		this.students = new Student[1000];
		this.courses = new Course[50];
		logger.info("Creating new University object");
	}
	
	/**
	 * Getter for the name of the university
	 * 
	 * @return name of university
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Defines the rector for the university
	 * 
	 * @param first first name of the rector
	 * @param last	last name of the rector
	 */
	public void setRector(String first, String last) {
		this.rector = String.join(" ", first, last);
	}
	
	/**
	 * Retrieves the rector of the university
	 * 
	 * @return name of the rector
	 */
	public String getRector() {
		return this.rector;
	}
	
// R2
	/**
	 * Enroll a student in the university
	 * 
	 * @param first first name of the student
	 * @param last last name of the student
	 * 
	 * @return unique ID of the newly enrolled student
	 */
	public int enroll(String first, String last) {
		this.students[this.enrolledStudents] = new Student(first, last, baseStudentId + this.enrolledStudents);
		logger.info("New student enrolled: " + this.student(baseStudentId + this.enrolledStudents));
		return baseStudentId + this.enrolledStudents++;
	}
	
	/**
	 * Retrieves the information for a given student
	 * 
	 * @param id the ID of the student
	 * 
	 * @return information about the student
	 */
	public String student(int id) {
		Student student = this.students[id - baseStudentId];
		return String.join(" ", String.valueOf(id), student.getFullName());
	}
	
// R3
	/**
	 * Activates a new course with the given teacher
	 * 
	 * @param title title of the course
	 * @param teacher name of the teacher
	 * 
	 * @return the unique code assigned to the course
	 */
	public int activate(String title, String teacher) {
		this.courses[this.activeCourses] = new Course(title, teacher, baseCourseId + this.activeCourses);
		logger.info("New course activated: " + this.course(baseCourseId + this.activeCourses));
		return baseCourseId + this.activeCourses++;
	}
	
	/**
	 * Retrieve the information for a given course.
	 * 
	 * The course information is formatted as a string containing 
	 * code, title, and teacher separated by commas, 
	 * e.g., {@code "10,Object Oriented Programming,James Gosling"}.
	 * 
	 * @param code unique code of the course
	 * 
	 * @return information about the course
	 */
	public String course(int code) {
		Course course = this.courses[code - baseCourseId];
		return String.join(",", String.valueOf(code), course.getTitle(), course.getTeacher());
	}
	
// R4
	/**
	 * Register a student to attend a course
	 * @param studentID id of the student
	 * @param courseCode id of the course
	 */
	public void register(int studentID, int courseCode) {
		Student student = this.students[studentID - baseStudentId];
		Course course = this.courses[courseCode - baseCourseId];
		
		student.registerCourse(courseCode, course);
		course.registerStudent(studentID, student);
		logger.info("Student " + studentID + " signed up for course " + courseCode);
	}
	
	/**
	 * Retrieve a list of attendees
	 * 
	 * @param courseCode unique id of the course
	 * @return list of attendees separated by "\n"
	 */
	public String listAttendees(int courseCode) {
		Integer[] courseStudents = this.courses[courseCode - baseCourseId].getStudentsIds();
		StringBuilder outList = new StringBuilder();
	
		for (int i = 0; i < courseStudents.length; i++) {
			outList.append(this.student(courseStudents[i]) + "\n");
		}
		
		return outList.toString();
	}

	/**
	 * Retrieves the study plan for a student.
	 * 
	 * The study plan is reported as a string having
	 * one course per line (i.e. separated by '\n').
	 * The courses are formatted as describe in method {@link #course}
	 * 
	 * @param studentID id of the student
	 * 
	 * @return the list of courses the student is registered for
	 */
	public String studyPlan(int studentID) {
		Integer[] studentCourses = this.students[studentID - baseStudentId].getCoursesIds();
		StringBuilder outList = new StringBuilder();
		
		for (int i = 0; i < studentCourses.length; i++) {
			outList.append(this.course(studentCourses[i]) + "\n");
		}
		
		return outList.toString();
	}

// R5
	/**
	 * records the grade (integer 0-30) for an exam can 
	 * 
	 * @param studentId the ID of the student
	 * @param courseID	course code 
	 * @param grade		grade ( 0-30)
	 */
	public void exam(int studentId, int courseID, int grade) {
		Student student = this.students[studentId - baseStudentId];
		
		student.setCourseGrade(courseID, grade);
		logger.info("Student " + studentId + " took an exam in course " + courseID + " with grade " + grade);
	}

	/**
	 * Computes the average grade for a student and formats it as a string
	 * using the following format 
	 * 
	 * {@code "Student STUDENT_ID : AVG_GRADE"}. 
	 * 
	 * If the student has no exam recorded the method
	 * returns {@code "Student STUDENT_ID hasn't taken any exams"}.
	 * 
	 * @param studentId the ID of the student
	 * @return the average grade formatted as a string.
	 */
	public String studentAvg(int studentId) {
		Student student = this.students[studentId - baseStudentId];
		StringBuilder outString = new StringBuilder("Student " + studentId + " ");
		double avg = student.getAverage();
		
		if (avg >= 0) {
			outString.append(": " + avg);
		} else {
			outString.append("hasn't taken any exams");
		}
		return outString.toString();
	}
	
	/**
	 * Computes the average grades of all students that took the exam for a given course.
	 * 
	 * The format is the following: 
	 * {@code "The average for the course COURSE_TITLE is: COURSE_AVG"}.
	 * 
	 * If no student took the exam for that course it returns {@code "No student has taken the exam in COURSE_TITLE"}.
	 * 
	 * @param courseId	course code 
	 * @return the course average formatted as a string
	 */
	public String courseAvg(int courseId) {
		Course course = this.courses[courseId - baseCourseId];
		double avg = course.getAverage();
		
		if (avg >= 0) {
			return "The average for the course " + course.getTitle() + " is: " + avg;
		} else {
			return "No student has taken the exam in " + course.getTitle();
		}
	}
	

// R6
	/**
	 * Retrieve information for the best students to award a price.
	 * 
	 * The students' score is evaluated as the average grade of the exams they've taken. 
	 * To take into account the number of exams taken and not only the grades, 
	 * a special bonus is assigned on top of the average grade: 
	 * the number of taken exams divided by the number of courses the student is enrolled to, multiplied by 10.
	 * The bonus is added to the exam average to compute the student score.
	 * 
	 * The method returns a string with the information about the three students with the highest score. 
	 * The students appear one per row (rows are terminated by a new-line character {@code '\n'}) 
	 * and each one of them is formatted as: {@code "STUDENT_FIRSTNAME STUDENT_LASTNAME : SCORE"}.
	 * 
	 * @return info on the best three students.
	 */
	public String topThreeStudents() {
		double score;
		ArrayList<Student> sts = new ArrayList<Student>(Arrays.asList(this.students).subList(0, this.enrolledStudents));
		sts.sort(Student.comparator);
		
		StringBuilder outList = new StringBuilder();
		for (int i = 0; i < 3 && i < this.enrolledStudents; i++) {
			score = sts.get(i).getScore();
			if (score > 0)
				outList.append(sts.get(i).getFullName()  +  " : " + score + "\n");
		}
		
		return outList.toString();
	}

// R7
    /**
     * This field points to the logger for the class that can be used
     * throughout the methods to log the activities.
     */
    private final static Logger logger = Logger.getLogger("University");

}
