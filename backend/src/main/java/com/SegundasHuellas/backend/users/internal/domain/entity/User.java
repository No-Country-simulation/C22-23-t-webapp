package com.SegundasHuellas.backend.users.internal.domain.entity;

import com.SegundasHuellas.backend.users.api.dto.UserDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@SuppressWarnings("serial")
@Entity
@Data
public class User implements UserDetails {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String password;
    private String user;
    private String nombre;
    private String apellido;
    private String dni;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public UserDTO getUserDto() {
        UserDTO userDto = new UserDTO();
        userDto.setId(this.id);
        userDto.setEmail(this.email);
        userDto.setUser(this.user);
        userDto.setNombre(this.nombre);
        userDto.setApellido(this.apellido);
        userDto.setDni(this.dni);
        return userDto;
    }





}
