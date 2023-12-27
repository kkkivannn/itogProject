package com.example.individualproject.repo;

import com.example.individualproject.models.ReservServModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ReservServRepository extends JpaRepository<ReservServModel, Long> {
    @Query(value = "SELECT nextval('reserv_serv_id_seq')", nativeQuery = true)
    Long getNextId();
}
