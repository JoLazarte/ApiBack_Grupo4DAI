package com.uade.tpo.api_grupo4.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.uade.tpo.api_grupo4.entity.Course;
import com.uade.tpo.api_grupo4.entity.Inscripcion;
import com.uade.tpo.api_grupo4.entity.Student;

@Repository
public interface InscripcionRepository extends JpaRepository<Inscripcion, Long> {
    List<Inscripcion> findByEstudiante(Student estudiante);
    List<Inscripcion> findByEstudianteId(Long estudianteId);
    List<Inscripcion> findByCourse(Course course);
    List<Inscripcion> findByCourseId(Long courseId);
    List<Inscripcion> findByEstado(String estado);
    Optional<Inscripcion> findByEstudianteAndCourse(Student estudiante, Course course);
    
    @Query("SELECT i FROM Inscripcion i WHERE i.estudiante.id = :studentId AND i.estado = 'ACTIVA'")
    List<Inscripcion> findActiveInscriptionsByStudent(@Param("studentId") Long studentId);
    
    @Query("SELECT i FROM Inscripcion i WHERE i.course.sede.id = :sedeId")
    List<Inscripcion> findInscriptionsByHeadquarter(@Param("sedeId") Long sedeId);
    
    @Query("SELECT COUNT(i) FROM Inscripcion i WHERE i.course.id = :courseId AND i.estado = 'ACTIVA'")
    Long countActiveByCourse(@Param("courseId") Long courseId);
}
