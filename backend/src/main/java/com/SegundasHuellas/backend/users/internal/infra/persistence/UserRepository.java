package com.SegundasHuellas.backend.users.internal.infra.persistence;

import com.SegundasHuellas.backend.users.internal.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    Optional<User> findByUser(String user);

    Optional<User> findByDni(String dni);

    Optional<UserDetails> findFirstByEmail(String email);

    Optional<UserDetails> findFirstByUser(String user);

    boolean existsByUser(String user);
}
