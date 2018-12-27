package com.lss.springboot.SpringbootRest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lss.springboot.SpringbootRest.models.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long>{

}
