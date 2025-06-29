package com.uade.tpo.api_grupo4.entity;

import jakarta.persistence.*; // Importante
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity // <-- CAMBIO 1: Ahora es una entidad real
@Inheritance(strategy = InheritanceType.JOINED) // <-- CAMBIO 2: Estrategia de herencia
public abstract class Person implements UserDetails { // <-- CAMBIO 3: Implementa UserDetails aquí
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
    @Column(unique = true, nullable = false)
    protected String username;
    // ... resto de tus campos de Person ...
    protected String firstName;
    protected String lastName;
    @Column(unique = true, nullable = false)
    protected String email;
    protected String password;
    @Column(unique = true)
    protected String phone;
    @Column(unique = true)
    protected String address;
    @Column(columnDefinition = "LONGTEXT")
    protected String urlAvatar;
    protected Boolean permissionGranted;

    // --- Métodos de UserDetails (los movemos aquí) ---
    // La implementación concreta la darán las clases hijas
    @Override
    public abstract Collection<? extends GrantedAuthority> getAuthorities();

    @Override
    public boolean isAccountNonExpired() { return true; }
    @Override
    public boolean isAccountNonLocked() { return true; }
    @Override
    public boolean isCredentialsNonExpired() { return true; }
    @Override
    public boolean isEnabled() { return true; }
}