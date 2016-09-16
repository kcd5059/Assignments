package ssa;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

public class StudentEnrollment {

	static Connection conn = null;
	static PreparedStatement prepStat = null;
	static ResultSet rs = null;

	public static void main(String[] args) throws SQLException {

		// Enroll each student
		// enrollStudent("Adam","Zapel",1200,3.0);
		// enrollStudent("Graham","Krakir",500,2.5);
		// enrollStudent("Ella","Vader",800,3.0);
		// enrollStudent("Stanley","Kupp",1350,3.3);
		// enrollStudent("Lou","Zar",950,3.0);
		
		// Assign major to student
//		assignMajor(191, 3);
//		assignMajor(192, 7);
		//assignMajor(193, 2); //This fails because her SAT score is too low
//		assignMajor(194, 5);
//		assignMajor(195, 6);
		
		// Assign student (Adam Zapel) to classes then print report
//		addClass(191,10101);
//		addClass(191,10102);
//		addClass(191,40311);
//		addClass(191,20201);
		printReport(191);
		
//		addClass(192,10101);
//		addClass(192, 10101);
//		addClass(192, 30101);
//		addClass(192, 40311);
		printReport(192);
		
//		addClass(193, 10101);
//		addClass(193, 10102);
//		addClass(193, 20401);
//		addClass(193, 40311);
		printReport(193);
		
//		addClass(194, 10101);
//		addClass(194, 10102);
//		addClass(194, 20401);
//		addClass(194, 40311);
		printReport(194);
		
//		addClass(195, 10101);
//		addClass(195, 10102);
//		addClass(195, 60221);
//		addClass(195, 30101);
		printReport(195);
		
		}

