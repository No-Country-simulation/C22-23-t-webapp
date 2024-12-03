package com.SegundasHuellas.backend.users.api.dto;

import lombok.Data;

@Data
public class AuthenticationResponse {
    private String jwt;
    private Long userId;

}
