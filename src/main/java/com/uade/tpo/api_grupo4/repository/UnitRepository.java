package com.uade.tpo.api_grupo4.repository;

import com.uade.tpo.api_grupo4.entity.Unit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;


@Repository
public interface UnitRepository extends JpaRepository<Unit, Long> {
    Optional<Unit> findByDescriptionIgnoreCase(String description); // <-- AÑADE ESTA LÍNEA
}