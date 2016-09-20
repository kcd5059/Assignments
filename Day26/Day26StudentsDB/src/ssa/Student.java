package ssa;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class Student {
	
	private int id;
	private String firstName;
	private String lastName;
	private int sat;
	private double gpa;
	private Major major = new Major();
	SQLConnection sql = null;
	
	
	public void loadMajor() {
		
		try {
			createConnection();
			String query = "select major.id,description,req_sat from major join student on major_id = major.id where student.id = " + this.id;
			ResultSet rs = sql.executeQuery(query);
			
			if(rs.next()) {
				this.setMajorId(rs.getInt("id"));
				this.setMajorDesc(rs.getString("description"));
				this.setMajorRequiredSAT(rs.getInt("req_sat"));
			}
			rs.close();
			sql.close();
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
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
		return this.major.getId();
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
		this.setMajorId(majorId);
	}
	
	public Student(String firstName, String lastName, int sat, double gpa, int majorId) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.sat = sat;
		this.gpa = gpa;
		this.setMajorId(majorId);
	}

	public Student() {}

	@Override
	public String toString() {
		return String.format("%3d %-20s %4.2f %4d", this.id, this.getFullName(), this.gpa, this.sat) + " " + this.major.toString();
	}
	
	private void createConnection() {
		try {
			Properties prop = new Properties();
			prop.load(new java.io.FileInputStream("db.properties"));
			String url = prop.getProperty("url");
			String usr = prop.getProperty("user");
			String pwd = prop.getProperty("password");
			sql = new SQLConnection(url, usr, pwd);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void setMajorId(int id) {
		this.major.setId(id);
	}
	public void setMajorDesc(String desc) {
		this.major.setDescription(desc);
	}
	public void setMajorRequiredSAT(int reqSAT) {
		this.major.setRequiredSAT(reqSAT);
	}
	
	
	
}
