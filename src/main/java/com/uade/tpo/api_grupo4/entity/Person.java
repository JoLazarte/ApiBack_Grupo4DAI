package com.uade.tpo.api_grupo4.entity;
import com.uade.tpo.api_grupo4.controllers.person.PersonView;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@MappedSuperclass
public abstract class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
    @Column(unique = true)
    protected String username;
    protected String firstName;
    protected String lastName;
    @Column(unique = true)
    protected String email;
    protected String password;
    @Column(unique = true)
    protected String phone;
    @Column(unique = true)
    protected String address;
    //@NotEmpty
    @Column(columnDefinition = "LONGTEXT")
    protected String urlAvatar;
    protected Boolean permissionGranted;

    public PersonView toView() {
		return new PersonView(
            this.id, 
            this.username, 
            this.firstName, 
            this.lastName, 
            this.email, 
            this.password, 
            this.phone, 
            this.address, 
            this.urlAvatar, 
            this.permissionGranted);
	}

}
