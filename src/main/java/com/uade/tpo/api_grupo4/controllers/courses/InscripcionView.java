package com.uade.tpo.api_grupo4.controllers.courses;

import java.time.LocalDateTime;
import java.util.List;

import com.uade.tpo.api_grupo4.entity.Course;
import com.uade.tpo.api_grupo4.entity.CourseAttended;
import com.uade.tpo.api_grupo4.entity.CourseSchedule;
import com.uade.tpo.api_grupo4.entity.Headquarter;
import com.uade.tpo.api_grupo4.entity.Inscripcion;
import com.uade.tpo.api_grupo4.entity.Student;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class InscripcionView {
    private Long id;

    private Student student;
 
    private CourseSchedule courseSchedule;
    
    private LocalDateTime fechaInscripcion;
    
    private String estado; // ACTIVA, CANCELADA, FINALIZADA
    
    private List<CourseAttended> asistencias;

    public Inscripcion toEntity(){
        return new Inscripcion(
            this.id,
            this.student,
            this.courseSchedule,
            this.fechaInscripcion,
            this.estado,
            this.asistencias
            
        );
    }
}
