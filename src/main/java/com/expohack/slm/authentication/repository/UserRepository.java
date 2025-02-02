package com.expohack.slm.authentication.repository;

import com.expohack.slm.authentication.model.entity.User;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, UUID> {

  Optional<User> findByUsername(String username);
}
