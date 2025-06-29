package com.uade.tpo.api_grupo4.repository;
//import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.uade.tpo.api_grupo4.entity.Student;



@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    //Optional<Student> findByUsername(String username);
    //Optional<Student> findByEmail(String email);
    //Optional<Student> findByNroDocumento(String nroDocumento);
    boolean existsByEmail(String email);
    Student findByUsername(String username);
    boolean existsByUsername(String username);
    Student findByEmail(String email);
    
  
}