package com.uade.tpo.api_grupo4.entity;

import java.util.Collection;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.uade.tpo.api_grupo4.controllers.student.StudentView;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

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
public class Student extends Person{

    private String cardNumber;
    @Column(columnDefinition = "LONGTEXT")
    private String dniFrente;
    @Column(columnDefinition = "LONGTEXT")
    private String dniDorso;
    private String nroTramite;
    private int cuentaCorriente;
    private String nroDocumento;
    private String tipoTarjeta;


    public StudentView toView(){
        return new StudentView(
                this.id, this.username, this.firstName, this.lastName, this.email, this.password,
                this.phone, this.address, this.urlAvatar, this.permissionGranted,
                this.cardNumber, this.dniFrente, this.dniDorso, this.nroTramite, this.cuentaCorriente,
                this.nroDocumento, this.tipoTarjeta
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("STUDENT"));
    }

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