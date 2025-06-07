package com.uade.tpo.api_grupo4.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Headquarter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String name;
    @Column(unique = true)
    private String phone;
    @Column
    private String address;
    @Column(unique = true)
    private String email;
    @Column(unique = true)
    private String whattsapp;
    @Column
    private List<CourseSchedule> coursesScheduled;
    @Column
    private String typeOfBonus;
    @Column
    private String courseBonus;	
    @Column
    private String typeOfPromo;	
    @Column
    private String coursePromo;
}
