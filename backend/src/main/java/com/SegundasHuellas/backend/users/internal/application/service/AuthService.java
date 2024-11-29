package com.SegundasHuellas.backend.users.internal.application.service;

import com.SegundasHuellas.backend.users.api.dto.SignUpRequest;
import com.SegundasHuellas.backend.users.api.dto.UserDTO;

public interface AuthService {
    UserDTO createUser(SignUpRequest signUpRequest);
}
