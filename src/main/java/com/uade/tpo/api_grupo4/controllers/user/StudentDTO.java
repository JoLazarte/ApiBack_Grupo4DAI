package com.uade.tpo.api_grupo4.controllers.user;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.uade.tpo.api_grupo4.entity.CourseEnrolled;
import com.uade.tpo.api_grupo4.entity.Student;
import com.uade.tpo.api_grupo4.entity.User;

//import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentDTO {
    
    private Long id;
    @JsonIgnore
    private List<CourseEnrolled> coursesEnrolled;
  
    private int cardNumber;

    private String dniFrente;
 
    private String dniDorso;

    private int nroTramite;

    private int cuentaCorriente;
    
    private User user;


    public Student toEntity() {
        return new Student(
                this.id,
                this.coursesEnrolled,
                this.cardNumber,
                this.dniFrente,
                this.dniDorso,
                this.nroTramite,
                this.cuentaCorriente, 
                this.user
            );   
                
    }
}
