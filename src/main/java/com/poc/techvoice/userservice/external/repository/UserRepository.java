package com.poc.techvoice.userservice.external.repository;

import com.poc.techvoice.userservice.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

    boolean existsByEmail(String email);
    User findByEmail(String email);
}
