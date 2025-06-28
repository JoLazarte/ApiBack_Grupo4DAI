package com.uade.tpo.api_grupo4.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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
    private String content;
    private String requirements;
    private int length;
    private Double price;
    private CourseMode mode;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "coursSchedule_id")
    @JsonManagedReference
    private CourseSchedule courseSchedule;

    public void assignCourseSched(CourseSchedule courseSchedule) {
        this.courseSchedule.setCourse(this);
    }
    
}