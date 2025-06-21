package com.uade.tpo.api_grupo4.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
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
    @ManyToMany
    @JoinTable(
    name = "courseScheduled_headq", 
    joinColumns = @JoinColumn(name = "courseShedule_id"), 
    inverseJoinColumns = @JoinColumn(name = "headquarter_id"))
    private List<Headquarter> headquarters;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "course_id")
    @JsonBackReference
    private Course course;
    private LocalDate startDate;
    private LocalDate completionDate;
    private int vacancy;
}