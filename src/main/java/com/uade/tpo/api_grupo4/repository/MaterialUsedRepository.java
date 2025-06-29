// Archivo: MaterialUsedRepository.java
package com.uade.tpo.api_grupo4.repository;

import com.uade.tpo.api_grupo4.entity.MaterialUsed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MaterialUsedRepository extends JpaRepository<MaterialUsed, Long> {
}