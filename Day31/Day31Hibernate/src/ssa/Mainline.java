package ssa;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Mainline {
	

	public static void main(String[] args) {
		
		
		
		// This will hold the ID of the added major, so that updates can be made without changing hardcoded values
		int targetMajorID;
		
		System.out.println("*****STARTING LIST*****");
		printMajors();
		// Add a new major and save the id
		Major major = new Major("Martian Terraforming", 1599);
		targetMajorID = addMajor(major);
		System.out.println("*****Added Major******");
		System.out.println(getMajor(targetMajorID));
		// Update major with the ID of  to have a req_sat of 1600
		updateMajorReqSat(targetMajorID, 1600);
		System.out.println("*****Updated Major*****");
		System.out.println(getMajor(targetMajorID));
		// Now delete that same major
		deleteMajor(targetMajorID);
		System.out.println("*****POST DELETE LIST*****");
		printMajors();
		
		System.out.println("*****printWhere() Results*****");
		// Prints result of select with given WHERE clause
		printWhere("id = 3 OR id = 7");

	}
	
	// Add major
	public static int addMajor(Major major) {
		
		SessionFactory factory = buildFactory();
		Session session = factory.getCurrentSession();
		int id = -1;
		try {
			
			// begin transaction
			session.beginTransaction();
			// Extract the id of the newly added major
			id = (int) session.save(major);

			session.getTransaction().commit();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			factory.close();
		}
		
		// Return ID of added major
		return id;
	}
	// Delete major with passed id
	public static void deleteMajor(int id) {
		
		SessionFactory factory = buildFactory();
		Session session = factory.getCurrentSession();
		try {
			
			session.beginTransaction();
			Major major = session.get(Major.class, id);
			session.delete(major);

			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			factory.close();
		}
	}
	
	// Update the req_sat value for given major id
	public static void updateMajorReqSat(int id, int req_sat) {
		SessionFactory factory = buildFactory();
		Session session = factory.getCurrentSession();
		
		try {
			session.beginTransaction();
			Major major = session.get(Major.class, id);
			major.setReq_sat(req_sat);
			session.getTransaction().commit();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			factory.close();
		}
	}
	// Update the req_sat value for given major id
	public static void updateMajorDescription(int id, String newDescription) {
		SessionFactory factory = buildFactory();
		Session session = factory.getCurrentSession();
		
		try {
			session.beginTransaction();
			Major major = session.get(Major.class, id);
			major.setDescription(newDescription);
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			factory.close();
		}
	}
	
	// Returns single major with given id
	public static Major getMajor(int id) {
		SessionFactory factory = buildFactory();
		Session session = factory.getCurrentSession();
		
		Major major = null;
		try {
			session.beginTransaction();
			major = session.get(Major.class, id);
			session.getTransaction().commit();
		} catch (Exception e) {e.printStackTrace();}
		finally {
			factory.close();
		}
		
		return major;
	}
	
	// Print a list of all majors in table
	public static void printMajors() {
		
		SessionFactory factory = buildFactory();
		Session session = factory.getCurrentSession();
		List<Major> majors = null;
		try {
			session.beginTransaction();
			majors = session.createQuery("from Major").list();
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			factory.close();
		}
		
		System.out.println("ID | Description | Required SAT Score");
		display(majors);
	}
	
	// Print a list of majors in table fitting the WHERE clause
		public static void printWhere(String whereClause) {
			
			SessionFactory factory = buildFactory();
			Session session = factory.getCurrentSession();
			List<Major> majors = null;
			try {
				session.beginTransaction();
				majors = session.createQuery("from Major WHERE " + whereClause).list();
			} catch (Exception e){
				e.printStackTrace();
			} finally {
				factory.close();
			}
			
			System.out.println("ID | Description | Required SAT Score");
			display(majors);
		}
	// Sets up and returns factory for session creation
	public static SessionFactory buildFactory() {
		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Major.class)
				.buildSessionFactory();
		
		return factory;
	}
	
	// Print given list of majors
	public static void display(List<Major> list) {
		for(Major major : list) {
			System.out.println(major);
		}
	}

}
