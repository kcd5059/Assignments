package ssa;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

public class Students {

	SQLConnection sql = null;

	// Insert a student, given all columns EXCEPT id
	public int insert(Student student) {

		try {
			createConnection();
			String query = String.format(
					"INSERT student (first_name,last_name,sat,gpa,major_id) values ('%s','%s',%d,%f,%d)",
					student.getFirstName(), student.getLastName(), student.getSAT(), student.getGPA(),
					student.getMajorId());
			int rowsAffected = sql.executeUpdate(query);
			sql.close();
			return rowsAffected;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}

	// Update student record in DB to match student object parameter, ID must not be null
	public int update(Student newRecord) {

		int changes = 0;
		try {
			String query;
			Student originalRecord = this.getById(newRecord.getId());
			sql.close();

			if (newRecord.getFirstName() != originalRecord.getFirstName()) {
				createConnection();
				query = "UPDATE student SET first_name = '" + newRecord.getFirstName() + "' WHERE id = "
						+ newRecord.getId();
				sql.executeUpdate(query);
				changes++;
				sql.close();
			}
			if (newRecord.getLastName() != originalRecord.getLastName()) {
				createConnection();
				query = "UPDATE student SET last_name = '" + newRecord.getLastName() + "' WHERE id = "
						+ newRecord.getId();
				sql.executeUpdate(query);
				changes++;
				sql.close();
			}
			if (newRecord.getSAT() != originalRecord.getSAT()) {
				createConnection();
				query = "UPDATE student SET sat = " + newRecord.getSAT() + " WHERE id = " + newRecord.getId();
				sql.executeUpdate(query);
				changes++;
				sql.close();
			}
			if (newRecord.getGPA() != originalRecord.getGPA()) {
				createConnection();
				query = "UPDATE student SET gpa = " + newRecord.getGPA() + " WHERE id = " + newRecord.getId();
				sql.executeUpdate(query);
				changes++;
				sql.close();
			}
			if (newRecord.getMajorId() != originalRecord.getMajorId()) {
				createConnection();
				query = "UPDATE student SET major_id = " + newRecord.getMajorId() + " WHERE id = " + newRecord.getId();
				sql.executeUpdate(query);
				changes++;
				sql.close();
			}
			return changes;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}

	// Update students id, given the current id and the new id
	public int updateId(int currentId, int desiredId) {

		int rowsAffected = -1;
		try {
			createConnection();
			String query = String.format("UPDATE student SET id = %d WHERE id = %d", desiredId, currentId);

			rowsAffected = sql.executeUpdate(query);
			sql.close();
			return rowsAffected;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rowsAffected;
	}

	// Delete student record by last name
	public int deleteByLastName(String lastName) {

		try {
			createConnection();
			String query = "DELETE FROM student WHERE last_name = '" + lastName + "'";
			int rowsAffected = sql.executeUpdate(query);
			sql.close();
			return rowsAffected;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}

	// Delete student record by id
	public int deleteById(int id) {

		try {
			createConnection();
			String query = "DELETE student where id = " + id;
			int rowsAffected = sql.executeUpdate(query);
			sql.close();
			return rowsAffected;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}

	// Select student record by id
	public Student getById(int id) {
		Student student = new Student();
		try {
			createConnection();
			ResultSet rs = sql.executeQuery(String.format("SELECT * FROM student where id = %d", id));

			while (rs.next()) {
				student.setId(rs.getInt("id"));
				student.setFirstName(rs.getString("first_name"));
				student.setLastName(rs.getString("last_name"));
				student.setSAT(rs.getInt("sat"));
				student.setGPA(rs.getInt("gpa"));
				student.setMajorId(rs.getInt("major_id"));
			}

			rs.close();
			sql.close();
			return student;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return student;
	}

	// Select student records given a certain where clause
	public ArrayList<Student> getWhere(String whereClause) {
		ArrayList<Student> students = new ArrayList<>();

		try {
			createConnection();
			ResultSet rs = sql.executeQuery("SELECT * FROM student " + whereClause);

			while (rs.next()) {
				Student student = new Student();
				student.setId(rs.getInt("id"));
				student.setFirstName(rs.getString("first_name"));
				student.setLastName(rs.getString("last_name"));
				student.setSAT(rs.getInt("sat"));
				student.setGPA(rs.getDouble("gpa"));
				student.setMajorId(rs.getInt("major_id"));
				students.add(student);
			}

			rs.close();
			sql.close();
			return students;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return students;
	}

	// Get a student by last name
	public Student getByLastName(String lastName) {
		Student student = new Student();
		try {
			createConnection();
			ResultSet rs = sql.executeQuery(String.format("SELECT * FROM student where last_name = '%s'", lastName));

			while (rs.next()) {
				student.setId(rs.getInt("id"));
				student.setFirstName(rs.getString("first_name"));
				student.setLastName(rs.getString("last_name"));
				student.setSAT(rs.getInt("sat"));
				student.setGPA(rs.getInt("gpa"));
				student.setMajorId(rs.getInt("major_id"));
			}

			rs.close();
			sql.close();
			return student;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return student;
	}

	// Get all students in student table
	public ArrayList<Student> getAll() {
		ArrayList<Student> students = new ArrayList<>();

		try {

			createConnection();
			ResultSet rs = sql.executeQuery("SELECT * FROM student");
			while (rs.next()) {
				students.add(new Student(rs.getInt("id"), rs.getString("first_name"), rs.getString("last_name"),
						rs.getInt("sat"), rs.getDouble("gpa"), rs.getInt("major_id")));
			}
			rs.close();
			sql.close();
			return students;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return students;
	}

	/* Parameters column name to order by, order can is true for asc, false for
	   desc" */
	public ArrayList<Student> getAllOrdered(String column, boolean order) {
		ArrayList<Student> students = new ArrayList<>();

		try {

			createConnection();
			ResultSet rs = null;
			if (order) {
				rs = sql.executeQuery("SELECT * FROM student ORDER by " + column);
			} else {
				rs = sql.executeQuery("SELECT * FROM student ORDER by " + column + " desc");
			}

			while (rs.next()) {
				students.add(new Student(rs.getInt("id"), rs.getString("first_name"), rs.getString("last_name"),
						rs.getInt("sat"), rs.getDouble("gpa"), rs.getInt("major_id")));
			}
			rs.close();
			sql.close();
			return students;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return students;
	}

	// Create a connection object for sql queries
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

}
