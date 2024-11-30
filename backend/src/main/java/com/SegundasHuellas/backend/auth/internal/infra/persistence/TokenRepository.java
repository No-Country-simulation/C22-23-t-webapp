package com.SegundasHuellas.backend.auth.internal.infra.persistence;

import com.SegundasHuellas.backend.auth.internal.domain.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {

    List<Token> findAllValidIsFalseOrRevokedIsFalseByUserId(Long userId);

    Optional<Token> findByToken(String token);
}
