package com.example.individualproject.repo;

import com.example.individualproject.models.ReservationModel;
import com.example.individualproject.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepository extends JpaRepository<ReservationModel, Long> {
    Iterable<ReservationModel> findByUserModel(UserModel user);
}
