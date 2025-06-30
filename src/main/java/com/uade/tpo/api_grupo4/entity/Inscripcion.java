package com.uade.tpo.api_grupo4.entity;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.uade.tpo.api_grupo4.controllers.courses.InscripcionView;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Inscripcion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    @JsonBackReference
    private Student student;
    
    @ManyToOne
    @JoinColumn(nullable = false, name = "courseSchedule_id")
   //@JsonBackReference
    private CourseSchedule courseSchedule;
    
    @Column(name = "fecha_inscripcion", nullable = false)
    private LocalDateTime fechaInscripcion;
    
    @Column(nullable = false)
    private String estado; // ACTIVA, CANCELADA, FINALIZADA
    
    @OneToMany(mappedBy = "inscripcion", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CourseAttended> asistencias;

    public InscripcionView toView(){
        return new InscripcionView(
            this.id,
            this.student,
            this.courseSchedule,
            this.fechaInscripcion,
            this.estado,
            this.asistencias
            
        );
    }
}
