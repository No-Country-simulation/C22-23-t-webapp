package com.SegundasHuellas.backend.auth.internal.infra.persistence;

import com.SegundasHuellas.backend.auth.internal.domain.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {

    Optional<Token> findByToken(String token);

    @Query("""
            select t from Token t
            where t.user.id = :userId
            and (t.expired = false or t.revoked = false)
            """)
    List<Token> findAllValidTokensByUser(@Param("userId") Long userId);

    @Modifying(clearAutomatically = true)
    @Query("""
            delete from Token t
            where t.user.id = :userId
            and t.expired = true
            and t.revoked = true
            """)
    void deleteExpiredTokensForUser(@Param("userId") Long userId);

    @Modifying
    @Query("""
            update Token t
            set t.expired =true, t.revoked = true
            where t.user.id = :userId
            """)
    void revokeAllUserTokens(@Param("userId") Long userId);

    @Query("""
            select t from Token t
            where t.expired = true
            and t.revoked = true
            and t.createdAt < :before
            """)
    List<Token> findExpiredTokensCreatedBefore(@Param("before") LocalDateTime before);
}
