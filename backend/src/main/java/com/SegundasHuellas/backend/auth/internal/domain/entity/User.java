package com.SegundasHuellas.backend.auth.internal.domain.entity;

import com.SegundasHuellas.backend.shared.domain.base.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;
import java.util.Set;

@SuperBuilder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User extends BaseEntity {

    private String email;
    private String password;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Token> tokens;

    @Enumerated(EnumType.STRING)
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles")
    private Set<UserRole> roles;


    public enum UserRole implements GrantedAuthority {
        ADMIN,
        USER,
        ADOPTER,
        PROVIDER;

        @Override
        public String getAuthority() {
            return name();
        }
    }

}
