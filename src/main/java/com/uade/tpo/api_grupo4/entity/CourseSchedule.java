package com.uade.tpo.api_grupo4.entity;

import java.io.Serializable;
import java.util.List;

import com.uade.tpo.api_grupo4.controllers.courseSchedule.CourseScheduleView;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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
public class CourseSchedule implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(nullable = false, name = "course_id")
    //@JsonBackReference
    private Course course;
    @Column(name = "hora_inicio", nullable = false)
    private String horaInicio;
    @Column(name = "hora_fin", nullable = false)
    private String horaFin;
    private String instructor;
    private int vacancy;
    private int diaEnQueSeDicta;
   
    @ManyToOne
    @JoinColumn(name = "headquarter_id")
    private Headquarter sede;
    public CourseScheduleView toView() {
        return new CourseScheduleView(
                this.id,
                this.course,
                this.horaInicio,
                this.horaFin,
                this.instructor,
                this.vacancy,
                this.diaEnQueSeDicta,
                this.sede);
    }
}