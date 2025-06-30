// Archivo: StudentView.java (Versión Corregida y Final)
package com.uade.tpo.api_grupo4.controllers.student;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.uade.tpo.api_grupo4.controllers.person.PersonView;
import com.uade.tpo.api_grupo4.entity.Course;
import com.uade.tpo.api_grupo4.entity.Inscripcion;
import com.uade.tpo.api_grupo4.entity.Student;

import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true) // <-- Corregido para herencia
@NoArgsConstructor // Lombok usará el constructor de abajo
public class StudentView extends PersonView {

    private String cardNumber;
    private String dniFrente;
    private String dniDorso;
    private String nroTramite;
    private int cuentaCorriente;
    private String nroDocumento;
    private String tipoTarjeta;

    // ---> CONSTRUCTOR CORREGIDO USANDO SUPER() <---
    public StudentView(Long id, String username, String firstName, String lastName, String email, String password,
                       String phone, String address, String urlAvatar, Boolean permissionGranted,
                        String cardNumber, String dniFrente, String dniDorso, String nroTramite, int cuentaCorriente,
                       String nroDocumento, String tipoTarjeta) {

        // 1. Llamamos al constructor del padre (PersonView) para que él asigne sus campos.
        super(id, username, firstName, lastName, email, password, phone, address, urlAvatar, permissionGranted);

        // 2. Aquí solo asignamos los campos propios de StudentView.
       
        this.cardNumber = cardNumber;
        this.dniFrente = dniFrente;
        this.dniDorso = dniDorso;
        this.nroTramite = nroTramite;
        this.cuentaCorriente = cuentaCorriente;
        this.nroDocumento = nroDocumento;
        this.tipoTarjeta = tipoTarjeta;
    }

    // ---> MÉTODO toEntity() CORREGIDO USANDO GETTERS <---
    public Student toEntity() {
        return Student.builder()
                .id(this.getId()) // Usamos getters para los campos del padre
                .username(this.getUsername())
                .firstName(this.getFirstName())
                .lastName(this.getLastName())
                .email(this.getEmail())
                .password(this.getPassword())
                .phone(this.getPhone())
                .address(this.getAddress())
                .urlAvatar(this.getUrlAvatar())
                .permissionGranted(this.getPermissionGranted())
                // Los campos propios se pueden acceder directamente
                .cardNumber(this.cardNumber)
                .dniFrente(this.dniFrente)
                .dniDorso(this.dniDorso)
                .nroTramite(this.nroTramite)
                .cuentaCorriente(this.cuentaCorriente)
                .nroDocumento(this.nroDocumento)
                .tipoTarjeta(this.tipoTarjeta)
                .build();
    }
}