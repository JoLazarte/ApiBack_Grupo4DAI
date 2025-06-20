package com.uade.tpo.api_grupo4.controllers.student;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.uade.tpo.api_grupo4.controllers.person.PersonView;
import com.uade.tpo.api_grupo4.entity.CourseAttended;
import com.uade.tpo.api_grupo4.entity.Student;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper=false)
@AllArgsConstructor
@NoArgsConstructor
public class StudentView extends PersonView{

    private String username;
    private String firstName; 
    private String lastName;
    private String email;
    private String password;
    private String phone;
    private String address;
    private String urlAvatar;
    private Boolean permissionGranted;
    @JsonIgnore
    private List<CourseAttended> attendedCourses;
    private String cardNumber;
    private String dniFrente;
    private String dniDorso;
    private String nroTramite;
    private int cuentaCorriente;

    public StudentView(Long id, String username, String firstName, String lastName, String email, String password,
            String phone, String address, String urlAvatar, Boolean permissionGranted,
            List<CourseAttended> attendedCourses, String cardNumber, String dniFrente, String dniDorso, String nroTramite, int cuentaCorriente) {
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

    public Student toEntity(){
        return new Student(
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
