package com.uade.tpo.api_grupo4.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
//import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.uade.tpo.api_grupo4.controllers.user.StudentDTO;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
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
    name = "courseEnrolled_student", 
    joinColumns = @JoinColumn(name = "student_id"), 
    inverseJoinColumns = @JoinColumn(name = "courseEnrolled_id"))
    private List<CourseEnrolled> coursesEnrolled;
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
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

    public StudentDTO toDTO() {
        return new StudentDTO(
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

    public void assignUser(User user) {
        this.user.setStudent(this);
    }

    public void updateData(Student newStudent){
        setCardNumber(newStudent.getCardNumber());
        setDniFrente(newStudent.getDniFrente());
        setDniDorso(newStudent.getDniDorso());
        setNroTramite(newStudent.getNroTramite());
        setCuentaCorriente(newStudent.getCuentaCorriente());
    }
     

}
