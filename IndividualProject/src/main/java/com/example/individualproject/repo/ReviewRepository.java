package com.example.individualproject.repo;

import com.example.individualproject.models.ReviewModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<ReviewModel, Long> {
}
