package com.uade.tpo.api_grupo4.entity;

import java.time.LocalDateTime;
import java.util.List;

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
    @JoinColumn(name = "estudiante_id", nullable = false)
    private Student estudiante;
    
    @ManyToOne
    @JoinColumn(name = "curso_id", nullable = false)
    private Course course;
    
    @Column(name = "fecha_inscripcion", nullable = false)
    private LocalDateTime fechaInscripcion;
    
    @Column(nullable = false)
    private String estado; // ACTIVA, CANCELADA, FINALIZADA
    
    @OneToMany(mappedBy = "inscripcion", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CourseAttended> asistencias;

    public InscripcionView toView(){
        return new InscripcionView(
            this.id,
            this.estudiante,
            this.course,
            this.fechaInscripcion,
            this.estado,
            this.asistencias,
            
        );
    }
}
