package com.uade.tpo.api_grupo4.entity;
import com.uade.tpo.api_grupo4.controllers.person.PersonView;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String username;
    private String firstName;
    private String lastName;
    @Column(unique = true)
    private String email;
    private String password;
    @Column(unique = true)
    private String phone;
    @Column(unique = true)
    private String address;
    //@NotEmpty
    @Column(nullable = false, columnDefinition = "LONGTEXT")
    private String urlAvatar;
    private Boolean permissionGranted;

    public PersonView toView() {
		return new PersonView(id, username, firstName, lastName, email, password, phone, address, urlAvatar, permissionGranted);
	}

}
