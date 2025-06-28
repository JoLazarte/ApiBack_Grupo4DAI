package com.uade.tpo.api_grupo4.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.uade.tpo.api_grupo4.entity.Course;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    
    Boolean existsByName(String name);
    Optional<Course> findByName(String name);
}
