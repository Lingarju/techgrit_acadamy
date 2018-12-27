package com.lss.springboot.SpringbootRest.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lss.springboot.SpringbootRest.exception.ResourceNotFoundException;
import com.lss.springboot.SpringbootRest.models.Student;
import com.lss.springboot.SpringbootRest.repository.StudentRepository;

@RestController
@RequestMapping("/api/v1")
public class StudentController {
	
	@Autowired
	private StudentRepository studentRepository;
	
	@GetMapping("/students")
	public List<Student> getAllStudents() {
		return studentRepository.findAll();
	}
	
	@GetMapping("/students/{id}")
    public ResponseEntity<Student> getStudentById(
    @PathVariable(value = "id") Long student_id) throws ResourceNotFoundException {
        Student student = studentRepository.findById(student_id)
        .orElseThrow(() -> new ResourceNotFoundException("Student not found on :: "+ student_id));
        return ResponseEntity.ok().body(student);
     
    }
	
	@PostMapping("/student")
	public Student registerStudent(@Valid @RequestBody Student student) {
		return studentRepository.save(student);
	}
	
	@PutMapping("/students/{id}")
    public ResponseEntity<Student> updateStudent(
    @PathVariable(value = "id") Long student_id,
    @Valid @RequestBody Student studentDetails) throws ResourceNotFoundException {
         Student student = studentRepository.findById(student_id)
          .orElseThrow(() -> new ResourceNotFoundException("Student not found on :: "+ student_id));
        
        student.setFirstName(studentDetails.getFirstName());
        student.setLastName(studentDetails.getLastName());
        student.setGender(studentDetails.getGender());
        student.setAddress(studentDetails.getAddress());
        student.setEmail(studentDetails.getEmail());
        student.setDateOfBirth(studentDetails.getDateOfBirth());
        final Student updatedStudent = studentRepository.save(student);
        return ResponseEntity.ok(updatedStudent);
   }
	
	@DeleteMapping("/student/{id}")
	   public Map<String, Boolean> deleteStudent(
	       @PathVariable(value = "id") Long student_id) throws Exception {
	       Student student = studentRepository.findById(student_id)
	          .orElseThrow(() -> new ResourceNotFoundException("Student not found on :: "+ student_id));

	       studentRepository.delete(student);
	       Map<String, Boolean> response = new HashMap<>();
	       response.put("deleted", Boolean.TRUE);
	       return response;
	   }
}
