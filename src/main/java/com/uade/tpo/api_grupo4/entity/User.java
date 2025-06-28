package com.uade.tpo.api_grupo4.entity;

import java.util.Collection;
import java.util.List;

import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.uade.tpo.api_grupo4.controllers.user.UserView;

import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User extends Person implements UserDetails {

    @NotNull
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<SavedRecipe> savedRecipes;
    @NotNull
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Recipe> recipes;
    @NotNull
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Review> reviews;

    public UserView toView(){
        return new UserView(id, username, firstName, lastName, email, password, phone, address, urlAvatar, permissionGranted, savedRecipes, recipes, reviews);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("USER"));
    }

    @Override
    public boolean isAccountNonExpired() {
        // Devolvemos true para indicar que la cuenta nunca expira.
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // Devolvemos true para indicar que la cuenta nunca se bloquea.
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // Devolvemos true para indicar que la contraseña nunca expira.
        return true;
    }

    @Override
    public boolean isEnabled() {
        // Devolvemos true para indicar que el usuario está siempre habilitado.
        return true;
    }
}