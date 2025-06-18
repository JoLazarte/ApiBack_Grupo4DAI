package com.uade.tpo.api_grupo4.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.uade.tpo.api_grupo4.entity.Person;

public interface PersonRepository extends JpaRepository<Person, Long> {
}
