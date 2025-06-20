package com.uade.tpo.api_grupo4.entity;

import java.util.List;

//import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.uade.tpo.api_grupo4.controllers.student.StudentView;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data 
@EqualsAndHashCode(callSuper=false)
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Student extends Person{
  
    @ManyToMany
    @JoinTable(
    name = "attendedCourse_student", 
    joinColumns = @JoinColumn(name = "student_id"), 
    inverseJoinColumns = @JoinColumn(name = "attendedCourse_id"))
    private List<CourseAttended> attendedCourses;
    //@Column(unique = true)
    private String cardNumber;
    //@NotEmpty
    @Column(columnDefinition = "LONGTEXT")
    private String dniFrente;
    //@NotEmpty
    @Column(columnDefinition = "LONGTEXT")
    private String dniDorso;
    //@Column(unique = true)
    private String nroTramite;
    //@Column(unique = true)
    private int cuentaCorriente;

    public Student(Long id, String username, String firstName, String lastName, String email, String password,
            String phone, String address, String urlAvatar, Boolean permissionGranted
            ,List<CourseAttended> attendedCourses, String cardNumber, String dniFrente, String dniDorso, String nroTramite, int cuentaCorriente
            ) {
        this.id = id;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.address = address;
        this.urlAvatar = urlAvatar;
        this.permissionGranted = permissionGranted;
        this.attendedCourses = attendedCourses;
        this.cardNumber = cardNumber;
        this.dniFrente = dniFrente;
        this.dniDorso = dniDorso;
        this.nroTramite = nroTramite;
        this.cuentaCorriente = cuentaCorriente;
    }

    
    public StudentView toView(){
        return new StudentView(
                this.id,
                this.username, 
                this.firstName, 
                this.lastName, 
                this.email, 
                this.password, 
                this.phone, 
                this.address, 
                this.urlAvatar, 
                this.permissionGranted, 
                this.attendedCourses,
                this.cardNumber,
                this.dniFrente,
                this.dniDorso,
                this.nroTramite,
                this.cuentaCorriente
        );
    }

     

}