	private static void makeConnection() {

		try {
			Properties props = new Properties();
			props.load(new FileInputStream("db.properties"));
			String username = props.getProperty("user");
			String password = props.getProperty("password");
			String dbName = props.getProperty("dbname");
			String url = "jdbc:mysql://localhost:3306/" + dbName + "?autoReconnect=true&useSSL=false";
			conn = DriverManager.getConnection(url, username, password);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void enrollStudent(String fName, String lName, int sat, double gpa) throws SQLException {
		
		try {
			makeConnection();
			prepStat = conn.prepareStatement("INSERT student (first_name,last_name,sat,gpa) values (?,?,?,?)");

			prepStat.setString(1, fName);
			prepStat.setString(2, lName);
			prepStat.setInt(3, sat);
			prepStat.setDouble(4, gpa);

			prepStat.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}

	}
	
	public static Student getStudent(int studentId) throws SQLException {
		
		Student student = new Student();
		makeConnection();
		
		try {
			prepStat = conn.prepareStatement("SELECT * FROM student WHERE id = ?");
			prepStat.setInt(1, studentId);
			rs = prepStat.executeQuery();
			
			while(rs.next()) {
				student = new Student(rs.getInt("id"), rs.getString("first_name"), rs.getString("last_name"), rs.getInt("sat"),
						rs.getDouble("gpa"), rs.getInt("major_id"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close();
		}
		
		return student;
		
	}
	
	public static Major getMajor(int majorId) throws SQLException {
		
		Major major = new Major();
		makeConnection();
		
		try {
			prepStat = conn.prepareStatement("SELECT * FROM major WHERE id = ?");
			prepStat.setInt(1, majorId);
			rs = prepStat.executeQuery();
			
			while(rs.next()) {
				major = new Major(rs.getInt("id"), rs.getString("description"), rs.getInt("req_sat"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close();
		}
		
		return major;
	}

	public static void assignMajor(int studentId, int majorId) throws SQLException {
		Student student = getStudent(studentId);
		Major major = getMajor(majorId);
		
		int reqSat = major.getRequiredSAT();
		int sat = student.getSAT();
		
		if (sat < reqSat) {
    		printValidMajors(sat, majorId);
    	} else {
    		updateMajor(studentId, majorId);
    	}
				    
	}
	
	public static void addClass(int studentId, int classId) throws SQLException {
		
		try {
			makeConnection();
			prepStat = conn.prepareStatement("INSERT student_class_relationship (student_id,class_id) values (?,?)");
			prepStat.setInt(1, studentId);
			prepStat.setInt(2, classId);
			
			prepStat.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
	}
	

	private static void updateMajor(int studentId, int majorId) throws SQLException {
		
		try {
			makeConnection();
			
			prepStat = conn.prepareStatement("UPDATE student set major_id = ? WHERE id = ?");
			prepStat.setInt(1, majorId);
			prepStat.setInt(2, studentId);
			prepStat.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
	}

	public static void printValidMajors(int sat, int majorId) throws SQLException {
		
		Major major = getMajor(majorId);
		
		String description = major.getDescription();
		int reqSat = major.getRequiredSAT();

		System.out.println("Sorry, but " + description + " requires a SAT of " + reqSat + ".");
		System.out.println("With a SAT score of " + sat + " you may choose from the following majors:");
		try {
			makeConnection();
			prepStat = conn.prepareStatement("SELECT * FROM major WHERE req_sat <= ?");
			prepStat.setInt(1, sat);
			rs = prepStat.executeQuery();

			while (rs.next()) {
				System.out.println("* " + rs.getString("description") + " (" + rs.getInt("req_sat") + ")");
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close();
		}
	}

	
	public static void printReport(int studentId) throws SQLException {
		
		Student student = getStudent(studentId);
		Major major = getMajor(student.getMajorId());
		String majorDescription = major.getDescription();
		int reqSAT = major.getRequiredSAT();
		ArrayList<AClass> classList = getClasses(studentId);
		
		System.out.println("Education System - Enrollment Process");
		System.out.println("=====================================\n");
		System.out.println("Enrolled " + student.getFullName() + " as a new student.");
		System.out.println(student.getFullName() + " has an SAT score of " + student.getSAT() + ".");
		if (majorDescription == null) {
			System.out.println(student.getFirstName() + " has NOT been assigned to a major.");
		} else {
			System.out.println("Assigned " + student.getFullName() + " to the " + majorDescription +
					" major which requires an SAT score of " + reqSAT + ".");
		}
		System.out.println("Enrolled " + student.getFullName() + " in the following four classes:");
		
		for (AClass aClass : classList) {
			String classTitle = aClass.getSubject() + " " + aClass.getSection();
			int classId = aClass.getId();
			
			System.out.print(classTitle);
			if (isRequiredClass(classId,student.getMajorId())) {
				System.out.println(" required for major");
			} else {
				System.out.println(" elective (not required for major)");
			}
		}
	}
	
	private static ArrayList<AClass> getClasses(int studentId) throws SQLException {
		
		ArrayList<AClass> classList = new ArrayList<>();
		ArrayList<Integer> classIds = new ArrayList<>();
		
		try {
			makeConnection();
			prepStat = conn.prepareStatement("SELECT class_id FROM student_class_relationship WHERE student_id = ?");
			prepStat.setInt(1, studentId);
			rs = prepStat.executeQuery();

			// Collect classIds
			while(rs.next()) {
				classIds.add(rs.getInt("class_id"));
			}
			
			// Cycle through classIds and collect details of each class
			for (int classId : classIds) {
				prepStat = conn.prepareStatement("SELECT * FROM class where id = ?");
				prepStat.setInt(1, classId);
				
				rs = prepStat.executeQuery();
				while (rs.next()) {
					classList.add(new AClass(rs.getInt("id"), rs.getString("subject"), rs.getString("section"), rs.getInt("instructor_id")));
				}
				
			}
			

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close();
		}
		
		return classList;
	}
	
	
	private static boolean isRequiredClass(int classId, int majorId) throws SQLException {
		
		boolean isRequired = false;
		try {
			makeConnection();
			prepStat = conn.prepareStatement("SELECT * FROM major_class_relationship WHERE class_id = ? AND major_id = ?");
			prepStat.setInt(1, classId);
			prepStat.setInt(2, majorId);
			
			rs = prepStat.executeQuery();
			
			if(rs.next())
				isRequired = true;
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		
				
		return isRequired;
	}
	

	private static void close() throws SQLException {

		if (conn != null)
			conn.close();
		if (prepStat != null)
			prepStat.close();
		if (rs != null)
			rs.close();
	}

}
