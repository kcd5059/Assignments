package com.ssa.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


import com.ssa.entity.Student;
import com.ssa.service.IStudentService;

@CrossOrigin
@RestController
@RequestMapping("/")
public class StudentController {
	
	@Autowired
	private IStudentService studentService;
	
	
	@RequestMapping(value= "/students", method = RequestMethod.GET)
    public ResponseEntity<List<Student>> getAllStudents() {
        List<Student> students = studentService.getAllStudents();
        return new ResponseEntity<List<Student>>(students, HttpStatus.OK);
    }
	
	@RequestMapping(value= "/student/{id}", method = RequestMethod.GET)
    public ResponseEntity<Student> getStudentById(@PathVariable("id") Integer id) {
        Student student = studentService.getStudentById(id);
        return new ResponseEntity<Student>(student, HttpStatus.OK);
    }
	
	@RequestMapping(value= "/deletestudent/{id}", method = RequestMethod.GET)
    public void deleteStudent(@PathVariable("id") Integer id) {
        Student student = studentService.getStudentById(id);
        studentService.deleteStudent(student);
    }
		@RequestMapping(value= "/updatestudent/{id}/{fn}/{ln}/{sat}/{gpa}/{mId}", method = RequestMethod.GET)
	    public void updateStudent(
	    		@PathVariable("id") Integer id, @PathVariable("fn") String fn, @PathVariable("ln") String ln,
	    		@PathVariable("sat") int sat, @PathVariable("gpa") double gpa, @PathVariable("mId") Integer mId
	    		) {
	        Student student = studentService.getStudentById(id);
	        student.setFirst_name(fn);
	        student.setLast_name(ln);
	        student.setSat(sat);
	        student.setGpa(gpa);
	        student.setMajor_id(mId);
	        studentService.updateStudent(student);
	    }
	
		@RequestMapping(value= "/addstudent/{fn}/{ln}/{sat}/{gpa}/{mId}", method = RequestMethod.GET)
	    public boolean addStudent(
	    		@PathVariable("fn") String fn, @PathVariable("ln") String ln,
	    		@PathVariable("sat") int sat, @PathVariable("gpa") double gpa, @PathVariable("mId") Integer mId) {
	        Student student = new Student();
	        student.setFirst_name(fn);
	        student.setLast_name(ln);
	        student.setSat(sat);
	        student.setGpa(gpa);
	        student.setMajor_id(mId);
	        studentService.addStudent(student);
	        return true;
	    }

}
