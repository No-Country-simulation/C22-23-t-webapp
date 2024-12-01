package com.SegundasHuellas.backend.auth.api;

import com.SegundasHuellas.backend.auth.api.dto.AuthRegistrationRequest;
import com.SegundasHuellas.backend.auth.internal.application.dto.AuthenticationResponse;
import com.SegundasHuellas.backend.auth.internal.application.dto.RegistrationRequest;

public interface RegistrationService {

    AuthenticationResponse register(AuthRegistrationRequest request, Long domainUserId);

}
