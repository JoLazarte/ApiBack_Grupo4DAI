package com.uade.tpo.api_grupo4.entity;

import java.util.List;

//import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.uade.tpo.api_grupo4.controllers.student.StudentView;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Student{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToMany
    @JoinTable(
    name = "attendedCourse_student", 
    joinColumns = @JoinColumn(name = "student_id"), 
    inverseJoinColumns = @JoinColumn(name = "attendedCourse_id"))
    private List<CourseAttended> attendedCourses;
    //@Column(unique = true)
    private int cardNumber;
    //@NotEmpty
    @Column(nullable = false, columnDefinition = "LONGTEXT")
    private String dniFrente;
    //@NotEmpty
    @Column(nullable = false, columnDefinition = "LONGTEXT")
    private String dniDorso;
    //@Column(unique = true)
    private int nroTramite;
    //@Column(unique = true)
    private int cuentaCorriente;
    @ManyToOne
    @JoinColumn(name = "id", referencedColumnName = "id", insertable = false, updatable = false)
    private Person persona;

    public StudentView toView() {
        return new StudentView(
                this.id,
                this.attendedCourses,
                this.cardNumber,
                this.dniFrente,
                this.dniDorso,
                this.nroTramite,
                this.cuentaCorriente
                );
    }
    public StudentView toViewDos(){
        return new StudentView(
                this.id,
                this.persona.getUsername(), 
                this.persona.getFirstName(), 
                this.persona.getLastName(), 
                this.persona.getEmail(), 
                this.persona.getPassword(), 
                this.persona.getPhone(), 
                this.persona.getAddress(), 
                this.persona.getUrlAvatar(), 
                this.persona.getPermissionGranted(), 
                this.attendedCourses,
                this.cardNumber,
                this.dniFrente,
                this.dniDorso,
                this.nroTramite,
                this.cuentaCorriente
        );
    }

     

}
