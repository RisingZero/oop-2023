package university;
import java.util.HashMap;
import java.util.Arrays;

public class Course {

	private String title;
	private String teacher;
	private int code;
	
	private HashMap<Integer,Student> students;
	private int enrolledStudents = 0;
	
	private final static int maxStudentsNumber = 100;
	
	public Course(String title, String teacher, int courseCode) {
		this.title = title;
		this.teacher = teacher;
		this.code = courseCode;
		this.students = new HashMap<Integer,Student>();
	}
	
	public String getTitle() {
		return this.title;
	}
	
	public String getTeacher() {
		return this.teacher;
	}
	
	public int getCode() {
		return this.code;
	}
	
	public void registerStudent(int studentId, Student student) {
		if (this.enrolledStudents < maxStudentsNumber) {
			this.students.put(studentId, student);
			this.enrolledStudents++;
		} else {
			System.out.println("Max number of students reached!");
		}
	}
	
	public Integer[] getStudentsIds() {
		Object[] objArray = this.students.keySet().toArray();
		return Arrays.copyOf(objArray, objArray.length, Integer[].class);
	}
	
	public double getAverage() {
		int sum = 0;
		int n = 0;
		
		if (this.enrolledStudents == 0)
			return -1;
		
		int studentGrade;
		for (Student student: this.students.values()) {
			studentGrade = student.getCourseGrade(this.code);
			if (studentGrade >= 0) {
				sum += studentGrade;
				n++;
			}
		}
		
		return (n > 0 ? (double)sum/(double)n : -1);
	}
	
}
