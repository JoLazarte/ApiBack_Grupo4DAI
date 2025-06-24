package com.uade.tpo.api_grupo4.entity;

import java.util.Collection; // <-- NUEVA IMPORTACIÓN
import java.util.List;
import org.springframework.security.core.GrantedAuthority; // <-- NUEVA IMPORTACIÓN
import org.springframework.security.core.authority.SimpleGrantedAuthority; // <-- NUEVA IMPORTACIÓN
import org.springframework.security.core.userdetails.UserDetails; // <-- NUEVA IMPORTACIÓN

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
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
// PASO 1: AÑADIMOS "implements UserDetails"
public class Student extends Person implements UserDetails {

    @ManyToMany
    @JoinTable(
            name = "attendedCourse_student",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "attendedCourse_id"))
    private List<CourseAttended> attendedCourses;

    private String cardNumber;
    @Column(columnDefinition = "LONGTEXT")
    private String dniFrente;
    @Column(columnDefinition = "LONGTEXT")
    private String dniDorso;
    private String nroTramite;
    private int cuentaCorriente;
    private String nroDocumento;
    private String tipoTarjeta;


    public Student(Long id, String username, String firstName, String lastName, String email, String password,
                   String phone, String address, String urlAvatar, Boolean permissionGranted
            ,List<CourseAttended> attendedCourses, String cardNumber, String dniFrente, String dniDorso, String nroTramite, int cuentaCorriente, String nroDocumento, String tipoTarjeta
    ) {
        super(id, username, firstName, lastName, email, password, phone, address, urlAvatar, permissionGranted);
        this.attendedCourses = attendedCourses;
        this.cardNumber = cardNumber;
        this.dniFrente = dniFrente;
        this.dniDorso = dniDorso;
        this.nroTramite = nroTramite;
        this.cuentaCorriente = cuentaCorriente;
        this.nroDocumento = nroDocumento;
        this.tipoTarjeta = tipoTarjeta;
    }


    public StudentView toView(){
        return new StudentView(
                this.id, this.username, this.firstName, this.lastName, this.email, this.password,
                this.phone, this.address, this.urlAvatar, this.permissionGranted, this.attendedCourses,
                this.cardNumber, this.dniFrente, this.dniDorso, this.nroTramite, this.cuentaCorriente,
                this.nroDocumento, this.tipoTarjeta
        );
    }

    // ====================================================================
    // PASO 2: AÑADIMOS LOS MÉTODOS REQUERIDOS POR LA INTERFAZ UserDetails
    // ====================================================================

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Le damos a todos los estudiantes el rol "STUDENT".
        return List.of(new SimpleGrantedAuthority("STUDENT"));
    }

    // NOTA: getPassword() y getUsername() ya los provee la anotación @Data
    // al heredar de Person, por lo que no necesitamos volver a escribirlos.

    @Override
    public boolean isAccountNonExpired() {
        return true; // Asumimos que las cuentas no expiran
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Asumimos que las cuentas no se bloquean
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Asumimos que las contraseñas no expiran
    }

    @Override
    public boolean isEnabled() {
        return true; // Asumimos que los usuarios están siempre habilitados
    }
}