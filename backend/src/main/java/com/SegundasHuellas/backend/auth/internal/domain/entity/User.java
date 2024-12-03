package com.SegundasHuellas.backend.auth.internal.domain.entity;

import com.SegundasHuellas.backend.auth.api.enums.UserRole;
import com.SegundasHuellas.backend.shared.domain.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@SuperBuilder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@Slf4j
@Table(name = "users")
public class User extends BaseEntity {

    public static final int MAX_FAILED_ATTEMPTS = 5;
    private String email;
    private String password;

    @Column(name = "domain_user_id")
    private Long domainUserId; // Reference to the specific domain entity (Adopter, PetProvider, etc.)

    @Builder.Default
    private boolean active = true;

    @Builder.Default
    private boolean locked = false;

    @Builder.Default
    private boolean emailVerified = true; //TODO: EMAIL VERIFICATION FLOW
    private LocalDateTime passwordExpiryDate;

    @Column(name = "last_login_date")
    private LocalDateTime lastLoginDate;

    @Column(name = "failed_attempts_count", nullable = false)
    @Builder.Default
    private int failedAttemptsCount = 0;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Token> tokens;

    @Enumerated(EnumType.STRING)
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles")
    private Set<UserRole> roles;

    public void incrementFailedAttemptsCount() {
        this.failedAttemptsCount++;
        if (this.failedAttemptsCount >= MAX_FAILED_ATTEMPTS) {
            this.locked = true;
        }
    }

    public void resetFailedAttempts() {
        this.failedAttemptsCount = 0;
    }

    public void recordSuccessfulLogin() {
        this.lastLoginDate = LocalDateTime.now();
        this.resetFailedAttempts();
    }


}
