package com.uade.tpo.api_grupo4.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.uade.tpo.api_grupo4.entity.CourseAttended;



@Repository
public interface CourseAttendRepository extends JpaRepository<CourseAttended, Long> {
    
}
