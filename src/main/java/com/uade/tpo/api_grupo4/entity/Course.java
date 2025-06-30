package com.uade.tpo.api_grupo4.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.uade.tpo.api_grupo4.controllers.courses.CourseView;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(columnDefinition = "LONGTEXT")
    private String content;
    private String requirements;
    private int length;
    private int price;
    private CourseMode mode;
    private String fechaInicio;
    private String fechaFin;
    @ManyToOne
    @JoinColumn(name = "headquarter_id", nullable = false)
    private Headquarter sede;
    
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
   
    private List<CourseSchedule> cronogramas;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    //@JsonManagedReference
    private List<Inscripcion> inscripciones;

    public CourseView toView(){
        return new CourseView(
            this.id,
            this.name,
            this.content,
            this.requirements,
            this.length,
            this.price,
            this.mode,
            this.fechaInicio,
            this.fechaFin, 
            this.sede,
            this.cronogramas,
            this.inscripciones
        );
    }


    


    
    
}