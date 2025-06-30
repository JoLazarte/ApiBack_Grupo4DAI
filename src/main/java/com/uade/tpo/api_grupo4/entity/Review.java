package com.uade.tpo.api_grupo4.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @ManyToOne
    @JoinColumn(nullable = false, name = "recipe_id")
    @JsonBackReference
    private Recipe recipe;
    @NotNull
    @ManyToOne
    @JoinColumn(nullable = false, name = "user_id")
    @JsonBackReference("review-author")
    private Person user;
    @NotNull
    private int rating;
    @NotNull
    private String comment;

}