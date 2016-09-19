package ssa;

import java.util.ArrayList;

public class Mainline {

	public static void main(String[] args) {
		
	    Students students = new Students();
//	    // retrieve a single student with id = 130
//	    Student aStudent = students.getById(130);
//	    // display the student
//	    System.out.println(aStudent); // displays the student data in a formatted way
//	    // retrieve all the students into a collection
//	    ArrayList<Student> allStudents = students.getAll();
//	    // iterate through the collection and display each student data in a formatted way
//	    for(Student student : allStudents) {
//	        System.out.println(student);
//	    }
//	    
//	    // Insert new student
//	    Student derpJr = new Student ("Derp","Dorpson",900,1.5,6);
//	    students.insert(derpJr);
//	    System.out.println(students.getByLastName("Dorpson"));
//	    
//	    // Set id for update to work, ID MUST BE SET
//	    derpJr.setId(214);
//	    // Update student's information
//	    derpJr.setGPA(2.0);
//	    derpJr.setFirstName("Pred");
//	    derpJr.setLastName("Nosprod");
//	    derpJr.setSAT(1600);
//	    int changes = students.update(derpJr);
//	    System.out.println("POST UPDATE: " + changes + " changes made");
//	    System.out.println(students.getByLastName("Nosprod"));
//	    
//	    // Delete the new student (by last name because id auto_increments and changes every time this is run)
//	    students.deleteByLastName("Nosprod");
//	    
//	    // Prove deletion (prints null student)
//	    System.out.println(students.getByLastName("Nosprod"));
		
	    // I built a method to update the Id since it cannot be done with the normal update method
//		students.updateId(190, 999);
//		System.out.println(students.getById(999));
//		students.updateId(999, 190);
	    
//	    ArrayList<Student> whereStudents = students.getWhere("WHERE gpa between 2.0 AND 2.99");
//	    
//	    for (Student student : whereStudents) {
//	    	System.out.println(student);
//	    }
	    
	    ArrayList<Student> orderedStudents = students.getAllOrdered("gpa", true);
	    
	    for(Student student : orderedStudents) {
	    	System.out.println(student);
	    }


	}

}
