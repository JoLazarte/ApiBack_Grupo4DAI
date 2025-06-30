package com.uade.tpo.api_grupo4.repository;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.uade.tpo.api_grupo4.entity.Course;
import com.uade.tpo.api_grupo4.entity.CourseMode;


@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    
    Boolean existsByName(String name);
    Optional<Course> findByName(String name);

    List<Course> findByMode(CourseMode mode);
    List<Course> findByNameContainingIgnoreCase(String name);
    List<Course> findByFechaInicioBetween(String startDate, String endDate);
    List<Course> findByPriceBetween(Double minPrice, Double maxPrice);
    
   
}
