package com.SegundasHuellas.backend.auth.internal.domain.entity;

import com.SegundasHuellas.backend.shared.domain.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@SuperBuilder
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "tokens")
public class Token extends BaseEntity {

    @Column(name = "token", unique = true, length = 1024)
    private String token;
    @Enumerated(EnumType.STRING)
    @Column(name = "token_type")
    private TokenType tokenType = TokenType.BEARER;

    private boolean revoked;
    private boolean expired;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @PrePersist
    public void onCreate() {
        if (tokenType == null) {
            tokenType = TokenType.BEARER;
        }
    }

    public boolean isValid() {
        return !expired && !revoked;
    }

    public enum TokenType {
        BEARER,
    }
}
