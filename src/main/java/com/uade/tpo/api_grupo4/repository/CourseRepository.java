package com.uade.tpo.api_grupo4.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.uade.tpo.api_grupo4.entity.Course;
import com.uade.tpo.api_grupo4.entity.CourseMode;
import com.uade.tpo.api_grupo4.entity.Headquarter;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    
    Boolean existsByName(String name);
    Optional<Course> findByName(String name);
    List<Course> findBySede(Headquarter sede);
    List<Course> findBySedeId(Long sedeId);
    List<Course> findByMode(CourseMode mode);
    List<Course> findByNameContainingIgnoreCase(String name);
    List<Course> findByFechaInicioBetween(LocalDate startDate, LocalDate endDate);
    List<Course> findByPriceBetween(Double minPrice, Double maxPrice);
    
    @Query("SELECT c FROM Course c WHERE c.fechaInicio >= :currentDate")
    List<Course> findUpcomingCourses(@Param("currentDate") LocalDate currentDate);
    
    @Query("SELECT c FROM Course c WHERE c.sede.id = :sedeId AND c.fechaInicio >= :currentDate")
    List<Course> findUpcomingCoursesByHeadquarter(@Param("sedeId") Long sedeId, @Param("currentDate") LocalDate currentDate);
   
}
