package com.uade.tpo.api_grupo4.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.uade.tpo.api_grupo4.entity.Course;
import com.uade.tpo.api_grupo4.entity.CourseSchedule;
import com.uade.tpo.api_grupo4.entity.Inscripcion;
import com.uade.tpo.api_grupo4.entity.Student;

@Repository
public interface InscripcionRepository extends JpaRepository<Inscripcion, Long> {
    List<Inscripcion> findByStudent(Student student);
    List<Inscripcion> findByStudentId(Long studentId);
    List<Inscripcion> findByCourseSchedule(CourseSchedule courseSchedule);
    List<Inscripcion> findByCourseScheduleId(Long courseScheduleId);
    List<Inscripcion> findByEstado(String estado);
    Optional<Inscripcion> findByStudentAndCourseSchedule(Student student, CourseSchedule courseSchedule);
    @Query("SELECT COUNT(i) FROM Inscripcion i WHERE i.courseSchedule.id = :courseScheduleId AND i.estado = 'ACTIVA'")
    Long countActiveByCourseSchedule(@Param("courseScheduleId") Long courseScheduleId);
}
