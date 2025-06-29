package com.uade.tpo.api_grupo4.repository;

import com.uade.tpo.api_grupo4.entity.Step;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StepRepository extends JpaRepository<Step, Long> {
}