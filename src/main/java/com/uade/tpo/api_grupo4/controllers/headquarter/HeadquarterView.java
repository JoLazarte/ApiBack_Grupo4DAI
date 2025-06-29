package com.uade.tpo.api_grupo4.controllers.headquarter;

import com.uade.tpo.api_grupo4.entity.Headquarter;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class HeadquarterView {
    private Long id;
    private String name;
    private String phone;
    private String address;
    private String email;
    private String whattsapp;
    private String typeOfBonus;
    private Double courseBonus;	
    private String typeOfPromo;	
    private Double coursePromo;

    public Headquarter toEntity(){
        return new Headquarter(
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
