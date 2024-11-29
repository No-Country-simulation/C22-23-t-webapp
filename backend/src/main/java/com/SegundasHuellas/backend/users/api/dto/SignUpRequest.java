package com.SegundasHuellas.backend.users.api.dto;

import lombok.Data;

@Data
public class SignUpRequest {
    private String nombre;
    private String apellido;
    private String dni;
    private String user;
    private String email;
    private String password;
}
