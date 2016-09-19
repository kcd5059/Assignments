package ssa;

public class Student {
	
	private int id;
	private String firstName;
	private String lastName;
	private int sat;
	private double gpa;
	private int majorId;
	
	
	public String getFullName() {
		return firstName + " " + lastName;
	}
	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public int getSAT() {
		return sat;
	}

	public void setSAT(int sat) {
		this.sat = sat;
	}

	public double getGPA() {
		return gpa;
	}

	public void setGPA(double gpa) {
		this.gpa = gpa;
	}

	public int getMajorId() {
		return majorId;
	}

	public void setMajorId(int majorId) {
		this.majorId = majorId;
	}

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public Student(int id, String firstName, String lastName, int sat, double gpa, int majorId) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.sat = sat;
		this.gpa = gpa;
		this.majorId = majorId;
	}
	
	public Student(String firstName, String lastName, int sat, double gpa, int majorId) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.sat = sat;
		this.gpa = gpa;
		this.majorId = majorId;
	}

	public Student() {}

	@Override
	public String toString() {
		return String.format("%3d %-20s %4.2f %4d", this.id, this.getFullName(), this.gpa, this.sat);
	}
	
	
	
}
