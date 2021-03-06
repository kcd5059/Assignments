package ssa;
import java.sql.*;


public class SQLConnection {

	String url;
	String username;
	String password;
	public Connection conn;
	
	public int executeUpdate(String sql) throws SQLException {
		Statement stat = conn.createStatement();
		try {
			int rowsAffected = stat.executeUpdate(sql);
			return rowsAffected;
		} catch (SQLException ex) {
			throw ex; 
		}
	}
	public int executeInsert(String sql) throws SQLException {
		Statement stat = conn.createStatement();
		try {
			int primaryKey = -1;
			stat.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
			ResultSet rs = stat.getGeneratedKeys();
			if(rs.next()){
				primaryKey = rs.getInt(1);
			}
			rs.close();
			return primaryKey;
		} catch (SQLException ex) {
			throw ex; 
		}
	}
	public ResultSet executeQuery(String sql) throws SQLException {
		Statement stat = conn.createStatement();
		try {
			ResultSet rs = stat.executeQuery(sql);
			return rs;
		} catch(SQLException ex) {
			throw ex; 
		}
	}
	public SQLConnection(String url, String usr, String pwd) throws SQLException {
		conn = DriverManager.getConnection(url, usr, pwd);
	}
	public void close() throws SQLException {
		if (conn != null) { 
			conn.close(); 
			conn = null; 
		}
	}
}
