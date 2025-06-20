package com.uade.tpo.api_grupo4.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.uade.tpo.api_grupo4.entity.Student;



@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    Student findByUsername(String username);
    boolean existsByUsername(String username);
    Student findByEmail(String email);
    
  
}