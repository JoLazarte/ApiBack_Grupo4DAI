package com.uade.tpo.api_grupo4.entity;

import java.util.Collection;
import java.util.List;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import com.uade.tpo.api_grupo4.controllers.user.UserView;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import jakarta.persistence.Entity;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor // SuperBuilder se encarga de los constructores, pero los dejamos por consistencia.
@Entity
public class User extends Person {

    // --- CAMPOS ESPECÍFICOS DE USER ---

    // Esta clase ahora está intencionalmente casi vacía.
    // Todas las relaciones importantes (`recipes`, `savedRecipes`, `reviews`)
    // han sido movidas a la clase padre `Person` para que tanto User
    // como Student puedan utilizarlas.


    // --- MÉTODOS ---

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("USER"));
    }

    /**
     * Convierte la entidad User a su DTO de vista (UserView).
     * Utiliza los getters heredados de la clase Person para obtener las listas.
     */
    public UserView toView(){
        return new UserView(
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
            getSavedRecipes(), // <-- Usa el getter heredado de Person
            getRecipes(),      // <-- Usa el getter heredado de Person
            getReviews()       // <-- Usa el getter heredado de Person
        );
    }
}
