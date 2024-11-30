package com.SegundasHuellas.backend.auth.internal.infra.persistence;

import com.SegundasHuellas.backend.auth.internal.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
