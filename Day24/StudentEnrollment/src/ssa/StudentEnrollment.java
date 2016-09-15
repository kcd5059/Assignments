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
		// This one fails
//		assignMajor(193, 2);
//		assignMajor(194, 5);
//		assignMajor(195, 6);
		
		// Assign student (Adam Zapel) to classes
//		addClass(191,10101);
//		addClass(191,10102);
//		addClass(191,40311);
//		addClass(191,20201);
		
		printReport(191);
		
		

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

	public static void assignMajor(int studentId, int majorId) throws SQLException {

		int reqSat = getReqSAT(majorId);;
		int sat = getSAT(studentId);
		
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
	
	private static String getDescription(int majorId) throws SQLException {
		String description = "";

		try {
			makeConnection();
			prepStat = conn.prepareStatement("SELECT description FROM major WHERE id = ?");
			prepStat.setInt(1, majorId);
			rs = prepStat.executeQuery();

			if (rs.next())
				description = rs.getString("description");

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close();
		}

		return description;
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
		
		String description = getDescription(majorId);
		int reqSat = getReqSAT(majorId);

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

	private static int getSAT(int studentId) throws SQLException {

		int sat = 0;

		try {
			makeConnection();
			prepStat = conn.prepareStatement("SELECT sat FROM student WHERE id = ?");
			prepStat.setInt(1, studentId);
			rs = prepStat.executeQuery();

			if (rs.next())
				sat = rs.getInt("sat");

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close();
		}

		return sat;

	}

	private static int getReqSAT(int majorId) throws SQLException {

		int reqSat = 0;

		try {
			makeConnection();
			prepStat = conn.prepareStatement("SELECT req_sat FROM major WHERE id = ?");
			prepStat.setInt(1, majorId);
			rs = prepStat.executeQuery();

			if (rs.next())
				reqSat = rs.getInt("req_sat");

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close();
		}

		return reqSat;

	}
	
	private static String getFullName(int studentId) throws SQLException {
		
		String fullName = "";
		
		try {
			makeConnection();
			prepStat = conn.prepareStatement("SELECT first_name,last_name FROM student WHERE id = ?");
			prepStat.setInt(1, studentId);
			rs = prepStat.executeQuery();
			
			if (rs.next())
				fullName = rs.getString("first_name") + " " + rs.getString("last_name");
			
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		
		return fullName;
	}
	
	public static void printReport(int studentId) throws SQLException {
		
		String fullName = getFullName(studentId);
		int sat = getSAT(studentId);
		int majorId = getMajorId(studentId);
		String majorDescription = getDescription(majorId);
		int reqSAT = getReqSAT(majorId);
		ArrayList<String> classList = getClasses(studentId);
		
		System.out.println("Education System - Enrollment Process");
		System.out.println("=====================================\n");
		System.out.println("Enrolled " + fullName + " as a new student.");
		System.out.println(fullName + " has an SAT score of " + sat + ".");
		System.out.println("Assigned " + fullName + " to the " + majorDescription + " which requires a required SAT score of " + reqSAT + ".");
		System.out.println("Enrolled " + fullName + " in the following four classes:");
		
		for (String aClass : classList) {
			System.out.println(aClass);
		}
		
		
		
	}
	
	private static ArrayList<String> getClasses(int studentId) throws SQLException {
		
		ArrayList<String> classList = new ArrayList<>();
		ArrayList<Integer> classIds = new ArrayList<>();
		
		try {
			makeConnection();
			prepStat = conn.prepareStatement("SELECT class_id FROM student_class_relationship WHERE student_id = ?");
			prepStat.setInt(1, studentId);
			rs = prepStat.executeQuery();

			while(rs.next()) {
				classIds.add(rs.getInt("class_id"));
			}
			

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close();
		}
		
		for (Integer id : classIds) {
			classList.add(getClassString(id));
		}
		
		return classList;
	}
	
	private static String getClassString(int classId) throws SQLException {
		
		String classString = "";
		
		try {
			makeConnection();
			prepStat = conn.prepareStatement("SELECT subject,section FROM class WHERE id = ?");
			prepStat.setInt(1, classId);
			rs = prepStat.executeQuery();

			if (rs.next())
				classString = rs.getString("subject") + " " + rs.getString("section");

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close();
		}
		
		return classString;
	}
	
	private static int getMajorId(int studentId) throws SQLException {
		
		int majorId = 0;

		try {
			makeConnection();
			prepStat = conn.prepareStatement("SELECT major_id FROM student WHERE id = ?");
			prepStat.setInt(1, studentId);
			rs = prepStat.executeQuery();

			if (rs.next())
				majorId = rs.getInt("major_id");

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close();
		}

		return majorId;
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
