package com.SegundasHuellas.backend.auth.api;

import com.SegundasHuellas.backend.auth.api.dto.AuthRegistrationRequest;
import com.SegundasHuellas.backend.auth.api.dto.AuthenticationResponse;

public interface RegistrationService {

    AuthenticationResponse register(AuthRegistrationRequest request, Long domainUserId);

}
