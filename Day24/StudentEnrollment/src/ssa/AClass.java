package ssa;

public class AClass {

	private int id;
	private String subject;
	private String section;
	private int instructorId;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getSection() {
		return section;
	}

	public void setSection(String section) {
		this.section = section;
	}

	public int getInstructorId() {
		return instructorId;
	}

	public void setInstructorId(int instructorId) {
		this.instructorId = instructorId;
	}

	public AClass(int id, String subject, String section, int instructorId) {
		this.id = id;
		this.subject = subject;
		this.section = section;
		this.instructorId = instructorId;
	}
	
	public AClass() {}
}
