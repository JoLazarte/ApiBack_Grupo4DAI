package com.uade.tpo.api_grupo4.repository;

import com.uade.tpo.api_grupo4.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
}