package com.lss.springboot.SpringbootRest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;

import com.lss.springboot.SpringbootRest.models.Student;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
public class SpringbootRestApplicationTests {
	
	@Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    private String getRootUrl() {
        return "http://localhost:" + port;
    }
	
	@Test
	public void contextLoads() {
	}
	
	@Test
    public void testGetAllStudents() {
         HttpHeaders headers = new HttpHeaders();
         HttpEntity<String> entity = new HttpEntity<String>(null, headers);

         ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "/students",
         HttpMethod.GET, entity, String.class);
  
         assertNotNull(response.getBody());
    }
	
	@Test
    public void testGetStudentById() {
        Student student = restTemplate.getForObject(getRootUrl() + "/students/1", Student.class);
        System.out.println(student.getFirstName());
        assertNotNull(student);
    }
	
	@Test
    public void testRegisterStudent() {
        Student student = new Student();
        student.setEmail("admin@gmail.com");
        student.setFirstName("admin");
        student.setLastName("admin");
        student.setAddress("admin");
        student.setGender("admin");

        ResponseEntity<Student> postResponse = restTemplate.postForEntity(getRootUrl() + "/student", student, Student.class);
        assertNotNull(postResponse);
        assertNotNull(postResponse.getBody());
    }
	
	@Test
    public void testUpdatePost() {
         int id = 1;
         Student student = restTemplate.getForObject(getRootUrl() + "/students/" + id, Student.class);
         student.setFirstName("admin1");
         student.setLastName("admin2");

         restTemplate.put(getRootUrl() + "/students/" + id, student);

         Student updatedStudent = restTemplate.getForObject(getRootUrl() + "/students/" + id, Student.class);
         assertNotNull(updatedStudent);
    }
	
	 @Test
	    public void testDeletePost() {
	         int id = 2;
	         Student student = restTemplate.getForObject(getRootUrl() + "/student/" + id, Student.class);
	         assertNotNull(student);

	         restTemplate.delete(getRootUrl() + "/student/" + id);
	    
	         try {
	              student = restTemplate.getForObject(getRootUrl() + "/student/" + id, Student.class);
	         } catch (final HttpClientErrorException e) {
	         assertEquals(e.getStatusCode(), HttpStatus.NOT_FOUND);
	     }
	 }

}

