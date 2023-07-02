package university;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Comparator;

public class Student {
	
	private String name;
	private String surname;
	private int id;
	
	private HashMap<Integer,Course> courses;
	private HashMap<Integer,Integer> grades;
	private int activeCourses = 0;
	
	private final static int maxCoursesNumber = 25;
	
	public final static Comparator<Student> comparator = new Comparator<Student>() {
		public int compare(Student s1, Student s2) {
			double s1Score = s1.getScore();
			double s2Score = s2.getScore();
			
			if (s1Score >= s2Score)
				return (s1Score == s2Score) ? 0 : -1;
			else
				return 1;
		}
	};
	
	public Student(String name, String surname, int studentId) {
		this.name = name;
		this.surname = surname;
		this.id = studentId;
		this.courses = new HashMap<Integer,Course>();
		this.grades = new HashMap<Integer,Integer>();
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getSurname() {
		return this.surname;
	}
	
	public String getFullName() {
		return String.join(" ", this.name, this.surname);
	}
	
	public int getId() {
		return this.id;
	}
	
	public void registerCourse(int courseCode, Course course) {
		if (this.activeCourses < maxCoursesNumber) { 
			this.courses.put(courseCode, course);
			this.activeCourses++;
		} else {
			System.out.println("Max number of courses reached!");
		}
	}
	
	public Integer[] getCoursesIds() {
		Object[] objArray = this.courses.keySet().toArray();
		return Arrays.copyOf(objArray, objArray.length, Integer[].class);
	}
	
	public void setCourseGrade(int courseCode, int grade) {
		this.grades.put(courseCode, grade);
	}
	
	public int getCourseGrade(int courseCode) {
		if (this.grades.containsKey(courseCode))
			return this.grades.get(courseCode);
		else
			return -1;
	}
	
	public double getAverage() {
		int sum = 0;
		int n = 0;
		
		if (this.activeCourses == 0)
			return -1;
		
		for (Integer grade: this.grades.values()) {
			if (grade != -1) {
				sum += grade;
				n++;
			}
		}
		
		return (n > 0 ? (double)sum/(double)n : -1);
	}
	
	public double getScore() {
		int takenExams = this.grades.size();
		double avg = this.getAverage();
		
		if (avg == -1)
			return -1;
		
		return avg + 10 * (double)takenExams/(double)this.activeCourses;
	}
}