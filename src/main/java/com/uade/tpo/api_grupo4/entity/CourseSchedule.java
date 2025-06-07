package com.uade.tpo.api_grupo4.entity;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CourseSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToMany
    @JoinTable(
    name = "courseSchwduled_headq", 
    joinColumns = @JoinColumn(name = "courseShedule_id"), 
    inverseJoinColumns = @JoinColumn(name = "headquarter_id"))
    private List<Headquarter> headquarters;
  
    private Course course;
    private LocalDate startDate;
    private LocalDate completionDate;
    private Boolean vacancy;
}
