package com.example.individualproject.repo;

import com.example.individualproject.models.NumberModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NumberRepository extends JpaRepository<NumberModel, Long> {
}
