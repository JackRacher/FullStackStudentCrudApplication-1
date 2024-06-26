package com.example.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Student;
import com.example.demo.repo.StudentRepository;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/students")
public class StudentController {

	@Autowired
	private StudentRepository repo;

	@PostMapping("/student")
	public Student createStudent(@RequestBody Student student) {
		System.out.println("Hello");
		return repo.save(student);
	}

	@GetMapping("/students")
	public List<Student> getAllStudents() {
		return repo.findAll();
	}

	@GetMapping("/students/{id}")
	public ResponseEntity<Student> getStudentById(@PathVariable Long id) {
		Student student = repo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Student not exits with id: " + id));
		return ResponseEntity.ok(student);
	}

	@PutMapping("/students/{id}")
	public ResponseEntity<Student> updateStudent(@PathVariable Long id, @RequestBody Student student) {
		Student studentObj = repo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Student not exits with id: " + id));
		studentObj.setName(student.getName());
		studentObj.setSubject(student.getSubject());
		Student updateStudent = repo.save(studentObj);
		return ResponseEntity.ok(updateStudent);
	}

	@DeleteMapping("/students/{id}")
	public ResponseEntity<Map<String, Boolean>> deleteStudent(@PathVariable Long id) {
		Student studentObj = repo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Student not exits with id: " + id));
		repo.delete(studentObj);
		Map<String, Boolean> response = new HashMap<>();
		response.put("delete", Boolean.TRUE);
		return ResponseEntity.ok(response);
	}

}
