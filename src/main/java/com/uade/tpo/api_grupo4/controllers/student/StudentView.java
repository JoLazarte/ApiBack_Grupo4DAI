package com.uade.tpo.api_grupo4.controllers.student;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.uade.tpo.api_grupo4.entity.CourseAttended;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentView {
    private Long id;
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
    private List<CourseAttended> attendedCourse;
    private int cardNumber;
    private String dniFrente;
    private String dniDorso;
    private int nroTramite;
    private int cuentaCorriente;

    public StudentView(Long id, List<CourseAttended> attendedCourse, int cardNumber, String dniFrente, String dniDorso, int nroTramite, int cuentaCorriente){
                this.id = id;
                this.attendedCourse = attendedCourse;
                this.cardNumber = cardNumber;
                this.dniFrente = dniFrente;
                this.dniDorso = dniDorso;
                this.nroTramite = nroTramite;
                this.cuentaCorriente = cuentaCorriente;
    }



    @Override
    public String toString() {
        return "StudentView [ attendedCourse=" + attendedCourse + ", cardNumber=" + cardNumber
                + ", dniFrente=" + dniFrente + ", dniDorso=" + dniDorso + ", nroTramite=" + nroTramite
                + ", cuentaCorriente=" + cuentaCorriente + "]";
    }
    
    
    
}
