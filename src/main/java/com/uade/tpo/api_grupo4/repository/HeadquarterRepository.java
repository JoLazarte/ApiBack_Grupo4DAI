package com.uade.tpo.api_grupo4.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.uade.tpo.api_grupo4.entity.Headquarter;

@Repository
public interface HeadquarterRepository extends JpaRepository<Headquarter, Long> {
     Optional<Headquarter> findByName(String name);
}