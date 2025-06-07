package com.uade.tpo.api_grupo4.repository;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.uade.tpo.api_grupo4.entity.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    @Query("SELECT u FROM User u WHERE u.username = ?1")
    Optional<Student> findByUsername(String username);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
}