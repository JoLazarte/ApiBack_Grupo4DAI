package com.uade.tpo.api_grupo4.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.uade.tpo.api_grupo4.entity.Course;
import com.uade.tpo.api_grupo4.entity.CourseSchedule;

@Repository
public interface CourseScheduleRepository extends JpaRepository<CourseSchedule, Long> {
    List<CourseSchedule> findByCourse(Course course);
    List<CourseSchedule> findByCourseId(Long courseId);
    List<CourseSchedule> findByDiaEnQueSeDicta(int dia);
    List<CourseSchedule> findByInstructorContainingIgnoreCase(String instructor);
    
    
}