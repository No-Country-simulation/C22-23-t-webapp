package com.SegundasHuellas.backend.auth.internal.infra.persistence;

import com.SegundasHuellas.backend.auth.api.dto.UserDetailsResponse;
import com.SegundasHuellas.backend.auth.api.enums.UserRole;
import com.SegundasHuellas.backend.auth.internal.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.Set;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);


    @Query("""
            select new com.SegundasHuellas.backend.auth.api.dto.UserDetailsResponse(
                u.email,
                u.lastLoginDate,
                u.active,
                u.locked
            )
            from User u
            left join u.roles r
            where u.id = :userId
                group by u.email, u.lastLoginDate, u.active, u.locked
            """)
    Optional<UserDetailsResponse> findUserDetails(@Param("userId") Long userId);

    @Query("""
            select u.roles
            from User u
            where u.id = :userId
            """)
    Set<UserRole> findUserRoles(@Param("userId") Long userId);

}
