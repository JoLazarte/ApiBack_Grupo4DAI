package com.uade.tpo.api_grupo4.entity;

import java.io.Serializable;
import java.util.List;

import com.uade.tpo.api_grupo4.controllers.headquarter.HeadquarterView;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Headquarter  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(unique = true)
    private String phone;
    private String address;
    @Column(unique = true)
    private String email;
    @Column(unique = true)
    private String whattsapp;
    private String typeOfBonus;
    private Double courseBonus;	
    private String typeOfPromo;	
    private Double coursePromo;
  
    public HeadquarterView toView(){
        return new HeadquarterView(
            this.id,
            this.name,
            this.phone,
            this.address,
            this.email,
            this.whattsapp,
            this.typeOfBonus,
            this.courseBonus,
            this.typeOfPromo,
            this.coursePromo
          
           
        );
    }
}