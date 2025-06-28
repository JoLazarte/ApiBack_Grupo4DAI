package com.uade.tpo.api_grupo4.repository;

import com.uade.tpo.api_grupo4.entity.PendingUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PendingUserRepository extends JpaRepository<PendingUser, String> { // El ID es de tipo String (el email)
}