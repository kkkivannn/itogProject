package com.example.individualproject.repo;

import com.example.individualproject.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserModel, Long> {
    UserModel findByUsernameContaining(String username);
}
