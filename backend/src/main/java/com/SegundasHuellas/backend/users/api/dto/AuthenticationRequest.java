package com.SegundasHuellas.backend.users.api.dto;


import lombok.Data;

@Data
public class AuthenticationRequest {
    private String email;
    private String password;
}
